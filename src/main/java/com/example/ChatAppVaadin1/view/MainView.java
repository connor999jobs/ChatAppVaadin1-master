package com.example.ChatAppVaadin1.view;

import com.example.ChatAppVaadin1.model.Storage;
import com.example.ChatAppVaadin1.security.SecurityService;
import com.github.rjeschke.txtmark.Processor;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;
import org.springframework.beans.factory.annotation.Autowired;

@Route("")
@Push
public class MainView extends VerticalLayout {
    private final Storage storage;
    private Registration registration;
    private Grid<Storage.ChatMessage> grid;
    private VerticalLayout chat;
    private VerticalLayout login;
    private String user = "";
    private final SecurityService securityService;

    @Autowired
    public MainView(Storage storage, SecurityService securityService) {
        this.securityService = securityService;
        this.storage = storage;
        buildChat();
        buildLogin();
    }

    private void buildLogin() {

        H1 logo = new H1("Vaadin CRM");
        login = new VerticalLayout() {{
            TextField field = new TextField();
            field.setPlaceholder("Please, introduce yourself: ");
            add(
                    field,
                    new Button("Login") {{
                        addClickListener(click -> {
                            login.setVisible(false);
                            chat.setVisible(true);
                            user = field.getValue();
                            storage.addRecordJoined(user);
                        });
                        addClickShortcut(Key.ENTER);
                    }}
            );
        }};

        Button logout = new Button("Log out", e -> securityService.logout());
        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo, logout);
        header.expand(logo);
        add(login);
    }

    private void buildChat() {
        chat = new VerticalLayout();
        add(chat);
        chat.setVisible(false);
        grid = new Grid<>();
        grid.setItems(storage.getMessages());
        grid.addColumn(new ComponentRenderer<>(message -> new Html(renderRow(message))))
                .setAutoWidth(true);
        TextField field = new TextField();
        chat.add(
                new H3("Chat Application"),
                grid,
                new HorizontalLayout() {{
                    add(
                            field,
                            new Button("Send Message") {{
                                addClickListener(click -> {
                                    storage.addRecord(user, field.getValue());
                                    field.clear();
                                });
                                addClickShortcut(Key.ENTER);
                            }}
                    );
                }}

        );
    }

    public void onMessage(Storage.ChatEvent event) {
        if (getUI().isPresent()) {
            UI ui = getUI().get();
            ui.getSession().lock();
//           ui.getPage().executeJs("$0._scrollToIndex($1)",grid, storage.size());

            ui.beforeClientResponse(grid, ctx -> grid.scrollToEnd());
            ui.access(() -> grid.getDataProvider().refreshAll());
            ui.getSession().unlock();

        }
    }

    private String renderRow(Storage.ChatMessage message) {
        if (message.getName().isEmpty()) {
            return Processor.process(String.format("_User **%s** is just joined the chat!_", message.getName(), message.getMessage()));
        } else {
            return Processor.process(String.format("**%s**: %s", message.getName(), message.getMessage()));
        }
    }


    @Override
    protected void onAttach(AttachEvent attachEvent) {
        registration = storage.attachListener(this::onMessage);
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        registration.remove();
    }
}

package com.example.ChatAppVaadin1.view;


import com.example.ChatAppVaadin1.service.AppUserService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.PermitAll;
import java.util.List;
import java.util.Map;

/**
 * Activation view. After register new User, you need verification him.
 * You will receive an email with a link to activate your account
 * */

@Route("activate")
@PermitAll
public class ActivationView extends Composite<Component> implements BeforeEnterObserver {
    private VerticalLayout layout;
    private final AppUserService service;
    @Autowired
    public ActivationView(AppUserService service) {
        this.service = service;
    }

    @Override
    @SneakyThrows
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        Map<String, List<String>> params = beforeEnterEvent.getLocation().getQueryParameters().getParameters();
        String code = params.get("code").get(0);
        service.activate(code);
        layout.add(
                new Text("Account activate"),
                new RouterLink("Login", LoginView.class)
        );
    }

    @Override
    protected Component initContent() {
        layout = new VerticalLayout();
        return layout;
    }
}

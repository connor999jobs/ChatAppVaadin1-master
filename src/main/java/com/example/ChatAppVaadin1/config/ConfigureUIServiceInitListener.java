package com.example.ChatAppVaadin1.config;

import com.example.ChatAppVaadin1.security.SecurityUtils;
import com.example.ChatAppVaadin1.view.ActivationView;
import com.example.ChatAppVaadin1.view.LoginView;
import com.example.ChatAppVaadin1.view.RegisterView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import org.springframework.stereotype.Component;

/**
 * In serviceInit(), listen for the initialization of the UI (the internal root component in Vaadin)
 * and then add a listener before every view transition.
 * In beforeEnter(), reroute all requests to the login, if the user is not logged in.
 **/

@Component
public class ConfigureUIServiceInitListener implements VaadinServiceInitListener {
    @Override
    public void serviceInit(ServiceInitEvent event) {
        event.getSource().addUIInitListener(uiEvent -> {
            final UI ui = uiEvent.getUI();
            ui.addBeforeEnterListener(this::beforeEnterActivation);
            ui.addBeforeEnterListener(this::beforeEnter);

        });
    }

    private void beforeEnter(BeforeEnterEvent event) {
        if (!LoginView.class.equals(event.getNavigationTarget())
                && !RegisterView.class.equals(event.getNavigationTarget())
                && !SecurityUtils.isUserLoggedIn()) {
            event.rerouteTo(LoginView.class);
        }
    }

    private void beforeEnterActivation(BeforeEnterEvent event) {
        if (ActivationView.class.equals(event.getNavigationTarget()) && SecurityUtils.isUserLoggedIn()) {
            event.rerouteTo(ActivationView.class);
        }
    }
}

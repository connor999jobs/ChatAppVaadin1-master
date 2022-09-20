package com.example.ChatAppVaadin1.view;


import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("login")
@PageTitle("Login | Vaadin Chat Application")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private final LoginForm loginForm = new LoginForm();

    private static final String OAUTH_URL_GOOGLE = "/oauth2/authorization/google";
    private static final String OAUTH_URL_GITHUB = "/oauth2/authorization/github";

    public LoginView(){

        Anchor loginLinkGoogle = new Anchor(OAUTH_URL_GOOGLE, "Login with Google");
        // Set router-ignore attribute so that Vaadin router doesn't handle the login request
        loginLinkGoogle.getElement().setAttribute("router-ignore", true);
        add(loginLinkGoogle);
        getStyle().set("padding", "200px");
        setAlignItems(FlexComponent.Alignment.CENTER);

        Anchor loginLinkGitHub = new Anchor(OAUTH_URL_GITHUB, "Login with Github");
        // Set router-ignore attribute so that Vaadin router doesn't handle the login request
        loginLinkGitHub.getElement().setAttribute("router-ignore", true);
        add(loginLinkGitHub);
        getStyle().set("padding", "200px");
        setAlignItems(FlexComponent.Alignment.CENTER);

        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        loginForm.setAction("login");

        add(new H1("Vaadin Chat Application"), loginForm);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if (beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")){
            loginForm.setError(true);
        }
    }
}

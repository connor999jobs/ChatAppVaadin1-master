package com.example.ChatAppVaadin1.view;

import com.example.ChatAppVaadin1.model.user.AppUser;
import com.example.ChatAppVaadin1.service.AppUserService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.ui.Notification;
import org.springframework.beans.factory.annotation.Autowired;


@Route("register")
public class RegisterView extends Composite {


    private AppUserService userService;

    @Autowired
    public RegisterView(AppUserService userService) {
        this.userService = userService;
    }

    @Override
    protected Component initContent() {
        TextField name = new TextField("Name");
        TextField username = new TextField("Username");
        EmailField email = new EmailField("Email");
        PasswordField password = new PasswordField("Password");
        PasswordField confirmPassword = new PasswordField("Confirm password");

        return new VerticalLayout(
                new H2("Register"),
                name,
                username,
                email,
                password,
                confirmPassword,
                new Button("Send", buttonClickEvent -> register(
                        name.getValue(),
                        username.getValue(),
                        email.getValue(),
                        password.getValue(),
                        confirmPassword.getValue()
                ))
        );
    }

    private void register(String name, String username, String email, String password, String confirmPassword) {
        if (name.isEmpty()){
            Notification.show("Enter a name");
        }
        else if (username.trim().isEmpty()) {
            Notification.show("Enter a username");
        } else if (email.isEmpty()) {
            Notification.show("Enter a email");
        } else if (password.isEmpty()) {
            Notification.show("Enter a password");
        }
        else if (!password.equals(confirmPassword)) {
            Notification.show("Passwords don't match");
        } else {
            userService.register(name,username, email, password, confirmPassword);
            Notification.show("Success.");
        }
    }
}
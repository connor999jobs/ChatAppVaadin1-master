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
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.ui.Notification;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.PermitAll;

/**
 * View for Register new User
 */


@Route("register")
@PermitAll
public class RegisterView extends Composite {
    private final AppUserService userService;
    private final BeanValidationBinder<AppUser> binder = new BeanValidationBinder<>(AppUser.class);
    @Autowired
    public RegisterView(AppUserService userService ) {
        this.userService = userService;
    }
    @Override
    protected Component initContent() {
        TextField name = new TextField("Name");
        name.setPlaceholder("Enter your name");
        name.setClearButtonVisible(true);
        name.setMinLength(2);
        name.setPattern("^[a-zA-z\\s]+");
        name.setErrorMessage("Only letter, min 2 chars");
        binder.forField(name).withValidator(n -> n.length() >=2,
                "Name must contain at least two characters")
                .bind(AppUser::getName, AppUser::setName);

        TextField username = new TextField("Username");
        username.setPlaceholder("Enter your username");
        username.setClearButtonVisible(true);
        username.setMinLength(2);
        username.setPattern("^[a-zA-z\\s]+");
        username.setErrorMessage("Only letter, min 2 chars");
        binder.forField(username).withValidator(n -> n.length() >=2,
                        "Username must contain at least two characters")
                .bind(AppUser::getUsername, AppUser::setUsername);

        EmailField email = new EmailField("Email");
        email.setPlaceholder("Enter your email");
        email.setClearButtonVisible(true);
        email.setPattern("^(.+)@(\\S+)$");
        email.setErrorMessage("This email is not valid. Example: vvv@gmail.com");
        binder.forField(email).withValidator
                (new EmailValidator( "This doesn't look like a valid email address"))
                .bind(AppUser::getEmail, AppUser::setEmail);

        PasswordField password = new PasswordField("Password");
        password.setPlaceholder("Enter your password");
        password.setClearButtonVisible(true);
        password.setMinLength(2);
        password.setErrorMessage("Min 2 chars");
        binder.forField(password).withValidator(n -> n.length() >=2,
                        "Password must contain at least two characters")
                .bind(AppUser::getPassword, AppUser::setPassword);

        PasswordField confirmPassword = new PasswordField("Confirm password");
        confirmPassword.setPlaceholder("Confirm your password");
        confirmPassword.setClearButtonVisible(true);

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
                )),
                new RouterLink("Back to Login page",LoginView.class)
        );
    }

    private void register(String name, String username, String email, String password, String confirmPassword) {
        AppUser newUser = new AppUser();
        if (name.isEmpty()) {
            Notification.show("Enter a name");
        } else if (username.isEmpty()) {
            Notification.show("Enter a username");
        } else if (email.isEmpty()) {
            Notification.show("Enter a email");
        } else if (password.isEmpty()) {
            Notification.show("Enter a password");
        } else if (!password.equals(confirmPassword)) {
            Notification.show("Passwords don't match");
        } else {
            newUser.setName(name);
            newUser.setUsername(username);
            newUser.setEmail(email);
            newUser.setPassword(password);
            newUser.setMatchingPassword(confirmPassword);
            userService.registerNewUserAccount(newUser);
            Notification.show("Success.");
        }
    }
}

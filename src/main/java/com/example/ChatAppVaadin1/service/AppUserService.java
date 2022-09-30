package com.example.ChatAppVaadin1.service;

import com.example.ChatAppVaadin1.model.user.AppUser;
import com.example.ChatAppVaadin1.model.user.AppUserRole;
import com.example.ChatAppVaadin1.repository.AppUserRepository;
import lombok.SneakyThrows;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.security.auth.message.AuthException;
import javax.validation.Valid;

/**
 * Application User Service for registration new User, and activate his account with email
 * */

@Service
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;


    @Autowired
    public AppUserService(@Lazy AppUserRepository appUserRepository,
                          @Lazy PasswordEncoder passwordEncoder, JavaMailSender mailSender) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String USER_NOT_FOUND_MSG = "User with username %s not found";
        return appUserRepository
                .findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, username)));
    }


    @SneakyThrows
    public void registerNewUserAccount(@Valid AppUser accountDto) {
        AppUser user = new AppUser();
        user.setName(accountDto.getName());
        user.setUsername(accountDto.getUsername());
        user.setEmail(accountDto.getEmail());
        user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        user.setMatchingPassword(passwordEncoder.encode(accountDto.getMatchingPassword()));
        user.setAppUserRole(AppUserRole.USER);
        user.setEnabled(true);
        user.setLocked(false);

        String randomCode = RandomString.make(64);
        user.setVerificationCode(randomCode);

        String text = "http://localhost:8080/activate?code=" + user.getVerificationCode();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("connor999@gmail.com");
        message.setSubject("Confirmation email");
        message.setText(text);
        message.setTo(accountDto.getEmail());
        mailSender.send(message);

        appUserRepository.save(user);
    }


    @SneakyThrows
    public void activate(String activationCode)   {
        AppUser user = appUserRepository.getByVerificationCode(activationCode);
        if (user != null) {
            user.setEnabled(true);
            appUserRepository.save(user);
        } else {
            throw new AuthException();
        }
    }
}

package com.example.ChatAppVaadin1.service;

import com.example.ChatAppVaadin1.model.user.AppUser;
import com.example.ChatAppVaadin1.model.user.AppUserRole;
import com.example.ChatAppVaadin1.repository.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AppUserService(@Lazy AppUserRepository appUserRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String USER_NOT_FOUND_MSG = "User with username %s not found";
        return appUserRepository
                .findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, username)));
    }


    public void register(String name, String username, String email, String password, String confirmPassword) {
        appUserRepository.save(new AppUser(name,username,email,password,confirmPassword,
                AppUserRole.USER,false,true));
    }

    public AppUser registerNewUserAccount(AppUser accountDto) {
        AppUser user = new AppUser();
        user.setName(accountDto.getName());
        user.setUsername(accountDto.getUsername());
        user.setEmail(accountDto.getEmail());
        user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        user.setMatchingPassword(passwordEncoder.encode(accountDto.getMatchingPassword()));
        user.setAppUserRole(AppUserRole.USER);
        user.setEnabled(true);
        user.setLocked(false);

        return appUserRepository.save(user);
    }
}

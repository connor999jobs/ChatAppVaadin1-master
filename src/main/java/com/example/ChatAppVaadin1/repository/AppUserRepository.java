package com.example.ChatAppVaadin1.repository;

import com.example.ChatAppVaadin1.model.user.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByName(String name);

    AppUser getByVerificationCode(String verificationCode);
}

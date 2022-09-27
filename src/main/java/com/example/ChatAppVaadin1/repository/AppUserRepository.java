package com.example.ChatAppVaadin1.repository;

import com.example.ChatAppVaadin1.model.user.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

//    @Query("SELECT username from AppUser where username = ?1")
    Optional<AppUser> findByName(String name);
}

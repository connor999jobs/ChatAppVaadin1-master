package com.example.ChatAppVaadin1.model.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@RequiredArgsConstructor
@Getter
@Setter
public abstract class IdFactory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}

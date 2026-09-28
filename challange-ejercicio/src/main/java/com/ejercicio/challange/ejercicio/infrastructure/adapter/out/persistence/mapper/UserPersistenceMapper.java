package com.ejercicio.challange.ejercicio.infrastructure.adapter.out.persistence.mapper;

import com.ejercicio.challange.ejercicio.domain.model.User;
import com.ejercicio.challange.ejercicio.infrastructure.adapter.out.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class UserPersistenceMapper {

    public UserEntity toEntity(User domain) {
        if (domain == null) return null;
        UserEntity entity = new UserEntity();
        entity.setId(domain.getId());
        entity.setNombre(domain.getNombre());
        entity.setEdad(domain.getEdad());
        entity.setCorreo(domain.getCorreo());
        entity.setPokemonIds(domain.getPokemonIds() != null ? domain.getPokemonIds() : new ArrayList<>());
        return entity;
    }

    public User toDomain(UserEntity entity) {
        if (entity == null) return null;
        User domain = new User();
        domain.setId(entity.getId());
        domain.setNombre(entity.getNombre());
        domain.setEdad(entity.getEdad());
        domain.setCorreo(entity.getCorreo());
        domain.setPokemonIds(entity.getPokemonIds() != null ? entity.getPokemonIds() : new ArrayList<>());
        return domain;
    }
}
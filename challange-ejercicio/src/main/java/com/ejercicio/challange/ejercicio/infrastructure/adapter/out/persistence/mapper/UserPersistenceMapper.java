package com.ejercicio.challange.ejercicio.infrastructure.adapter.out.persistence.mapper;

import com.ejercicio.challange.ejercicio.domain.model.User;
import com.ejercicio.challange.ejercicio.infrastructure.adapter.out.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserPersistenceMapper {

    public User toDomain(UserEntity entity) {
        if (entity == null) return null;
        return new User(
                entity.getId(),
                entity.getNombre(),
                entity.getEdad(),
                entity.getCorreo()
        );
    }

    public UserEntity toEntity(User domain) {
        if (domain == null) return null;
        return new UserEntity(
                domain.getId(),
                domain.getNombre(),
                domain.getEdad(),
                domain.getCorreo()
        );
    }
}
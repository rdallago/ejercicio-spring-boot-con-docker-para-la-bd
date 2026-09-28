package com.ejercicio.challange.ejercicio.infrastructure.adapter.in.rest.mapper;

import com.ejercicio.challange.ejercicio.domain.model.User;
import com.ejercicio.challange.ejercicio.infrastructure.adapter.in.rest.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserRestMapper {

    public UserDto toDto(User domain) {
        if (domain == null) return null;
        return new UserDto(
                domain.getId(),
                domain.getNombre(),
                domain.getEdad(),
                domain.getCorreo()
        );
    }

    public User toDomain(UserDto dto) {
        if (dto == null) return null;
        return new User(
                dto.getId(),
                dto.getNombre(),
                dto.getEdad(),
                dto.getCorreo()
        );
    }
}
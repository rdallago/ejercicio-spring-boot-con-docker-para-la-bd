package com.ejercicio.challange.ejercicio.infrastructure.adapter.in.rest.mapper;

import com.ejercicio.challange.ejercicio.domain.model.User;
import com.ejercicio.challange.ejercicio.infrastructure.adapter.in.rest.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class UserRestMapper {

  public UserDto toDto(User domain) {
    if (domain == null) return null;
    
    UserDto dto = new UserDto();
    dto.setId(domain.getId());
    dto.setNombre(domain.getNombre());
    dto.setEdad(domain.getEdad());
    dto.setCorreo(domain.getCorreo());
    dto.setPokemonIds(domain.getPokemonIds() != null ? domain.getPokemonIds() : new ArrayList<>());
    
    // ⚠️ ESTA LÍNEA ES LA QUE FALTA: mapear los objetos enriquecidos desde el dominio
    dto.setPokemons(domain.getPokemons() != null ? domain.getPokemons() : new ArrayList<>());
    
    return dto;
}

    // Este es el método que necesita UserController para recibir el UserDto
    public User toDomain(UserDto dto) {
        if (dto == null) return null;
        User user = new User();
        user.setId(dto.getId());
        user.setNombre(dto.getNombre());
        user.setEdad(dto.getEdad());
        user.setCorreo(dto.getCorreo());
        user.setPokemonIds(dto.getPokemonIds() != null ? dto.getPokemonIds() : new ArrayList<>());
        return user;
    }
    
}
package com.ejercicio.challange.ejercicio.infrastructure.adapter.out.persistence.adapter;

import com.ejercicio.challange.ejercicio.domain.model.User;
import com.ejercicio.challange.ejercicio.domain.repository.UserRepositoryPort;
import com.ejercicio.challange.ejercicio.infrastructure.adapter.out.persistence.entity.UserEntity;
import com.ejercicio.challange.ejercicio.infrastructure.adapter.out.persistence.mapper.UserPersistenceMapper;
import com.ejercicio.challange.ejercicio.infrastructure.adapter.out.persistence.repository.SpringDataUserRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final SpringDataUserRepository springDataUserRepository;
    private final UserPersistenceMapper mapper;

    public UserRepositoryAdapter(SpringDataUserRepository springDataUserRepository, UserPersistenceMapper mapper) {
        this.springDataUserRepository = springDataUserRepository;
        this.mapper = mapper;
    }

    @Override
    public User save(User user) {
        UserEntity entity = mapper.toEntity(user);
        UserEntity savedEntity = springDataUserRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public List<User> findAll() {
        return springDataUserRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> findById(Long id) {
        return springDataUserRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public User update(Long id, User user) {
        return springDataUserRepository.findById(id).map(existingEntity -> {
            existingEntity.setNombre(user.getNombre());
            existingEntity.setEdad(user.getEdad());
            existingEntity.setCorreo(user.getCorreo());
            UserEntity updatedEntity = springDataUserRepository.save(existingEntity);
            return mapper.toDomain(updatedEntity);
        }).orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }

    @Override
    public void deleteById(Long id) {
        springDataUserRepository.deleteById(id);
    }
}
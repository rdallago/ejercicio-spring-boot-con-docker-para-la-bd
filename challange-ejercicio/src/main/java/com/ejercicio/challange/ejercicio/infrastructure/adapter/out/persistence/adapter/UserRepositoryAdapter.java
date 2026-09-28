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
    private final UserPersistenceMapper userPersistenceMapper;

   public UserRepositoryAdapter(SpringDataUserRepository springDataUserRepository,
                                UserPersistenceMapper userPersistenceMapper) {
        this.springDataUserRepository = springDataUserRepository;
        this.userPersistenceMapper = userPersistenceMapper;
    }

 @Override
    public User save(User user) {
        UserEntity entity = userPersistenceMapper.toEntity(user);
        UserEntity savedEntity = springDataUserRepository.save(entity);
        return userPersistenceMapper.toDomain(savedEntity);
    }


    @Override
    public List<User> findAll() {
        return springDataUserRepository.findAll()
                .stream()
                .map(userPersistenceMapper::toDomain) // <-- Cambiado mapper::toDomain por userPersistenceMapper::toDomain
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> findById(Long id) {
        return springDataUserRepository.findById(id)
                .map(userPersistenceMapper::toDomain); // <-- Cambiado mapper::toDomain por userPersistenceMapper::toDomain
    }

   @Override
    public User update(Long id, User user) {
        return springDataUserRepository.findById(id).map(entity -> {
            entity.setNombre(user.getNombre());
            entity.setEdad(user.getEdad());
            entity.setCorreo(user.getCorreo());
            entity.setPokemonIds(user.getPokemonIds());
            
            UserEntity updatedEntity = springDataUserRepository.save(entity);
            return userPersistenceMapper.toDomain(updatedEntity);
        }).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    @Override
    public void deleteById(Long id) {
        springDataUserRepository.deleteById(id);
    }
}
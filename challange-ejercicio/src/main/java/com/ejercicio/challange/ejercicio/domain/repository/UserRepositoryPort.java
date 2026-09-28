package com.ejercicio.challange.ejercicio.domain.repository;

import com.ejercicio.challange.ejercicio.domain.model.User;
import java.util.List;
import java.util.Optional;

public interface UserRepositoryPort {
    User save(User user);
    List<User> findAll();
    Optional<User> findById(Long id);
    User update(Long id, User user);
    void deleteById(Long id);
}
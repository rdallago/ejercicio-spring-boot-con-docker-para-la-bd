package com.ejercicio.challange.ejercicio.application.service;

import com.ejercicio.challange.ejercicio.domain.model.User;
import com.ejercicio.challange.ejercicio.domain.repository.UserRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepositoryPort userRepositoryPort;

    public UserService(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

    public User createUser(User user) {
        return userRepositoryPort.save(user);
    }

    public List<User> getAllUsers() {
        return userRepositoryPort.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepositoryPort.findById(id);
    }

    public User updateUser(Long id, User user) {
        return userRepositoryPort.update(id, user);
    }

    public void deleteUser(Long id) {
        userRepositoryPort.deleteById(id);
    }
}
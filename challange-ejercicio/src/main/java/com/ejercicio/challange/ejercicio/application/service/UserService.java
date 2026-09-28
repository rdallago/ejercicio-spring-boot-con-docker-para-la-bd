package com.ejercicio.challange.ejercicio.application.service;

import com.ejercicio.challange.ejercicio.domain.model.User;
import com.ejercicio.challange.ejercicio.domain.repository.UserRepositoryPort;
import org.springframework.stereotype.Service;
import com.ejercicio.challange.ejercicio.domain.port.out.PokemonApiClientPort;
import java.util.List;
import java.util.Optional;
@Service
public class UserService {

    private final UserRepositoryPort userRepositoryPort;
    private final PokemonApiClientPort pokemonApiClientPort;

   
    // Constructor con ambas dependencias inyectadas
    public UserService(UserRepositoryPort userRepositoryPort, PokemonApiClientPort pokemonApiClientPort) {
        this.userRepositoryPort = userRepositoryPort;
        this.pokemonApiClientPort = pokemonApiClientPort;
    }

    public User createUser(User user) {
        return userRepositoryPort.save(user);
    }

 public List<User> getAllUsers() {
        List<User> users = userRepositoryPort.findAll();
        users.forEach(user -> {
            if (user.getPokemonIds() != null && !user.getPokemonIds().isEmpty()) {
                user.setPokemons(pokemonApiClientPort.getPokemonsByIds(user.getPokemonIds()));
            }
        });
        return users;
    }

   public Optional<User> getUserById(Long id) {
        Optional<User> userOptional = userRepositoryPort.findById(id);
        userOptional.ifPresent(user -> {
            if (user.getPokemonIds() != null && !user.getPokemonIds().isEmpty()) {
                user.setPokemons(pokemonApiClientPort.getPokemonsByIds(user.getPokemonIds()));
            }
        });
        return userOptional;
    }

    public User updateUser(Long id, User user) {
        return userRepositoryPort.update(id, user);
    }

    public void deleteUser(Long id) {
        userRepositoryPort.deleteById(id);
    }
    
    
}
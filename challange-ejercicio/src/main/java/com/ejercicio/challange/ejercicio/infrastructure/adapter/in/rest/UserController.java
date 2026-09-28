package com.ejercicio.challange.ejercicio.infrastructure.adapter.in.rest;

import com.ejercicio.challange.ejercicio.application.service.UserService;
import com.ejercicio.challange.ejercicio.domain.model.User;
import com.ejercicio.challange.ejercicio.infrastructure.adapter.in.rest.dto.UserDto;
import com.ejercicio.challange.ejercicio.infrastructure.adapter.in.rest.mapper.UserRestMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;
    private final UserRestMapper restMapper;

    public UserController(UserService userService, UserRestMapper restMapper) {
        this.userService = userService;
        this.restMapper = restMapper;
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        User userDomain = restMapper.toDomain(userDto);
        User createdUser = userService.createUser(userDomain);
        return new ResponseEntity<>(restMapper.toDto(createdUser), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers()
                .stream()
                .map(restMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(restMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        User userDomain = restMapper.toDomain(userDto);
        User updatedUser = userService.updateUser(id, userDomain);
        return ResponseEntity.ok(restMapper.toDto(updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
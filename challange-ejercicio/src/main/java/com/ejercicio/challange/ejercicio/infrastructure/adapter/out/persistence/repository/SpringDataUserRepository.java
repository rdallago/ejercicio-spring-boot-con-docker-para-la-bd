package com.ejercicio.challange.ejercicio.infrastructure.adapter.out.persistence.repository;

import com.ejercicio.challange.ejercicio.infrastructure.adapter.out.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataUserRepository extends JpaRepository<UserEntity, Long> {
}
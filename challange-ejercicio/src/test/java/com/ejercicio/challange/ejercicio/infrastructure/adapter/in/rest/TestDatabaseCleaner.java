package com.ejercicio.challange.ejercicio.infrastructure.adapter.in.rest;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class TestDatabaseCleaner {

    private final JdbcTemplate jdbcTemplate;

    public TestDatabaseCleaner(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void clean() {
        // Limpia la tabla 'usuario' en challange_db_test y reinicia el ID autoincremental
        jdbcTemplate.execute("TRUNCATE TABLE usuario RESTART IDENTITY CASCADE;");
    }
}
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
        jdbcTemplate.execute("DELETE FROM usuario;");
        jdbcTemplate.execute("ALTER TABLE usuario ALTER COLUMN id RESTART WITH 1;");
    }
}
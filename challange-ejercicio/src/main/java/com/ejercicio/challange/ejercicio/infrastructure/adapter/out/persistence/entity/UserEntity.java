package com.ejercicio.challange.ejercicio.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import com.ejercicio.challange.ejercicio.infrastructure.adapter.out.persistence.converter.IntegerListConverter;
import java.util.ArrayList;
import java.util.List;
import com.ejercicio.challange.ejercicio.config.Generated;
@Entity
@Table(name = "usuario")
@Generated
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "edad", nullable = false)
    private Integer edad;

    // Vinculamos la propiedad 'correo' de Java con la columna física 'email' de PostgreSQL
    @Column(name = "email", nullable = false, unique = true)
    private String correo;
    
      
    // Se guarda en la misma tabla como un texto '1,5,7' ejemplos de pokemonIds
    @Convert(converter = IntegerListConverter.class)
    @Column(name = "pokemon_ids")
    private List<Integer> pokemonIds = new ArrayList<>();

    public UserEntity() {
    }
    // Constructor de 4 parámetros
    public UserEntity(Long id, String nombre, Integer edad, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.correo = correo;
        
    }
    // Constructor de 5 parámetros
    public UserEntity(Long id, String nombre, Integer edad, String correo,List<Integer> pokemonIds) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.correo = correo;
        this.pokemonIds = pokemonIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public List<Integer> getPokemonIds() { return pokemonIds; }
    
    public void setPokemonIds(List<Integer> pokemonIds) { this.pokemonIds = pokemonIds; }
}
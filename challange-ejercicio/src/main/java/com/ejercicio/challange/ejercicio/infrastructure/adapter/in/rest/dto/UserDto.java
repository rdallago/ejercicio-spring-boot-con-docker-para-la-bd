package com.ejercicio.challange.ejercicio.infrastructure.adapter.in.rest.dto;

import com.ejercicio.challange.ejercicio.domain.model.User.PokemonDetail;
import java.util.ArrayList;
import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonProperty;
public class UserDto {
   @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    private String nombre;
    private Integer edad;
    private String correo;
    private List<Integer> pokemonIds = new ArrayList<>();
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private List<PokemonDetail> pokemons = new ArrayList<>();

    public UserDto() {
    }

    public UserDto(Long id, String nombre, Integer edad, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.correo = correo;
    }

    public UserDto(Long id, String nombre, Integer edad, String correo, List<Integer> pokemonIds) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.correo = correo;
        this.pokemonIds = pokemonIds;
    }

    public UserDto(Long id, String nombre, Integer edad, String correo, List<Integer> pokemonIds, List<PokemonDetail> pokemons) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.correo = correo;
        this.pokemonIds = pokemonIds;
        this.pokemons = pokemons;
    }

    // --- GETTERS Y SETTERS ---

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

    public List<Integer> getPokemonIds() {
        return pokemonIds;
    }

    public void setPokemonIds(List<Integer> pokemonIds) {
        this.pokemonIds = pokemonIds;
    }

    public List<PokemonDetail> getPokemons() {
        return pokemons;
    }

    public void setPokemons(List<PokemonDetail> pokemons) {
        this.pokemons = pokemons;
    }
}
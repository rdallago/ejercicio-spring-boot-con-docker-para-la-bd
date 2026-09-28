package com.ejercicio.challange.ejercicio.domain.model;
import java.util.List;
import java.util.ArrayList;
public class User {
    private Long id;
    private String nombre;
    private Integer edad;
    private String correo;
    private List<Integer> pokemonIds = new ArrayList<>();
    private List<PokemonDetail> pokemons = new ArrayList<>();

    public User() {
    }

    public User(Long id, String nombre, Integer edad, String correo, List<Integer> pokemonIds) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.correo = correo;
        this.pokemonIds = pokemonIds != null ? pokemonIds : new ArrayList<>();
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

    public List<PokemonDetail> getPokemons() { return pokemons; }
    public void setPokemons(List<PokemonDetail> pokemons) { this.pokemons = pokemons; }
    
    // Clase estática interna para representar el detalle del Pokémon en el dominio
    public static class PokemonDetail {
        private Integer id;
        private String name;

        public PokemonDetail() {}

        public PokemonDetail(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

        public Integer getId() { return id; }
        public void setId(Integer id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }
}
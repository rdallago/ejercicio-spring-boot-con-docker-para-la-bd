package com.ejercicio.challange.ejercicio.infrastructure.adapter.out.rest;

import com.ejercicio.challange.ejercicio.domain.model.User.PokemonDetail;
import com.ejercicio.challange.ejercicio.domain.port.out.PokemonApiClientPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class PokeApiClientAdapter implements PokemonApiClientPort {
    
    private final RestClient restClient;
    // Pool de hilos para llamadas concurrentes
    private final ExecutorService executor = Executors.newCachedThreadPool(); 
    public PokeApiClientAdapter(@Value("${pokeapi.base-url}") String baseUrl) {
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Override
    public PokemonDetail getPokemonById(Integer pokemonId) {
        try {
            PokeApiResponse response = restClient.get()
                    .uri("/pokemon/{id}", pokemonId)
                    .retrieve()
                    .body(PokeApiResponse.class);

            if (response != null) {
                return new PokemonDetail(response.getId(), response.getName());
            }
        } catch (Exception e) {
            return new PokemonDetail(pokemonId, "Desconocido");
        }
        return null;
    }

    @Override
    public List<PokemonDetail> getPokemonsByIds(List<Integer> pokemonIds) {
        if (pokemonIds == null || pokemonIds.isEmpty()) {
            return new ArrayList<>();
        }

        // Ejecutar las peticiones HTTP en hilos en paralelo
        List<CompletableFuture<PokemonDetail>> futures = pokemonIds.stream()
                .map(id -> CompletableFuture.supplyAsync(() -> getPokemonById(id), executor))
                .toList();

        // Esperar a que todos los hilos terminen y consolidar resultados
        return futures.stream()
                .map(CompletableFuture::join)
                .toList();
    }

    private static class PokeApiResponse {
        private Integer id;
        private String name;

        public Integer getId() { return id; }
        public void setId(Integer id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }
}
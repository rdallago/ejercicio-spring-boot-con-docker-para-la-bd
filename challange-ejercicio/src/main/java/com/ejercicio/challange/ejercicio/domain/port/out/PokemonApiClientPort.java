package com.ejercicio.challange.ejercicio.domain.port.out;

import com.ejercicio.challange.ejercicio.domain.model.User.PokemonDetail;
import java.util.List;

public interface PokemonApiClientPort {
    PokemonDetail getPokemonById(Integer pokemonId);
    List<PokemonDetail> getPokemonsByIds(List<Integer> pokemonIds);
}
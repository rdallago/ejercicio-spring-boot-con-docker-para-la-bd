package com.ejercicio.challange.ejercicio.infrastructure.adapter.in.rest;

import com.ejercicio.challange.ejercicio.domain.model.User.PokemonDetail;
import com.ejercicio.challange.ejercicio.domain.port.out.PokemonApiClientPort;
import com.ejercicio.challange.ejercicio.infrastructure.adapter.out.persistence.entity.UserEntity;
import com.ejercicio.challange.ejercicio.infrastructure.adapter.out.persistence.repository.SpringDataUserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.context.ActiveProfiles;
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SpringDataUserRepository springDataUserRepository;

    @Autowired
    private TestDatabaseCleaner databaseCleaner;

    // Mockeamos el puerto de salida de Clean Architecture
    @MockBean
    private PokemonApiClientPort pokemonApiClientPort;

    @BeforeEach
    void setUp() {
        // Limpieza previa de la base de datos de test
        databaseCleaner.clean();
    }

    @AfterEach
    void tearDown() {
        // Limpieza posterior
        databaseCleaner.clean();
    }

    @Test
    @DisplayName("POST /api/usuarios - Crear usuario guardando pokemonIds")
    void shouldCreateUserSuccessfully() throws Exception {
        String requestBody = """
                {
                    "nombre": "Analia",
                    "edad": 60,
                    "correo": "analia@mail.com",
                    "pokemonIds": [1, 3]
                }
                """;

        mockMvc.perform(post("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre", is("Analia")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pokemonIds", hasItems(1, 3)));
    }

    @Test
    @DisplayName("POST /api/usuarios - Crear usuario guardando pokemonIds")
    void shouldCreateSecondUserSuccessfully() throws Exception {
        String requestBody = """
                {
                    "nombre": "Mariano",
                    "edad": 50,
                    "correo": "mariano@mail.com",
                    "pokemonIds": [12, 23]
                }
                """;

        mockMvc.perform(post("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre", is("Mariano")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pokemonIds", hasItems(12, 23)));
    }

    @Test
    @DisplayName("GET /api/usuarios/{id} - Obtener usuario con sus pokémons enriquecidos")
    void shouldGetUserByIdWithEnrichedPokemons() throws Exception {
        // 1. SETUP BD: Cargar un usuario previo en la BD de pruebas
        UserEntity entity = new UserEntity();
        entity.setNombre("Lidelma");
        entity.setEdad(36);
        entity.setCorreo("eri@mail.com");
        entity.setPokemonIds(List.of(1, 3));
        UserEntity savedEntity = springDataUserRepository.save(entity);

        // 2. SETUP MOCK PUERTO: Definir qué responde el puerto al consultar los IDs [1, 3]
        List<PokemonDetail> mockPokemons = List.of(
                new PokemonDetail(1, "bulbasaur"),
                new PokemonDetail(3, "venusaur")
        );
        Mockito.when(pokemonApiClientPort.getPokemonsByIds(List.of(1, 3)))
                .thenReturn(mockPokemons);

        // 3. RUN TEST: Probar el endpoint GET del controlador (acá espera los datos que asignó en memoria a ese objeto simulando la BD real), si cambian los parámetros fallaría el test
        mockMvc.perform(get("/api/usuarios/" + savedEntity.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(savedEntity.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre", is("Lidelma")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pokemonIds", hasItems(1, 3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pokemons[0].name", is("bulbasaur")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pokemons[1].name", is("venusaur")));
    }

    @Test
    @DisplayName("GET /api/usuarios/{id} - Obtener usuario con sus pokémons enriquecidos")
    void shouldGetSecondUserByIdWithEnrichedPokemons() throws Exception {
        // 1. SETUP BD: Cargar un usuario previo en la BD de pruebas
        UserEntity entity = new UserEntity();
        entity.setNombre("Ricardo");
        entity.setEdad(30);
        entity.setCorreo("rdl@mail.com");
        entity.setPokemonIds(List.of(40, 45));
        UserEntity savedEntity = springDataUserRepository.save(entity);

        // 2. SETUP MOCK PUERTO: Definir qué responde el puerto al consultar los IDs [1, 3]
        List<PokemonDetail> mockPokemons = List.of(
                new PokemonDetail(1, "wigglytuff"),
                new PokemonDetail(3, "vileplume")
        );
        Mockito.when(pokemonApiClientPort.getPokemonsByIds(List.of(40, 45)))
                .thenReturn(mockPokemons);

        // 3. RUN TEST: Probar el endpoint GET del controlador (acá espera los datos que asignó en memoria a ese objeto simulando la BD real), si cambian los parámetros fallaría el test
        mockMvc.perform(get("/api/usuarios/" + savedEntity.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(savedEntity.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre", is("Ricardo")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pokemonIds", hasItems(40, 45)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pokemons[0].name", is("wigglytuff")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pokemons[1].name", is("vileplume")));
    }

    @Test
    @DisplayName("GET /api/usuarios - Consultar usuario guardando pokemonIds")
    void shouldUserSuccessfully() throws Exception {
        UserEntity entity = new UserEntity();
        entity.setNombre("Mario");
        entity.setEdad(46);
        entity.setCorreo("mario@mail.com");
        entity.setPokemonIds(List.of(1, 4, 6));
        UserEntity savedEntity = springDataUserRepository.save(entity);

        // 2. SETUP MOCK PUERTO: Definir qué responde el puerto al consultar los IDs [1, 4,6]
        List<PokemonDetail> mockPokemons = List.of(
                new PokemonDetail(1, "bulbasaur"),
                new PokemonDetail(4, "charmander"),
                new PokemonDetail(6, "charizard")
        );
        Mockito.when(pokemonApiClientPort.getPokemonsByIds(List.of(1, 4, 6)))
                .thenReturn(mockPokemons);

        // 3. RUN TEST: Probar el endpoint GET del controlador (acá espera los datos que asignó en memoria a ese objeto simulando la BD real), si cambian los parámetros fallaría el test
        mockMvc.perform(get("/api/usuarios/" + savedEntity.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(savedEntity.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre", is("Mario")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pokemonIds", hasItems(1, 4, 6)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pokemons[0].name", is("bulbasaur")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pokemons[1].name", is("charmander")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pokemons[2].name", is("charizard")));

    }

}

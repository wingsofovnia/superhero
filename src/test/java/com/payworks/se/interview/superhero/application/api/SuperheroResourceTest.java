package com.payworks.se.interview.superhero.application.api;

import com.payworks.se.interview.superhero.application.api.dto.SuperheroData;
import com.payworks.se.interview.superhero.application.api.dto.SuperheroMapper;
import com.payworks.se.interview.superhero.domain.Superhero;
import com.payworks.se.interview.superhero.domain.SuperheroRepository;
import com.payworks.se.interview.superhero.domain.World;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebFluxTest(SuperheroResource.class)
@Import(SuperheroMapper.class)
public class SuperheroResourceTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private SuperheroRepository repository;

    private final Superhero testSuperhero = new Superhero("Name", "pseudo", World.DC, null, null, LocalDate.now());
    private final SuperheroData testSuperheroDto = new SuperheroData(
        testSuperhero.id().toString(),
        testSuperhero.name(),
        testSuperhero.pseudonym(),
        testSuperhero.world(),
        emptyList(), emptySet(),
        testSuperhero.dateOfBirth());

    @Test
    public void listAllSuperheroes() {
        when(repository.findAll()).thenReturn(Flux.just(testSuperhero));

        webClient.get().uri("/api/superheroes")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(SuperheroData.class)
            .contains(testSuperheroDto)
            .hasSize(1);

        verify(repository, times(1)).findAll();
    }

    @Test
    public void readOneSuperhero() {
        when(repository.findById(testSuperhero.id())).thenReturn(Mono.just(testSuperhero));

        webClient.get().uri("/api/superheroes/{superheroId}", testSuperhero.id())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody(SuperheroData.class)
            .isEqualTo(testSuperheroDto);

        verify(repository, times(1)).findById(testSuperhero.id());
    }

    @Test
    public void createSuperhero() {
        when(repository.save(testSuperhero)).thenReturn(Mono.just(testSuperhero));

        webClient.post().uri("/api/superheroes")
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromObject(testSuperheroDto))
            .exchange()
            .expectStatus().isCreated()
            .expectBody(SuperheroData.class)
            .isEqualTo(testSuperheroDto);

        verify(repository, times(1)).save(testSuperhero);
    }

    @Test
    public void deleteSuperhero() {
        when(repository.existsById(testSuperhero.id())).thenReturn(Mono.just(true));
        when(repository.deleteById(testSuperhero.id())).thenReturn(Mono.empty());

        webClient.delete().uri("/api/superheroes/{superheroId}", testSuperhero.id())
            .exchange()
            .expectStatus().isNoContent();

        verify(repository, times(1)).deleteById(testSuperhero.id());
    }
}

package com.payworks.se.interview.superhero.application.api;

import com.payworks.se.interview.superhero.application.api.dto.SuperheroData;
import com.payworks.se.interview.superhero.application.api.dto.SuperheroMapper;
import com.payworks.se.interview.superhero.domain.Superhero;
import com.payworks.se.interview.superhero.domain.SuperheroRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.xml.ws.Response;
import java.util.UUID;
import java.util.function.Function;

@Validated
@RestController
@RequestMapping("/api/superheroes")
public class SuperheroResource {

    private final SuperheroRepository repository;
    private final SuperheroMapper mapper;

    @Autowired
    public SuperheroResource(SuperheroRepository repository, SuperheroMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<SuperheroData> readAllSuperheroes() {
        return repository.findAll().flatMap(mapper::fromEntity);
    }

    @GetMapping(path = "/{superheroId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<SuperheroData>> readOneSuperhero(@PathVariable UUID superheroId) {
        return repository.findById(superheroId)
            .flatMap(mapper::fromEntity)
            .map(ResponseEntity::ok)
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<SuperheroData>> createSuperhero(@RequestBody SuperheroData superheroData) {
        return mapper.toEntity(superheroData)
            .flatMap(repository::save)
            .flatMap(mapper::fromEntity)
            .map(dto -> new ResponseEntity<>(dto, HttpStatus.CREATED));
    }

    @DeleteMapping(path = "/{superheroId}")
    public Mono<ResponseEntity<Void>> deleteSuperhero(@PathVariable UUID superheroId) {
        return repository.existsById(superheroId)
            .flatMap(exists -> {
                if (exists) {
                    return repository.deleteById(superheroId)
                        .thenReturn(new ResponseEntity<>(HttpStatus.NO_CONTENT));
                } else {
                    return Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND));
                }
            });
    }
}

package com.payworks.se.interview.superhero.application.api.dto;

import com.payworks.se.interview.superhero.domain.Superhero;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Component
public class SuperheroMapper {

    private final ReactiveCrudRepository<Superhero, UUID> repository;

    @Autowired
    public SuperheroMapper(ReactiveCrudRepository<Superhero, UUID> repository) {
        this.repository = repository;
    }

    public Mono<SuperheroData> fromEntity(Superhero entity) {
        val id = entity.id().toString();
        val name = entity.name();
        val pseudonym = entity.pseudonym();
        val world = entity.world();
        val allies = entity.allies().stream().map(Superhero::id).map(UUID::toString).collect(toList());
        val skills = entity.skills();
        val dateOfBirth = entity.dateOfBirth();

        return Mono.just(new SuperheroData(id, name, pseudonym, world, allies, skills, dateOfBirth));
    }

    public Mono<Superhero> toEntity(SuperheroData dto) {
        val id = isBlank(dto.getId()) ? UUID.randomUUID() : UUID.fromString(dto.getId());
        val name = dto.getName();
        val pseudonym = dto.getPseudonym();
        val world = dto.getWorld();
        val dateOfBirth = dto.getDateOfBirth();
        val alliesFlux = superheroesFromRawIds(dto.getAllies());
        val skills = dto.getSkills();

        return alliesFlux.map(allies -> new Superhero(id, name, pseudonym, world, allies, skills, dateOfBirth));
    }

    private Mono<List<Superhero>> superheroesFromRawIds(List<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return Mono.just(emptyList());
        }

        return repository.findAllById(ids.stream()
            .map(UUID::fromString)
            .collect(toSet()))
            .collectList();
    }
}

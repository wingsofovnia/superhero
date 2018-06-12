package com.payworks.se.interview.superhero.application.api.dto;

import com.payworks.se.interview.superhero.domain.Superhero;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

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

    public SuperheroData fromEntity(Superhero entity) {
        return SuperheroData.builder()
            .id(entity.toString())
            .name(entity.name())
            .pseudonym(entity.pseudonym())
            .world(entity.world())
            .allies(entity.allies().stream().map(Superhero::id).map(UUID::toString).collect(toList()))
            .dateOfBirth(entity.dateOfBirth())
            .build();
    }

    public Mono<Superhero> toEntity(SuperheroData dto) {
        val id = isBlank(dto.id()) ? UUID.randomUUID() : UUID.fromString(dto.id());
        val name = dto.name();
        val pseudonym = dto.pseudonym();
        val world = dto.world();
        val dateOfBirth = dto.dateOfBirth();

        if (dto.allies() == null) {
            return Mono.just(new Superhero(id, name, pseudonym, world, dateOfBirth));
        } else {
            val alliesFlux = superheroesFromRawIds(dto.allies());
            return alliesFlux.map(allies -> new Superhero(id, name, pseudonym, world, allies, dateOfBirth));
        }
    }

    private Mono<List<Superhero>> superheroesFromRawIds(List<String> ids) {
        return repository.findAllById(ids.stream()
            .map(UUID::fromString)
            .collect(toSet()))
            .collectList();
    }
}

package com.payworks.se.interview.superhero.domain;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SuperheroRepository extends ReactiveCrudRepository<Superhero, UUID> {
}

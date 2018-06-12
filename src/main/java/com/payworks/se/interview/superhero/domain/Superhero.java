package com.payworks.se.interview.superhero.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

@Document
@Getter @Accessors(fluent = true)
@ToString @EqualsAndHashCode(of = "id")
@NoArgsConstructor(force = true, access = AccessLevel.PACKAGE)
public class Superhero {

    @Id
    private final UUID id;

    @NotBlank
    private String name;

    private String pseudonym;

    @NotNull
    private World world;

    @DBRef
    private final List<Superhero> allies = new ArrayList<>();

    @NotNull
    private LocalDate dateOfBirth;

    public Superhero(UUID id, @NotBlank String name, String pseudonym, @NotNull World world, List<Superhero> allies,
                     @NotNull LocalDate dateOfBirth) {
        this.id = notNull(id);
        this.name = notBlank(name);
        this.pseudonym = pseudonym;
        this.world = notNull(world);
        this.dateOfBirth = notNull(dateOfBirth);

        if (allies != null) {
            this.allies.addAll(allies);
        }
    }
    public Superhero(UUID id, @NotBlank String name, String pseudonym, @NotNull World world,
                     @NotNull LocalDate dateOfBirth) {
        this(id, name, pseudonym, world, null, dateOfBirth);
    }

    public Superhero(@NotBlank String name, String pseudonym, @NotNull World world, List<Superhero> allies,
                     @NotNull LocalDate dateOfBirth) {
        this(UUID.randomUUID(), name, pseudonym, world, allies, dateOfBirth);
    }

    public Superhero(@NotBlank String name, String pseudonym, @NotNull World world, @NotNull LocalDate dateOfBirth) {
        this(name, pseudonym, world, null, dateOfBirth);
    }

    public Superhero(@NotBlank String name, @NotNull World world, @NotNull LocalDate dateOfBirth) {
        this(UUID.randomUUID(), name, null, world, null, dateOfBirth);
    }

    public void name(String name) {
        this.name = notBlank(name);
    }

    public void world(World world) {
        this.world = notNull(world);
    }

    public void dateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = notNull(dateOfBirth);
    }

    public List<Superhero> allies() {
        return Collections.unmodifiableList(allies);
    }

    public boolean addAlly(Superhero superhero) {
        if (id.equals(superhero.id))
            throw new IllegalArgumentException("A superhero cannot be an ally to him/her-self");

        return allies.add(superhero);
    }

    public boolean removeAlly(Superhero superhero) {
        return allies.remove(superhero);
    }

    public boolean removeAlly(UUID superheroId) {
        return allies.removeIf(a -> a.id.equals(superheroId));
    }
}

package com.payworks.se.interview.superhero.application.api.dto;

import com.payworks.se.interview.superhero.domain.World;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Builder @Data @Accessors(fluent = true)
public class SuperheroData {

    private final String id;

    @NotBlank
    private final String name;

    private final String pseudonym;

    @NotNull
    private final World world;

    private final List<String> allies;

    @NotNull
    private final LocalDate dateOfBirth;
}

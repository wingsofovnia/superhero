package com.payworks.se.interview.superhero.application.api.dto;

import com.payworks.se.interview.superhero.domain.World;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@ToString @EqualsAndHashCode
@RequiredArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PACKAGE)
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

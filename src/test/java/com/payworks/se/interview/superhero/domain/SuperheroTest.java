package com.payworks.se.interview.superhero.domain;

import lombok.val;
import org.junit.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SuperheroTest {

    @Test(expected = NullPointerException.class)
    public void failsOnCreatingHeroWithNullId() {
        new Superhero(null, "Name", "Pseudo", World.DC, null, null, LocalDate.now());
    }

    @Test
    public void failsOnCreatingHeroWithEmptyName() {
        assertThatThrownBy(() -> new Superhero("", World.DC, LocalDate.now()))
            .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Superhero(null, World.DC, LocalDate.now()))
            .isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> new Superhero("  ", World.DC, LocalDate.now()))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test(expected = NullPointerException.class)
    public void failsOnCreatingHeroWithNullWorld() {
        new Superhero("Name", "Pseudo", null, null, null, LocalDate.now());
    }

    @Test(expected = NullPointerException.class)
    public void failsOnCreatingHeroWithNullDateOfBirth() {
        new Superhero("Name", "Pseudo", World.DC, null, null, null);
    }

    @Test
    public void failsOnSettingEmptyName() {
        val hero = new Superhero("Name", World.DC, LocalDate.now());

        assertThatThrownBy(() -> hero.name("")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> hero.name(null)).isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> hero.name("  ")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test(expected = NullPointerException.class)
    public void failsOnSettingNullWorld() {
        val hero = new Superhero("Name", World.DC, LocalDate.now());
        hero.world(null);
    }

    @Test(expected = NullPointerException.class)
    public void failsOnSettingNullDateOfBirth() {
        val hero = new Superhero("Name", World.DC, LocalDate.now());
        hero.dateOfBirth(null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void failsOnDirectAlliesModification() {
        val hero = new Superhero("Name", World.DC, LocalDate.now());
        val heroAlly = new Superhero("Ally", World.DC, LocalDate.now());

        hero.allies().add(heroAlly);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void failsOnDirectSkillsModification() {
        val hero = new Superhero("Name", World.DC, LocalDate.now());
        hero.skills().add("snowball");
    }

    @Test(expected = IllegalArgumentException.class)
    public void failsOnAddingItselfAsAnAlly() {
        val hero = new Superhero("Name", World.DC, LocalDate.now());
        hero.addAlly(hero);
    }
}

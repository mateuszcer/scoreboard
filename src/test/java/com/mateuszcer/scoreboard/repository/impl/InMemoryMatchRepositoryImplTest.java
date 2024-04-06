package com.mateuszcer.scoreboard.repository.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mateuszcer.scoreboard.model.Match;
import com.mateuszcer.scoreboard.model.exception.DuplicateMatchException;
import com.mateuszcer.scoreboard.model.exception.MatchNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class InMemoryMatchRepositoryImplTest {

    private InMemoryMatchRepositoryImpl repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryMatchRepositoryImpl();
    }


    @Test
    void testAddMatchSuccessfully() {
        // Given
        Match match1 = new Match("HomeTeam1", "AwayTeam1");
        Match match2 = new Match("HomeTeam2", "AwayTeam2");

        // When
        match1 = repository.saveMatch(match1);
        match2= repository.saveMatch(match2);

        // Then
        assertTrue(match1.getId() < match2.getId(), "Id should be generated sequentially");
    }

    @Test
    void testAddDuplicatedMatchThrowsError() {
        // Given
        Match match1 = new Match("HomeTeam1", "AwayTeam1");
        repository.saveMatch(match1);

        // Then
        assertThrows(DuplicateMatchException.class,
                () -> repository.saveMatch(match1),
                "Expected DuplicateMatchException to be thrown when adding a duplicate match");
    }

    @Test
    void testRemoveMatchSuccessfully() {
        // Given
        Match match1 = new Match("HomeTeam1", "AwayTeam1");
        repository.saveMatch(match1);

        // When
        repository.removeMatch(match1);

        // Then
        assertEquals(0, repository.findAll().size(), "The match should be removed successfully");
    }

    @Test
    void testRemoveNonExistentMatchThrowsError() {
        // Given
        Match match1 = new Match("HomeTeam1", "AwayTeam1");

        // Then
        assertThrows(MatchNotFoundException.class,
                () -> repository.removeMatch(match1),
                "Expected MatchNotFoundException to be thrown when removing a non-existent match");
    }

    @Test
    void testUpdateMatchSuccessfully() {
        // Given
        Match match1 = new Match("HomeTeam1", "AwayTeam1");
        Match match2 = new Match("HomeTeam1", "AwayTeam1");
        repository.saveMatch(match1);

        // When
        match2.updateScore(2, 3);
        repository.updateMatch(match2);

        Match updatedMatch = repository.findByHomeTeamAndAwayTeam("HomeTeam1", "AwayTeam1").orElseThrow();

        // Then
        assertEquals(2, updatedMatch.getHomeScore(), "Home score should be updated to 2");
        assertEquals(3, updatedMatch.getAwayScore(), "Away score should be updated to 3");
    }

    @Test
    void testUpdateNonExistentMatchThrowsError() {
        // Given
        Match match1 = new Match("HomeTeam1", "AwayTeam1");

        // Then
        assertThrows(MatchNotFoundException.class,
                () -> repository.updateMatch(match1),
                "Expected MatchNotFoundException to be thrown when updating a non-existent match");
    }

    @Test
    void testFindMatchByTeams() {
        // Given
        Match match1 = new Match("HomeTeam1", "AwayTeam1");
        repository.saveMatch(match1);

        // When
        Optional<Match> foundMatch = repository.findByHomeTeamAndAwayTeam("HomeTeam1", "AwayTeam1");

        // Then
        assertTrue(foundMatch.isPresent(), "The match should be found by home and away teams");
        assertEquals(match1, foundMatch.get(), "Found match should be the same as the one added");
    }

    @Test
    void testFindNonExistentMatch() {
        // When
        Optional<Match> foundMatch = repository.findByHomeTeamAndAwayTeam("NonExistentTeam1", "NonExistentTeam2");

        // Then
        assertTrue(foundMatch.isEmpty(), "Should return an empty Optional for non-existent match");
    }
}

package com.mateuszcer.scoreboard.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class MatchTest {

    @Test
    public void testMatchInitialization() {
        // Given
        String homeTeam = "Team A";
        String awayTeam = "Team B";

        // When
        LocalDateTime beforeCreation = LocalDateTime.now();
        Match match = new Match("Team A", "Team B");
        LocalDateTime afterCreation = LocalDateTime.now();

        LocalDateTime startTime = match.getStartTime();

        // Then
        assertAll("match initialization",
                () -> assertEquals(homeTeam, match.getHomeTeam(), "Home team should match initialization value."),
                () -> assertEquals(awayTeam, match.getAwayTeam(), "Away team should match initialization value."),
                () -> assertEquals(0, match.getHomeScore(), "Initial home score should be 0."),
                () -> assertEquals(0, match.getAwayScore(), "Initial away score should be 0."),
                () -> assertTrue(!startTime.isBefore(beforeCreation) && !startTime.isAfter(afterCreation),
                        "Start time should be within the expected range of current time."));
    }

    @Test
    public void testUpdateScoreSuccessful() {
        // Given
        Match match = new Match("Home Team", "Away Team");
        int newHomeScore = 1;
        int newAwayScore = 2;

        // When
        match.updateScore(newHomeScore, newAwayScore);

        // Then
        assertEquals(1, match.getHomeScore(), "Home score should be updated to 1");
        assertEquals(2, match.getAwayScore(), "Away score should be updated to 2");
    }

    @Test
    public void testUpdateScoreWithNegativeHomeScore() {
        // Given
        Match match = new Match("Home Team", "Away Team");

        // Then
        assertThrows(IllegalArgumentException.class, () -> match.updateScore(-1, 0));
    }

}
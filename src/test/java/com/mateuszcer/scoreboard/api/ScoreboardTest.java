package com.mateuszcer.scoreboard.api;

import com.mateuszcer.scoreboard.model.Match;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ScoreboardTest {

    private Scoreboard scoreboard;

    @BeforeEach
    void setUp() {
        scoreboard = new Scoreboard();
    }

    @Test
    void testStartMatch() {
        scoreboard.startMatch("Team A", "Team B");
        List<Match> matches = scoreboard.getSummarySortedByScore();
        assertEquals(1, matches.size());
        assertEquals("Team A", matches.get(0).getHomeTeam());
        assertEquals("Team B", matches.get(0).getAwayTeam());
    }

    @Test
    void testUpdateScore() {
        scoreboard.startMatch("Team A", "Team B");
        scoreboard.updateScore("Team A", "Team B", 2, 1);
        List<Match> matches = scoreboard.getSummarySortedByScore();
        assertEquals(1, matches.size());
        assertEquals(2, matches.get(0).getHomeScore());
        assertEquals(1, matches.get(0).getAwayScore());
    }

    @Test
    void testFinishMatch() {
        scoreboard.startMatch("Team A", "Team B");
        scoreboard.finishMatch("Team A", "Team B");
        List<Match> matches = scoreboard.getSummarySortedByScore();
        assertTrue(matches.isEmpty());
    }

    @Test
    void testGetSummarySortedByScore() {
        scoreboard.startMatch("Team A", "Team B");
        scoreboard.startMatch("Team C", "Team D");
        scoreboard.startMatch("Team E", "Team F");
        scoreboard.updateScore("Team A", "Team B", 3, 2);
        scoreboard.updateScore("Team C", "Team D", 1, 1);
        scoreboard.updateScore("Team E", "Team F", 1, 1);

        List<Match> matches = scoreboard.getSummarySortedByScore();
        assertEquals(3, matches.size());

        assertEquals("Team A", matches.get(0).getHomeTeam());
        assertEquals("Team B", matches.get(0).getAwayTeam());
        assertEquals(3, matches.get(0).getHomeScore());
        assertEquals(2, matches.get(0).getAwayScore());

        assertEquals("Team E", matches.get(1).getHomeTeam());
        assertEquals("Team F", matches.get(1).getAwayTeam());
        assertEquals(1, matches.get(1).getHomeScore());
        assertEquals(1, matches.get(1).getAwayScore());

        assertEquals("Team C", matches.get(2).getHomeTeam());
        assertEquals("Team D", matches.get(2).getAwayTeam());
        assertEquals(1, matches.get(2).getHomeScore());
        assertEquals(1, matches.get(2).getAwayScore());

    }

    @Test
    void testGetStringSummary() {
        scoreboard.startMatch("Team A", "Team B");
        scoreboard.startMatch("Team C", "Team D");
        scoreboard.updateScore("Team A", "Team B", 3, 2);
        scoreboard.updateScore("Team C", "Team D", 1, 1);

        String summary = scoreboard.getStringSummary();
        assertTrue(summary.contains("Team A 3 - Team B 2"));
        assertTrue(summary.contains("Team C 1 - Team D 1"));
    }
}

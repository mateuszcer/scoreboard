package com.mateuszcer.scoreboard.logic.impl;

import com.mateuszcer.scoreboard.model.Match;
import com.mateuszcer.scoreboard.model.exception.MatchNotFoundException;
import com.mateuszcer.scoreboard.repository.MatchRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MatchServiceImplTest {

    private MatchRepository matchRepository;
    private MatchServiceImpl matchService;

    @BeforeEach
    void setUp() {
        matchRepository = mock(MatchRepository.class);
        matchService = new MatchServiceImpl(matchRepository);
    }

    @Test
    void testStartMatch() {
        matchService.startMatch("HomeTeam", "AwayTeam");
        verify(matchRepository).saveMatch(any(Match.class));
    }

    @Test
    void testUpdateScore() {
        when(matchRepository.findByHomeTeamAndAwayTeam("HomeTeam", "AwayTeam")).thenReturn(Optional.of(new Match(
                "HomeTeam",
                "AwayTeam")));
        matchService.updateScore("HomeTeam", "AwayTeam", 3, 2);
        verify(matchRepository).updateMatch(any(Match.class));
    }

    @Test
    void testFinishMatch() {
        when(matchRepository.findByHomeTeamAndAwayTeam("HomeTeam", "AwayTeam")).thenReturn(Optional.of(new Match(
                "HomeTeam",
                "AwayTeam")));
        matchService.finishMatch("HomeTeam", "AwayTeam");
        verify(matchRepository).removeMatch(any(Match.class));
    }

    @Test
    void testGetSummaryByScore() {
        Match match1 = new Match("Mexico", "Canada");
        match1.updateScore(0, 5);
        match1.setId(1L);
        Match match2 = new Match("Spain", "Brazil");
        match2.updateScore(10, 2);
        match2.setId(2L);
        Match match3 = new Match("Uruguay", "Italy");
        match3.updateScore(6, 6);
        match3.setId(3L);
        Match match4 = new Match("Argentina", "Australia");
        match4.updateScore(3, 1);
        match4.setId(4L);

        when(matchRepository.findAll()).thenReturn(List.of(match1, match2, match3, match4));

        List<Match> sortedMatches = matchService.getSummaryByScore();

        assertEquals(match3, sortedMatches.get(0));
        assertEquals(match2, sortedMatches.get(1));
        assertEquals(match1, sortedMatches.get(2));
        assertEquals(match4, sortedMatches.get(3));
    }

    @Test
    void testFinishNonExistentMatchThrowsError() {
        assertThrows(MatchNotFoundException.class, () -> matchService.finishMatch("HomeTeam", "AwayTeam"));
    }

    @Test
    void testUpdateNonExistentMatchThrowsError() {
        assertThrows(MatchNotFoundException.class, () -> matchService.updateScore("HomeTeam", "AwayTeam", 1, 1));
    }
}

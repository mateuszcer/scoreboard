package com.mateuszcer.scoreboard.logic;

import java.util.List;

import com.mateuszcer.scoreboard.model.Match;

public interface MatchService {
    void startMatch(String homeTeam, String awayTeam);

    void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore);

    void finishMatch(String homeTeam, String awayTeam);

    List<Match> getSummaryByScore();
}

package com.mateuszcer.scoreboard.repository;

import java.util.List;
import java.util.Optional;

import com.mateuszcer.scoreboard.model.Match;

public interface MatchRepository {

    void addMatch(Match match);

    void removeMatch(Match match);

    void updateMatch(Match match);

    List<Match> findAll();

    Optional<Match> findByHomeTeamAndAwayTeam(String homeTeam, String awayTeam);

}

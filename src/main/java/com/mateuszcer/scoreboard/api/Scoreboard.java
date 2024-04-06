package com.mateuszcer.scoreboard.api;

import java.util.List;

import com.mateuszcer.scoreboard.logic.MatchService;
import com.mateuszcer.scoreboard.logic.configuration.MatchServiceConfiguration;
import com.mateuszcer.scoreboard.model.Match;

/**
 * The Scoreboard class provides a high-level API for managing matches and retrieving match summaries.
 */
public class Scoreboard {

    private final MatchService matchService;

    public Scoreboard() {
        this.matchService = MatchServiceConfiguration.getMatchService();
    }

    /**
     * Starts a new match between the specified home and away teams.
     *
     * @param homeTeam The name of the home team.
     * @param awayTeam The name of the away team.
     */
    public void startMatch(String homeTeam, String awayTeam) {
        this.matchService.startMatch(homeTeam, awayTeam);
    }

    /**
     * Updates the score of an ongoing match between the specified home and away teams.
     *
     * @param homeTeam          The name of the home team.
     * @param awayTeam          The name of the away team.
     * @param absoluteHomeScore The new absolute score of the home team.
     * @param absoluteAwayScore The new absolute score of the away team.
     */
    public void updateScore(String homeTeam, String awayTeam, int absoluteHomeScore, int absoluteAwayScore) {
        this.matchService.updateScore(homeTeam, awayTeam, absoluteHomeScore, absoluteAwayScore);
    }

    /**
     * Finishes a match between the specified home and away teams.
     *
     * @param homeTeam The name of the home team.
     * @param awayTeam The name of the away team.
     */
    public void finishMatch(String homeTeam, String awayTeam) {
        this.matchService.finishMatch(homeTeam, awayTeam);
    }

    /**
     * Retrieves a summary of matches sorted by score in descending order.
     *
     * @return A list of matches sorted by score.
     */
    public List<Match> getSummarySortedByScore() {
        return this.matchService.getSummaryByScore();
    }

    /**
     * Retrieves a string representation of the match summary.
     *
     * @return A string representation of the match summary.
     */
    public String getStringSummary() {
        StringBuilder stringBuilder = new StringBuilder();
        this.matchService.getSummaryByScore().forEach(stringBuilder::append);
        return stringBuilder.toString();
    }
}

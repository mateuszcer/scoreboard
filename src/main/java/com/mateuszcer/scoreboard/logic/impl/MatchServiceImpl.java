package com.mateuszcer.scoreboard.logic.impl;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.mateuszcer.scoreboard.logic.MatchService;
import com.mateuszcer.scoreboard.model.Match;
import com.mateuszcer.scoreboard.model.exception.MatchNotFoundException;
import com.mateuszcer.scoreboard.repository.MatchRepository;

/**
 * Implementation of the MatchService interface providing operations to manage matches.
 */
public class MatchServiceImpl implements MatchService {

    private final MatchRepository matchRepository;

    public MatchServiceImpl(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    /**
     * Starts a new match between the specified home and away teams.
     * Creates a new match instance and saves it to the repository.
     *
     * @param homeTeam The name of the home team.
     * @param awayTeam The name of the away team.
     */
    @Override
    public void startMatch(String homeTeam, String awayTeam) {
        Match newMatch = new Match(homeTeam, awayTeam);

        this.matchRepository.saveMatch(newMatch);
    }

    /**
     * Updates the score of an ongoing match between the specified home and away teams.
     * Retrieves the match from the repository, updates its score, and saves it back to the repository.
     *
     * @param homeTeam  The name of the home team.
     * @param awayTeam  The name of the away team.
     * @param homeScore The new score of the home team.
     * @param awayScore The new score of the away team.
     * @throws MatchNotFoundException If the specified match is not found.
     */
    @Override
    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        Match match = this.getMatchOrThrow(homeTeam, awayTeam);
        match.updateScore(homeScore, awayScore);
        this.matchRepository.updateMatch(match);
    }

    /**
     * Finishes a match between the specified home and away teams.
     * Removes the match from the repository.
     *
     * @param homeTeam The name of the home team.
     * @param awayTeam The name of the away team.
     * @throws MatchNotFoundException If the specified match is not found.
     */
    @Override
    public void finishMatch(String homeTeam, String awayTeam) {
        Match match = this.getMatchOrThrow(homeTeam, awayTeam);

        this.matchRepository.removeMatch(match);
    }

    /**
     * Retrieves a summary of matches sorted by total score and match ID in descending order.
     *
     * @return A list of matches sorted by total score and match ID.
     */
    @Override
    public List<Match> getSummaryByScore() {
        return matchRepository.findAll()
                .stream()
                .sorted(this.getComparatorByTotalScoreAndIdDesc())
                .collect(Collectors.toList());
    }

    private Comparator<Match> getComparatorByTotalScoreAndIdDesc() {
        return Comparator.comparing((Match match) -> match.getHomeScore() + match.getAwayScore(),
                Comparator.reverseOrder()).thenComparing(Match::getId, Comparator.reverseOrder());
    }

    private Match getMatchOrThrow(String homeTeam, String awayTeam) {
        return this.matchRepository.findByHomeTeamAndAwayTeam(homeTeam, awayTeam)
                .orElseThrow(() -> new MatchNotFoundException("Match with given team names - %s : %s not found.".formatted(
                        homeTeam,
                        awayTeam)));
    }
}

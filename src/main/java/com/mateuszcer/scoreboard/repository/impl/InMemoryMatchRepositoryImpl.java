package com.mateuszcer.scoreboard.repository.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang3.SerializationUtils;

import com.mateuszcer.scoreboard.model.Match;
import com.mateuszcer.scoreboard.model.exception.DuplicateMatchException;
import com.mateuszcer.scoreboard.model.exception.MatchNotFoundException;
import com.mateuszcer.scoreboard.repository.MatchRepository;

/**
 * In-memory implementation of the MatchRepository based on HashMap.
 * Assumes that no two matches with same home team and away team can be present at the same time
 */
public class InMemoryMatchRepositoryImpl implements MatchRepository {

    private final Map<String, Match> matches = new ConcurrentHashMap<>();

    private static final AtomicLong counter = new AtomicLong(1);

    private String generateKey(String homeTeam, String awayTeam) {
        return "%s_%s".formatted(homeTeam, awayTeam);
    }

    /**
     * Saves a new Match to the map and sets unique ID.
     * If a match with the same home and away team already exists, throws DuplicateMatchException.
     *
     * @param match The Match object to add.
     * @return new Match object saved to the map with set ID
     * @throws DuplicateMatchException If a match with the same teams already exists.
     */
    @Override
    public Match saveMatch(Match match) {
        String key = generateKey(match.getHomeTeam(), match.getAwayTeam());

        if (!matches.containsKey(key)) {
            Match toSave = SerializationUtils.clone(match);
            toSave.setId(counter.getAndIncrement());
            matches.put(key, toSave);
            return toSave;
        } else {
            throw new DuplicateMatchException("Match not found for deletion: " + key);
        }
    }

    /**
     * Removes a Match from the map.
     * If the match does not exist, throws MatchNotFoundException.
     *
     * @param match The Match object to remove.
     * @throws MatchNotFoundException If the match is not found.
     */
    @Override
    public void removeMatch(Match match) {
        String key = generateKey(match.getHomeTeam(), match.getAwayTeam());

        if (matches.containsKey(key)) {
            matches.remove(key);
        } else {
            throw new MatchNotFoundException("Match not found for deletion: " + key);
        }
    }

    /**
     * Updates an existing Match in the map.
     * If the match does not exist, throws MatchNotFoundException.
     *
     * @param match The Match object to update.
     * @throws MatchNotFoundException If the match is not found in the map.
     */
    @Override
    public void updateMatch(Match match) {
        String key = generateKey(match.getHomeTeam(), match.getAwayTeam());

        if (matches.containsKey(key)) {
            matches.put(key, match);
        } else {
            throw new MatchNotFoundException("Match not found for update: " + key);
        }
    }

    /**
     * Returns a list of all Matches in the map.
     *
     * @return A List containing all Match objects.
     */
    @Override
    public List<Match> findAll() {
        return matches.values().stream().toList();
    }

    /**
     * Finds a Match by the home and away team names.
     *
     * @param homeTeam The name of the home team.
     * @param awayTeam The name of the away team.
     * @return An Optional containing the Match if found, or an empty Optional otherwise.
     */
    @Override
    public Optional<Match> findByHomeTeamAndAwayTeam(String homeTeam, String awayTeam) {
        return Optional.ofNullable(matches.get(this.generateKey(homeTeam, awayTeam)));
    }
}

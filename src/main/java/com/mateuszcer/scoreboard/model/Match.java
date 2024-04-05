package com.mateuszcer.scoreboard.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Match {

    private final String homeTeam;
    private final String awayTeam;
    private int homeScore;
    private int awayScore;
    private final LocalDateTime startTime;

    public Match(String homeTeam, String awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = 0;
        this.awayScore = 0;
        this.startTime = LocalDateTime.now();
    }

    public void updateScore(int homeScore, int awayScore) {
        if (homeScore < 0 || awayScore < 0) {
            throw new IllegalArgumentException("Scores must not be negative");
        }
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Match match = (Match) o;

        if (homeScore != match.homeScore || awayScore != match.awayScore || !Objects.equals(homeTeam, match.homeTeam)
                || !Objects.equals(awayTeam, match.awayTeam)) {
            return false;
        }
        return startTime.equals(match.startTime);
    }

    @Override
    public int hashCode() {
        int result = homeTeam != null ? homeTeam.hashCode() : 0;
        result = 31 * result + (awayTeam != null ? awayTeam.hashCode() : 0);
        result = 31 * result + homeScore;
        result = 31 * result + awayScore;
        result = 31 * result + startTime.hashCode();
        return result;
    }
}

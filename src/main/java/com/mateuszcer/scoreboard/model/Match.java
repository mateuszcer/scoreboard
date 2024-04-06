package com.mateuszcer.scoreboard.model;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.Objects;

import com.mateuszcer.scoreboard.model.asbtract.Entity;

public class Match extends Entity {

    @Serial
    private static final long serialVersionUID = 8938107247272980597L;
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
        return this.homeTeam;
    }

    public String getAwayTeam() {
        return this.awayTeam;
    }

    public int getHomeScore() {
        return this.homeScore;
    }

    public int getAwayScore() {
        return this.awayScore;
    }

    public LocalDateTime getStartTime() {
        return this.startTime;
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

        if (this.homeScore != match.homeScore || this.awayScore != match.awayScore || !Objects.equals(this.homeTeam,
                match.homeTeam) || !Objects.equals(this.awayTeam, match.awayTeam)) {
            return false;
        }
        return this.startTime.equals(match.startTime);
    }

    @Override
    public int hashCode() {
        int result = this.homeTeam != null ? this.homeTeam.hashCode() : 0;
        result = 31 * result + (this.awayTeam != null ? this.awayTeam.hashCode() : 0);
        result = 31 * result + this.homeScore;
        result = 31 * result + this.awayScore;
        result = 31 * result + this.startTime.hashCode();
        return result;
    }
}

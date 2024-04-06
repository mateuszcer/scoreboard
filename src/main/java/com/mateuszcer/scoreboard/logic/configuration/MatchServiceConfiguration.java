package com.mateuszcer.scoreboard.logic.configuration;

import com.mateuszcer.scoreboard.logic.MatchService;
import com.mateuszcer.scoreboard.logic.impl.MatchServiceImpl;
import com.mateuszcer.scoreboard.repository.impl.InMemoryMatchRepositoryImpl;

public class MatchServiceConfiguration {

    public static MatchService getMatchService() {
        return new MatchServiceImpl(new InMemoryMatchRepositoryImpl());
    }
}

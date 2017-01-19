package com.uran.gamblingstation.service.processor;

public interface RaceProcessor {

    void forbidStakeEditing(final int raceId);

    void process(int horseId, int raceId);

}

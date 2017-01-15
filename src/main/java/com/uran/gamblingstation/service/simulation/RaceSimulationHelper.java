package com.uran.gamblingstation.service.simulation;

import com.uran.gamblingstation.model.Horse;

import java.util.List;

public interface RaceSimulationHelper {

    void createBots(int max);

    void initBots(int min, int max);

    void clearWallets();

    void fillWallets();

    void killBots();

    void startGamble();

    List<Horse> getHorsesForRace();

}

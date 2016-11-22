package com.uran.gamblingstation.service.simulation;

import com.uran.gamblingstation.model.Horse;

import java.util.List;

public interface RaceSimulationHelper {
    void createBots();
    void fillWallets();
    void killBots();
    void startGamble();
    void selectHorsesForRace();
    List<Horse> getHorsesForRace();
}

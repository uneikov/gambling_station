package com.uran.gamblingstation.service.processor;

import java.time.LocalDateTime;

public interface RaceProcessor {
    void process(int horseId, LocalDateTime start, LocalDateTime end);
}

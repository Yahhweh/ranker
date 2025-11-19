package kegly.organisation.raceranker.services;

import kegly.organisation.raceranker.models.Driver;
import kegly.organisation.raceranker.models.LapResult;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class LapResultCreator {

    public List<LapResult> createLapResult(List<Driver> drivers, Map<String, Duration> durations) {
        return drivers.stream()
                .map(driver -> {
                    Duration duration = durations.get(driver.getAbbreviation());
                    if (duration == null) {
                        return null;
                    }

                    return new LapResult(duration, driver);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
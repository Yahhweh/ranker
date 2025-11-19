package kegly.organisation.raceranker.services;

import kegly.organisation.raceranker.models.Driver;
import kegly.organisation.raceranker.models.LapResult;

import java.time.Duration;
import java.util.*;

public class LapResultCreator {

    public List<LapResult> createLapResult(Map<String, Driver> driversByAbbreviation,
                                           Map<String, Duration> lapTimesByAbbreviation) {
        List<LapResult> result = new ArrayList<>();

        for (Map.Entry<String, Duration> timeEntry : lapTimesByAbbreviation.entrySet()) {

            String abbreviation = timeEntry.getKey();
            Duration duration = timeEntry.getValue();

            Driver driverData = driversByAbbreviation.get(abbreviation);

            if (driverData != null) {
                LapResult lap = new LapResult(duration, driverData);
                result.add(lap);
            }
        }

        return result;
    }
}
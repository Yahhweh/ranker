package kegly.organisation.raceranker.services;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;


public class DurationFinder {

    public Map<String, Duration> calculateDifference(
            Map<String, LocalDateTime> StartByAbbreviation, Map<String, LocalDateTime> EndByAbbreviation) {
        Map<String, Duration> result = new HashMap<>();

        for (Map.Entry<String, LocalDateTime> startEntry : StartByAbbreviation.entrySet()) {
            String key = startEntry.getKey();

            LocalDateTime endTime = EndByAbbreviation.get(key);

            if (endTime != null) {

                LocalDateTime startTime = startEntry.getValue();

                result.put(key, Duration.between(startTime, endTime));
            }
        }
        return result;
    }
}
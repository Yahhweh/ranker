package kegly.organisation.raceranker.services;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class DurationFinder {

    public Map<String, Duration> calculateDifference(
            Map<String, LocalDateTime> startByAbbreviation, Map<String, LocalDateTime> endByAbbreviation) {

        return startByAbbreviation.entrySet().stream()
                .filter(startEntry -> endByAbbreviation.get(startEntry.getKey())!= null)
                .collect(Collectors.toMap(
                        key -> key.getKey(),
                        startEntry -> Duration.between(startEntry.getValue(), endByAbbreviation.get(startEntry.getKey()))
                )
                );

    }
}
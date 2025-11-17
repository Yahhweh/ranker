package kegly.organisation.raceranker;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class CalculatingTime {

    public Map<String, Duration> calculateDifference(
            Map<String, LocalDateTime> start, Map<String, LocalDateTime> end) {

        Map<String, Duration> result = new HashMap<>();

        for (Map.Entry<String, LocalDateTime> startEntry : start.entrySet()) {
            String key = startEntry.getKey();

            LocalDateTime endTime = end.get(key);

            if (endTime != null) {

                LocalDateTime startTime = startEntry.getValue();

                result.put(key, Duration.between(startTime, endTime));
            }
        }
        return result;
    }
}
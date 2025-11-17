package kegly.organisation.raceranker;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeStructurer implements DataStructurer<LocalDateTime> {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss.SSS");

    @Override
    public Map<String, LocalDateTime> parse(List<String> lines) {
        Map<String, LocalDateTime> result = new HashMap<>();

        for (String line : lines) {

            String abbreviation = line.substring(0, 3);
            String timeString = line.substring(3);

            LocalDateTime time = LocalDateTime.parse(timeString.trim(), FORMATTER);

            result.put(abbreviation, time);
        }
        return result;
    }
}
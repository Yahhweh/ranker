package kegly.organisation.raceranker.parsers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TimeParser implements DataParser<Map<String, LocalDateTime>> {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss.SSS");
    private static final int ABBREVIATION_LENGTH = 3;

    @Override
    public Map<String, LocalDateTime> parse(Stream<String> lines) {
        return lines.collect(Collectors.toMap(
                line -> line.substring(0, ABBREVIATION_LENGTH), // Ключ (SVF)
                line -> LocalDateTime.parse(line.substring(ABBREVIATION_LENGTH).trim(), FORMATTER)                              // Значение (Время)
        ));
    }
}
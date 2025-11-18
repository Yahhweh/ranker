package kegly.organisation.raceranker.parsers;

import kegly.organisation.raceranker.interfaces.DataParser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class TimeParser implements DataParser<LocalDateTime> {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss.SSS");

    @Override
    public Map<String, LocalDateTime> parse(Stream<String> lines) {
        return lines
                .collect(Collectors.toMap(
                        line -> line.substring(0, 3),
                        line -> LocalDateTime.parse(line.substring(3).trim(), FORMATTER)));

    }
}
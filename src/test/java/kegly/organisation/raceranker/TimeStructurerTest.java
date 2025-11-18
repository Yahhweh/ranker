package kegly.organisation.raceranker;

import kegly.organisation.raceranker.parsers.TimeParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


class TimeStructurerTest {

    TimeParser timeStructurer;

    @BeforeEach
    void setUp() {
        timeStructurer = new TimeParser();
    }

    @Test
    void parse_throwError_whenIncorrectTime() {
        String invalidLine = "SVF2018-055-24_12:02:58.917";
        Stream<String> invalidInput = Stream.of(invalidLine);

        assertThrows(
                DateTimeParseException.class, () -> {
                    timeStructurer.parse(invalidInput);
                });
    }

    @Test
    void parse_returnMap_whenCorrectTime() {
        Stream<String> validInput = Stream.of(
                "SVF2018-05-24_12:02:58.917",
                "DRR2018-05-24_12:03:01.002"
        );

        Map<String, LocalDateTime> expectedMap = new HashMap<>();
        expectedMap.put("SVF", LocalDateTime.of(2018, 5, 24, 12, 2, 58, 917000000));
        expectedMap.put("DRR", LocalDateTime.of(2018, 5, 24, 12, 3, 1, 2000000));

        Map<String, LocalDateTime> actualMap = timeStructurer.parse(validInput);

        assertEquals(expectedMap, actualMap);
    }
}


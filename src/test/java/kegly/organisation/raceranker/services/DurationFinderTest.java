package kegly.organisation.raceranker.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


class DurationFinderTest {

    private DurationFinder durationFinder;

    @BeforeEach
    void setUp() {
        durationFinder = new DurationFinder();
    }

    @Test
    void calculateDifference_returnMapWithDurations_whenKeysMatch() {
        LocalDateTime baseTime = LocalDateTime.of(2024, 1, 1, 12, 0, 0);

        Map<String, LocalDateTime> start = Map.of(
                "SVF", baseTime.minusMinutes(1),
                "DRR", baseTime
        );

        Map<String, LocalDateTime> end = Map.of(
                "SVF", baseTime,
                "DRR", baseTime.plusSeconds(72).plusNanos(13000000)
        );

        Map<String, Duration> expected = Map.of(
                "SVF", Duration.ofMinutes(1),
                "DRR", Duration.ofSeconds(72, 13000000)
        );

        Map<String, Duration> result = durationFinder.calculateDifference(start, end);

        assertEquals(expected, result);
    }

    @Test
    void calculateDifference_ignoreKeysNotInEndMap() {
        LocalDateTime baseTime = LocalDateTime.of(2024, 1, 1, 12, 0, 0);

        Map<String, LocalDateTime> start = Map.of(
                "SVF", baseTime,
                "DRR", baseTime
        );

        Map<String, LocalDateTime> end = Map.of(
                "SVF", baseTime.plusSeconds(30)
        );

        Map<String, Duration> expected = Map.of(
                "SVF", Duration.ofSeconds(30)
        );

        Map<String, Duration> result = durationFinder.calculateDifference(start, end);

        assertEquals(expected, result);
        assertEquals(1, result.size());
    }

    @Test
    void calculateDifference_returnNegativeDuration_whenEndIsBeforeStart() {
        LocalDateTime baseTime = LocalDateTime.of(2024, 1, 1, 12, 0, 0);

        Map<String, LocalDateTime> start = Map.of("SVF", baseTime.plusSeconds(30));
        Map<String, LocalDateTime> end = Map.of("SVF", baseTime);

        Map<String, Duration> expected = Map.of(
                "SVF", Duration.ofSeconds(-30)
        );

        Map<String, Duration> result = durationFinder.calculateDifference(start, end);

        assertEquals(expected, result);
    }

    @Test
    void calculateDifference_returnEmptyMap_whenStartMapIsEmpty() {
        Map<String, LocalDateTime> start = Collections.emptyMap();
        Map<String, LocalDateTime> end = Map.of("SVF", LocalDateTime.now());

        Map<String, Duration> result = durationFinder.calculateDifference(start, end);

        assertTrue(result.isEmpty());
    }

    @Test
    void calculateDifference_throwNullPointerException_whenStartMapIsNull() {
        Map<String, LocalDateTime> end = Map.of("SVF", LocalDateTime.now());

        assertThrows(
                NullPointerException.class,
                () -> durationFinder.calculateDifference(null, end)
        );
    }

    @Test
    void calculateDifference_throwNullPointerException_whenEndMapIsNull() {
        Map<String, LocalDateTime> start = Map.of("SVF", LocalDateTime.now());

        assertThrows(
                NullPointerException.class,
                () -> durationFinder.calculateDifference(start, null)
        );
    }
}
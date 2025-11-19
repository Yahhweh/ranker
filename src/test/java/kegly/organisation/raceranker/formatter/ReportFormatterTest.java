package kegly.organisation.raceranker.formatter;

import kegly.organisation.raceranker.models.Driver;
import kegly.organisation.raceranker.models.LapResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReportFormatterTest {

    private ReportFormatter formatter;
    private Driver driver1;
    private Driver driver2;

    @BeforeEach
    void setUp() {
        formatter = new ReportFormatter();
        driver1 = new Driver("DRR", "Daniel Ricciardo", "RED BULL RACING TAG HEUER");
        driver2 = new Driver("SVF", "Sebastian Vettel", "FERRARI");
    }

    @Test
    void format_shouldReturnFullReportWithSeparator_whenLimitIsReached() {
        Duration time1 = Duration.ofMinutes(1).plusSeconds(12).plusMillis(913);
        Duration time2 = Duration.ofMinutes(1).plusSeconds(13).plusMillis(0);

        List<LapResult> results = Arrays.asList(
                new LapResult(time1, driver1),
                new LapResult(time2, driver2)
        );

        int qualificationLimit = 1;

        String expected = "1.  Daniel Ricciardo     | RED BULL RACING TAG HEUER | 1:12.913" + System.lineSeparator() +
                "------------------------------------------------------------------------" + System.lineSeparator() +
                "2.  Sebastian Vettel     | FERRARI                   | 1:13.000" + System.lineSeparator();

        String result = formatter.format(results, qualificationLimit);

        assertEquals(expected, result);
    }

    @Test
    void format_shouldFormatDurationCorrectly_whenSecondsNeedLeadingZero() {
        Duration zeroSecondsTime = Duration.ofMinutes(1).plusSeconds(9).plusMillis(5);
        List<LapResult> results = Collections.singletonList(new LapResult(zeroSecondsTime, driver1));

        String result = formatter.format(results, 10);

        assertTrue(result.contains("1:09.005"));
    }

    @Test
    void format_shouldFormatDurationCorrectly_whenTimeIsLessThanOneMinute() {
        Duration shortTime = Duration.ofSeconds(45).plusMillis(100);
        List<LapResult> results = Collections.singletonList(new LapResult(shortTime, driver1));

        String result = formatter.format(results, 10);

        assertTrue(result.contains("0:45.100"));
    }

    @Test
    void format_shouldThrowException_whenDurationIsNegative() {
        Duration negativeTime = Duration.ofMillis(-100);
        List<LapResult> results = Collections.singletonList(new LapResult(negativeTime, driver1));

        assertThrows(IllegalArgumentException.class, () -> {
            formatter.format(results, 10);
        });
    }
}
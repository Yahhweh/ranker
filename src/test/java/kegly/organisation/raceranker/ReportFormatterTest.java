package kegly.organisation.raceranker;

import kegly.organisation.raceranker.formatter.ReportFormatter;
import kegly.organisation.raceranker.models.Driver;
import kegly.organisation.raceranker.models.LapResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ReportFormatterTest {

    private ReportFormatter formatter;
    private Driver testDriver;

    @BeforeEach
    void setUp() {
        formatter = new ReportFormatter();
        testDriver = new Driver("DRR", "Daniel Ricciardo", "RED BULL RACING TAG HEUER");
    }

    @Test
    void formatLine_returnCorrectlyFormattedString_whenDataIsValid() {
        Duration time = Duration.ofMinutes(1).plusSeconds(12).plusMillis(913);
        LapResult lapResult = new LapResult(time, testDriver);

        String expected = "1.  Daniel Ricciardo     | RED BULL RACING TAG HEUER | 1:12.913";

        String result = formatter.formatLine(1, lapResult);

        assertEquals(expected, result);
    }

    @Test
    void formatDuration_returnCorrectlyFormattedDuration_whenTimeIsPositive() {
        Duration positiveTime = Duration.ofMinutes(1).plusSeconds(45).plusMillis(5);
        String expected = "1:45.005";

        String result = formatter.formatDuration(positiveTime);

        assertEquals(expected, result);
    }

    @Test
    void formatDuration_returnCorrectlyFormattedDuration_whenTimeIsNegative() {
        Duration negativeTime = Duration.ofMinutes(5).plusSeconds(3).plusMillis(789).negated();
        String expected = "-5:03.789";

        String result = formatter.formatDuration(negativeTime);

        assertEquals(expected, result);
    }

    @Test
    void formatDuration_returnCorrectlyFormattedDuration_whenTimeIsLessThanOneMinute() {
        Duration shortTime = Duration.ofSeconds(45).plusMillis(100);
        String expected = "0:45.100";

        String result = formatter.formatDuration(shortTime);

        assertEquals(expected, result);
    }

    @Test
    void formatDuration_returnCorrectlyFormattedDuration_whenSecondsNeedLeadingZero() {
        Duration zeroSecondsTime = Duration.ofMinutes(1).plusSeconds(9).plusMillis(500);
        String expected = "1:09.500";

        String result = formatter.formatDuration(zeroSecondsTime);

        assertEquals(expected, result);
    }
}
package kegly.organisation.raceranker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class LapResultCreaterTest {

    private LapResultCreater lapResultCreater;
    private Driver driver1;
    private Driver driver2;
    private Duration time1;
    private Duration time2;

    @BeforeEach
    void setUp() {
        lapResultCreater = new LapResultCreater();
        driver1 = new Driver("DRR", "Daniel Ricciardo", "RED BULL");
        driver2 = new Driver("SVF", "Sebastian Vettel", "FERRARI");
        time1 = Duration.ofSeconds(72, 13000000);
        time2 = Duration.ofSeconds(72, 415000000);
    }

    @Test
    void createLapResult_returnFullList_whenAllKeysMatch() {
        Map<String, Driver> drivers = Map.of("DRR", driver1, "SVF", driver2);
        Map<String, Duration> times = Map.of("DRR", time1, "SVF", time2);

        List<LapResult> expected = List.of(
                new LapResult(time1, driver1),
                new LapResult(time2, driver2)
        );

        List<LapResult> result = lapResultCreater.createLapResult(drivers, times);

        assertEquals(expected.size(), result.size());
        assertTrue(result.containsAll(expected));
    }

    @Test
    void createLapResult_returnPartialList_whenSomeKeysMissingInDriverMap() {
        Map<String, Driver> drivers = Map.of("DRR", driver1);
        Map<String, Duration> times = Map.of("DRR", time1, "SVF", time2);

        List<LapResult> expected = List.of(
                new LapResult(time1, driver1)
        );

        List<LapResult> result = lapResultCreater.createLapResult(drivers, times);

        assertEquals(expected, result);
    }

    @Test
    void createLapResult_throwNullPointerException_whenTimeMapIsNull() {
        Map<String, Driver> drivers = Map.of("DRR", driver1);

        assertThrows(
                NullPointerException.class,
                () -> lapResultCreater.createLapResult(drivers, null)
        );
    }
}
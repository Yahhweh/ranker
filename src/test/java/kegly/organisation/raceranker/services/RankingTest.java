package kegly.organisation.raceranker.services;

import kegly.organisation.raceranker.models.Driver;
import kegly.organisation.raceranker.models.LapResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RankingTest {

    private Ranking ranking;
    private Driver driver1, driver2, driver3;
    private LapResult resultSlow, resultMedium, resultFast;

    @BeforeEach
    void setUp() {
        ranking = new Ranking();

        driver1 = new Driver("DRR", "Daniel", "RED BULL");
        driver2 = new Driver("SVF", "Sebastian", "FERRARI");
        driver3 = new Driver("LHM", "Lewis", "MERCEDES");

        resultSlow = new LapResult(Duration.ofSeconds(75), driver1);
        resultMedium = new LapResult(Duration.ofSeconds(72), driver2);
        resultFast = new LapResult(Duration.ofSeconds(70), driver3);
    }

    @Test
    void sortResults_returnSortedList_whenListIsUnsorted() {
        List<LapResult> unsorted = new ArrayList<>();
        unsorted.add(resultSlow);
        unsorted.add(resultFast);
        unsorted.add(resultMedium);

        List<LapResult> expected = List.of(resultFast, resultMedium, resultSlow);

        List<LapResult> result = ranking.sortResults(unsorted);

        assertEquals(expected, result);
    }

    @Test
    void sortResults_returnEmptyList_whenListIsEmpty() {
        List<LapResult> emptyList = new ArrayList<>();
        List<LapResult> result = ranking.sortResults(emptyList);

        assertTrue(result.isEmpty());
    }

    @Test
    void sortResults_throwNullPointerException_whenListIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> ranking.sortResults(null)
        );
    }
}
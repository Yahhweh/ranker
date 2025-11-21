package kegly.organisation.raceranker.controller;

import kegly.organisation.raceranker.models.Driver;
import kegly.organisation.raceranker.models.LapResult;
import kegly.organisation.raceranker.parsers.DataParser;
import kegly.organisation.raceranker.printers.ReportPrinter;
import kegly.organisation.raceranker.services.DurationFinder;
import kegly.organisation.raceranker.services.LapResultCreator;
import kegly.organisation.raceranker.services.Ranking;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RaceControllerTest {

    @Mock
    private DataParser<List<Driver>> abbreviationParser;
    @Mock
    private DataParser<Map<String, LocalDateTime>> timeParser;
    @Mock
    private DurationFinder durationFinder;
    @Mock
    private LapResultCreator lapResultCreator;
    @Mock
    private Ranking ranker;
    @Mock
    private ReportPrinter printer;

    private RaceController raceController;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        raceController = new RaceController(
                abbreviationParser,
                timeParser,
                durationFinder,
                lapResultCreator,
                ranker,
                printer
        );
    }

    @Test
    void makeReport_printReport_whenFilesAreValid() throws IOException {
        Path abbrFile = createDummyFile("abbreviations.txt");
        Path startFile = createDummyFile("start.log");
        Path endFile = createDummyFile("end.log");

        List<Driver> mockDrivers = List.of(new Driver("DR1", "Name", "Team"));
        Map<String, LocalDateTime> mockStartTimes = Map.of("DR1", LocalDateTime.now());
        Map<String, LocalDateTime> mockEndTimes = Map.of("DR1", LocalDateTime.now());
        Map<String, Duration> mockDurations = Map.of("DR1", Duration.ZERO);
        List<LapResult> mockResults = Collections.singletonList(mock(LapResult.class));

        when(abbreviationParser.parse(any(Stream.class))).thenReturn(mockDrivers);
        when(timeParser.parse(any(Stream.class))).thenReturn(mockStartTimes).thenReturn(mockEndTimes);
        when(durationFinder.calculateDifference(mockStartTimes, mockEndTimes)).thenReturn(mockDurations);
        when(lapResultCreator.createLapResult(mockDrivers, mockDurations)).thenReturn(mockResults);
        when(ranker.sortResults(mockResults)).thenReturn(mockResults);

        raceController.makeReport(
                abbrFile.toString(),
                startFile.toString(),
                endFile.toString(),
                15
        );

        verify(printer, times(1)).print(mockResults, 15);
    }

    @Test
    void makeReport_throwIOException_whenFilesAreNotExist() {
        String abbrFile = tempDir.resolve("missing_abbr.txt").toString();
        String startFile = tempDir.resolve("missing_start.log").toString();
        String endFile = tempDir.resolve("missing_end.log").toString();

        assertThrows(IOException.class, () -> {
            raceController.makeReport(
                    abbrFile.toString(),
                    startFile.toString(),
                    endFile.toString(),
                    15
            );
        });
    }

    private Path createDummyFile(String name) throws IOException {
        Path file = tempDir.resolve(name);
        Files.write(file, List.of("DUMMY_CONTENT"));
        return file;
    }
}
package kegly.organisation.raceranker.controller;

import kegly.organisation.raceranker.parsers.DataParser;
import kegly.organisation.raceranker.models.Driver;
import kegly.organisation.raceranker.models.LapResult;
import kegly.organisation.raceranker.printers.ReportPrinter;
import kegly.organisation.raceranker.services.DurationFinder;
import kegly.organisation.raceranker.services.LapResultCreator;
import kegly.organisation.raceranker.services.Ranking;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class RaceController {

    private final DataParser<List<Driver>> abbreviationParser;
    private final DataParser<Map<String, LocalDateTime>> timeParser;
    private final DurationFinder durationFinder;
    private final LapResultCreator lapResultCreator;
    private final Ranking ranker;
    private final ReportPrinter printer;

    public RaceController(DataParser<List<Driver>> abbreviationParser, DataParser<Map<String, LocalDateTime>> timeParser, DurationFinder durationFinder, LapResultCreator lapResultCreator, Ranking ranker, ReportPrinter printer) {
        this.abbreviationParser = abbreviationParser;
        this.timeParser = timeParser;
        this.durationFinder = durationFinder;
        this.lapResultCreator = lapResultCreator;
        this.ranker = ranker;
        this.printer = printer;
    }

    public void makeReport(String abbreviationsFile, String startFile, String endFile, int limit) {
        try {

            List<Driver> drivers = processFile(abbreviationsFile, abbreviationParser);
            Map<String, LocalDateTime> startTimes = processFile(startFile, timeParser);
            Map<String, LocalDateTime> endTimes = processFile(endFile, timeParser);

            Map<String, Duration> durations = durationFinder.calculateDifference(startTimes, endTimes);
            List<LapResult> unsortedResults = lapResultCreator.createLapResult(drivers, durations);
            List<LapResult> sortedResults = ranker.sortResults(unsortedResults);

            printer.print(sortedResults, limit);

        } catch (IOException e) {
            System.err.println("Error while reading a file"
                    + abbreviationsFile + ", " + startFile + ", " + endFile
                    + " is located on wrong file");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Some error appeared");
            e.printStackTrace();
        }
    }

    private <T> T processFile(String filename, DataParser<T> parser) throws IOException {
        try (Stream<String> lines = Files.lines(Paths.get(filename))) {
            return parser.parse(lines);
        }
    }
}

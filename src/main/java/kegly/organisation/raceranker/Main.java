package kegly.organisation.raceranker;

import kegly.organisation.raceranker.interfaces.DataParser;
import kegly.organisation.raceranker.parsers.AbbreviationParser;
import kegly.organisation.raceranker.parsers.DriverParser;
import kegly.organisation.raceranker.printers.ReportPrinter;
import kegly.organisation.raceranker.services.LapResultCreator;
import kegly.organisation.raceranker.models.Driver;
import kegly.organisation.raceranker.models.LapResult;
import kegly.organisation.raceranker.parsers.TimeParser;
import kegly.organisation.raceranker.services.DurationFinder;
import kegly.organisation.raceranker.services.Ranking;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Main {
    private static final String ABBREVIATIONS_FILE = "abbreviation.log";
    private static final String START_LOG_FILE = "start.log";
    private static final String END_LOG_FILE = "end.log";

    public static void main(String[] args) {
        DriverParser driverParser = new DriverParser();
        DataParser<Driver> abbreviationParser = new AbbreviationParser(driverParser);
        DataParser<LocalDateTime> timeParser = new TimeParser();
        DurationFinder calculator = new DurationFinder();
        LapResultCreator assembler = new LapResultCreator();
        Ranking ranker = new Ranking();
        ReportPrinter printer = new ReportPrinter();
        final int QUALIFICATION_LIMIT = 15;

        try {

            Map<String, Driver> drivers = processFile(ABBREVIATIONS_FILE, abbreviationParser);
            Map<String, LocalDateTime> startTimes = processFile(START_LOG_FILE, timeParser);
            Map<String, LocalDateTime> endTimes = processFile(END_LOG_FILE, timeParser);

            Map<String, Duration> durations = calculator.calculateDifference(startTimes, endTimes);
            List<LapResult> unsortedResults = assembler.createLapResult(drivers, durations);


            List<LapResult> sortedResults = ranker.sortResults(unsortedResults);

            printer.print(sortedResults, QUALIFICATION_LIMIT);

        } catch (IOException e) {
            System.err.println("Error while reading a file"
                    + ABBREVIATIONS_FILE + ", " + START_LOG_FILE + ", " + END_LOG_FILE
                    + " is located on wrong file");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Some error appeared");
            e.printStackTrace();
        }
    }

    private static <T> Map<String, T> processFile(String filename, DataParser<T> parser) throws IOException {
        try (Stream<String> lines = Files.lines(Paths.get(filename))) {
            return parser.parse(lines);
        }
    }
}
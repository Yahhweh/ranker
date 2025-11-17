package kegly.organisation.raceranker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class Main {

    private static final String ABBREVIATIONS_FILE = "abbreviation.log";
    private static final String START_LOG_FILE = "start.log";
    private static final String END_LOG_FILE = "end.log";

    public static void main(String[] args) {


        DataStructurer<Driver> abbreviationParser = new AbbreviationStructurer();
        DataStructurer<LocalDateTime> timeParser = new TimeStructurer();
        CalculatingTime calculator = new CalculatingTime();
        LapResultCreater assembler = new LapResultCreater();
        Ranking ranker = new Ranking();
        ReportPrinter printer = new ReportPrinter();

        try {
            List<String> abbrevLines = Files.readAllLines(Paths.get(ABBREVIATIONS_FILE));
            List<String> startLines = Files.readAllLines(Paths.get(START_LOG_FILE));
            List<String> endLines = Files.readAllLines(Paths.get(END_LOG_FILE));

            Map<String, Driver> drivers = abbreviationParser.parse(abbrevLines);
            Map<String, LocalDateTime> startTimes = timeParser.parse(startLines);
            Map<String, LocalDateTime> endTimes = timeParser.parse(endLines);

            Map<String, Duration> durations = calculator.calculateDifference(startTimes, endTimes);
            List<LapResult> unsortedResults = assembler.createLapResult(drivers, durations);


            List<LapResult> sortedResults = ranker.sortResults(unsortedResults);

            printer.print(sortedResults);

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
}
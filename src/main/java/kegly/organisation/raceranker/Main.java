package kegly.organisation.raceranker;

import kegly.organisation.raceranker.controller.RaceController;
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
    private static final int LIMIT = 15;

    public static void main(String[] args) {
        DriverParser driverParser = new DriverParser();
        DataParser<Driver> abbreviationParser = new AbbreviationParser(driverParser);
        DataParser<LocalDateTime> timeParser = new TimeParser();
        DurationFinder durationFinder = new DurationFinder();
        LapResultCreator lapResultCreator = new LapResultCreator();
        Ranking ranker = new Ranking();
        ReportPrinter printer = new ReportPrinter();

        RaceController starter = new RaceController(abbreviationParser, timeParser, durationFinder, lapResultCreator, ranker, printer);

        starter.makeReport(ABBREVIATIONS_FILE, START_LOG_FILE,END_LOG_FILE, LIMIT);
    }
}
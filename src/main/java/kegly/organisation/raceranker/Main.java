package kegly.organisation.raceranker;

import kegly.organisation.raceranker.controller.RaceController;
import kegly.organisation.raceranker.formatter.ReportFormatter;
import kegly.organisation.raceranker.parsers.DataParser;
import kegly.organisation.raceranker.parsers.DriverParser;
import kegly.organisation.raceranker.printers.ReportPrinter;
import kegly.organisation.raceranker.services.LapResultCreator;
import kegly.organisation.raceranker.models.Driver;
import kegly.organisation.raceranker.parsers.TimeParser;
import kegly.organisation.raceranker.services.DurationFinder;
import kegly.organisation.raceranker.services.Ranking;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

public class Main {

    private static final String ABBREVIATIONS_FILE = "abbreviation.log";
    private static final String START_LOG_FILE = "start.log";
    private static final String END_LOG_FILE = "end.log";
    private static final int LIMIT = 15;

    public static void main(String[] args) throws IOException {
        DataParser<List<Driver>> driverParser = new DriverParser();
        DataParser<Map<String, LocalDateTime>> timeParser = new TimeParser();
        DurationFinder durationFinder = new DurationFinder();
        LapResultCreator lapResultCreator = new LapResultCreator();
        Ranking ranker = new Ranking();
        ReportFormatter reportFormatter = new ReportFormatter();
        ReportPrinter printer = new ReportPrinter(reportFormatter);

        RaceController starter = new RaceController(driverParser, timeParser, durationFinder, lapResultCreator, ranker, printer);

        starter.makeReport(ABBREVIATIONS_FILE, START_LOG_FILE,END_LOG_FILE, LIMIT);
    }
}
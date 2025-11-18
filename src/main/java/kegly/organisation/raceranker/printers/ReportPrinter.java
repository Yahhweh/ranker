package kegly.organisation.raceranker.printers;

import kegly.organisation.raceranker.models.LapResult;
import kegly.organisation.raceranker.formatter.*;
import kegly.organisation.raceranker.formatter.ReportFormatter;

import java.util.List;


public class ReportPrinter {

    private static final String SEPARATOR = "------------------------------------------------------------------------";
    private final ReportFormatter formatter = new ReportFormatter();

    public void print(List<LapResult> sortedResult, int qualificationLimit) {
        for (int i = 0; i < sortedResult.size(); i++) {
            int rank = i + 1;
            LapResult lap = sortedResult.get(i);

            String formattedLine = formatter.formatLine(rank, lap);

            System.out.println(formattedLine);

            if (rank == qualificationLimit) {
                System.out.println(SEPARATOR);
            }
        }
    }
}
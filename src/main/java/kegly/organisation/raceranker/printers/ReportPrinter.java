package kegly.organisation.raceranker.printers;

import kegly.organisation.raceranker.models.LapResult;
import kegly.organisation.raceranker.formatter.*;
import kegly.organisation.raceranker.formatter.ReportFormatter;

import java.util.List;

public class ReportPrinter {

    private final ReportFormatter formatter;

    public ReportPrinter(ReportFormatter formatter) {
        this.formatter = formatter;
    }

    public void print(List<LapResult> sortedResult, int qualificationLimit) {
        String report = formatter.format(sortedResult, qualificationLimit);
        System.out.print(report);
    }
}
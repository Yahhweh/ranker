package kegly.organisation.raceranker.formatter;

import kegly.organisation.raceranker.models.Driver;
import kegly.organisation.raceranker.models.LapResult;

import java.time.Duration;
import java.util.List;

public class ReportFormatter {

    public String format(List<LapResult> sortedResult, int qualificationLimit) {
        StringBuilder report = new StringBuilder();

        for (int i = 0; i < sortedResult.size(); i++) {
            int rank = i + 1;
            LapResult lap = sortedResult.get(i);

            String line = formatLine(rank, lap);
            report.append(line)
                    .append(System.lineSeparator());

            if (rank == qualificationLimit && i < sortedResult.size() - 1) {
                report.append("-".repeat(line.length()))
                        .append(System.lineSeparator());
            }
        }

        return report.toString();
    }

    private String formatLine(int rank, LapResult lapResult) {
        Driver driver = lapResult.getDriver();
        String durationStr = formatDuration(lapResult.getTime());

        return String.format("%-3s %-20s | %-25s | %s",
                rank + ".",
                driver.getName(),
                driver.getCar(),
                durationStr);
    }

    private String formatDuration(Duration duration) {
        long totalMillis = duration.toMillis();

        long minutes = duration.toMinutes();
        long remainingMillis = totalMillis % 60000;

        long seconds = remainingMillis / 1000;
        long millis = remainingMillis % 1000;

        return String.format("%d:%02d.%03d", minutes, seconds, millis);
    }
}
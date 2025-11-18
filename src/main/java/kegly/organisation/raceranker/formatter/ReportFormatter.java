package kegly.organisation.raceranker.formatter;

import kegly.organisation.raceranker.models.Driver;
import kegly.organisation.raceranker.models.LapResult;

import java.time.Duration;


public class ReportFormatter {

    public String formatLine(int rank, LapResult lapResult) {
        Driver driver = lapResult.getDriver();

        String durationStr = formatDuration(lapResult.getTime());

        return String.format("%-3s %-20s | %-25s | %s",
                rank + ".",
                driver.getName(),
                driver.getCar(),
                durationStr);
    }
    public String formatDuration(Duration duration) {
        long totalMillis = duration.toMillis();
        String sign = "";

        if (totalMillis < 0) {
            sign = "-";
            totalMillis = Math.abs(totalMillis);
        }

        long minutes = totalMillis / 60000;
        long remainingMillis = totalMillis % 60000;

        long seconds = remainingMillis / 1000;
        long millis = remainingMillis % 1000;

        return String.format("%s%d:%02d.%03d", sign, minutes, seconds, millis);
    }
}
package kegly.organisation.raceranker;

import java.time.Duration;
import java.util.List;

public class ReportPrinter {

    private static final int QUALIFICATION_LIMIT = 15;
    private static final String SEPARATOR = "------------------------------------------------------------------------";

    public void print(List<LapResult> sortedResult) {

        for (int i = 0; i < sortedResult.size(); i++) {
            int rank = i + 1;
            LapResult lap = sortedResult.get(i);

            String formattedLine = formatLine(rank, lap);

            System.out.println(formattedLine);

            if (rank == QUALIFICATION_LIMIT) {
                System.out.println(SEPARATOR);
            }
        }
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
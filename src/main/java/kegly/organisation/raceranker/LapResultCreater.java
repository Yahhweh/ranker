package kegly.organisation.raceranker;

import java.time.Duration;
import java.util.*;

public class LapResultCreater {

    public List<LapResult> createLapResult(Map<String, Driver> driver,
                                           Map<String, Duration> time) {

        List<LapResult> result = new ArrayList<>();

        for (Map.Entry<String, Duration> timeEntry : time.entrySet()) {

            String abbreviation = timeEntry.getKey();
            Duration duration = timeEntry.getValue();

            Driver driverData = driver.get(abbreviation);

            if (driverData != null) {
                LapResult lap = new LapResult(duration, driverData);
                result.add(lap);
            }
        }

        return result;
    }
}
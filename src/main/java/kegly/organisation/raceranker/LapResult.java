package kegly.organisation.raceranker;

import lombok.Data;

import java.time.Duration;

@Data
public class LapResult {

    Duration time;
    Driver driver;

    public LapResult(Duration time, Driver driver) {
        this.time = time;
        this.driver = driver;
    }

}

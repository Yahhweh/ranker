package kegly.organisation.raceranker.models;

import lombok.Data;

import java.time.Duration;

@Data
public class LapResult {

    private Duration time;
    private Driver driver;

    public LapResult(Duration time, Driver driver) {
        this.time = time;
        this.driver = driver;
    }

}

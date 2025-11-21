package kegly.organisation.raceranker.models;

import lombok.Data;

import java.time.Duration;

@Data
public class LapResult {

    private Duration time;
    private Driver driver;

    public LapResult(Duration time, Driver driver) {
        if(time.isNegative()){
            throw new IllegalArgumentException("Duration cannot be negative: ");
        }
        this.time = time;
        this.driver = driver;
    }

}

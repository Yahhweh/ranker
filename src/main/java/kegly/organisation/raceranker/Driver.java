package kegly.organisation.raceranker;

import lombok.Data;

@Data
public class Driver {
    String abbreviation;
    String name;
    String car;

    public Driver(String abbreviation, String name, String car) {
        this.abbreviation = abbreviation;
        this.name = name;
        this.car = car;
    }
}
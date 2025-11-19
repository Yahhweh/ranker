package kegly.organisation.raceranker.models;

import lombok.Data;

@Data
public class Driver {

    private String abbreviation;
    private String name;
    private String car;

    public Driver(String abbreviation, String name, String car) {
        this.abbreviation = abbreviation;
        this.name = name;
        this.car = car;
    }
}
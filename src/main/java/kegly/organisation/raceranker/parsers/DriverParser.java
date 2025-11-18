package kegly.organisation.raceranker.parsers;

import kegly.organisation.raceranker.models.Driver;


public class DriverParser {

    public Driver parse(String line) {
        String[] parts = line.split("_");

        int ABBREVIATION_LENGTH = 3;
        String abbreviation = parts[0];
        String name = parts[1];
        String team = parts[2];

        if (parts.length != ABBREVIATION_LENGTH) {
            throw new IllegalArgumentException("Invalid format");
        }

        if (abbreviation.length() != ABBREVIATION_LENGTH) {
            throw new IllegalArgumentException("Abbreviation must be 3 letters");
        }

        if (abbreviation.matches(".*\\d.*")) {
            throw new IllegalArgumentException("Invalid abbreviation: Contains digits");
        }

        return new Driver(abbreviation, name, team);
    }
}

package kegly.organisation.raceranker.parsers;

import kegly.organisation.raceranker.models.Driver;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DriverParser implements DataParser<List<Driver>> {

    private static final int EXPECTED_PARTS = 3;
    private static final int ABBREVIATION_LENGTH = 3;
    private static final String DELIMITER = "_";

    @Override
    public List<Driver> parse(Stream<String> lines) {
        return lines
                .map(this::parseLine)
                .collect(Collectors.toList());
    }

    private Driver parseLine(String line) {
        String[] parts = line.split(DELIMITER);

        if (parts.length != EXPECTED_PARTS) {
            throw new IllegalArgumentException("Invalid format via line: " + line);
        }

        String abbreviation = parts[0];
        String name = parts[1];
        String team = parts[2];

        validateAbbreviation(abbreviation);

        return new Driver(abbreviation, name, team);
    }

    private void validateAbbreviation(String abbreviation) {
        if (abbreviation.length() != ABBREVIATION_LENGTH) {
            throw new IllegalArgumentException("Abbreviation must be 3 letters: " + abbreviation);
        }

        if (abbreviation.matches(".*\\d.*")) {
            throw new IllegalArgumentException("Invalid abbreviation (contains digits): " + abbreviation);
        }
    }
}
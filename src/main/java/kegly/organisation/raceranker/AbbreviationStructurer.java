package kegly.organisation.raceranker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AbbreviationStructurer implements DataStructurer<Driver> {
    @Override
    public Map<String, Driver> parse(List<String> lines) {

        Map<String, Driver> result = new HashMap<>();
        for (String line : lines) {
            String[] parts = line.split("_");

            if (parts.length != 3) {
                throw new IllegalArgumentException();
            }

            if(parts[0].length() != 3){
                throw  new IllegalArgumentException();

            }

            String abbreviation = parts[0];
            String name = parts[1];
            String team = parts[2];

            if (abbreviation.matches(".*\\d.*")) {
                throw new IllegalArgumentException(
                        "Invalid abbreviation: Contains digits. Line: " + line);
            }
            Driver driver = new Driver(abbreviation, name, team);
            result.put(abbreviation, driver);
        }
        return result;
    }
}
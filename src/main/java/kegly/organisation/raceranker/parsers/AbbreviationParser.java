package kegly.organisation.raceranker.parsers;

import kegly.organisation.raceranker.interfaces.DataParser;
import kegly.organisation.raceranker.models.Driver;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AbbreviationParser implements DataParser<Driver> {

    private final DriverParser parser = new DriverParser();

    @Override
    public Map<String, Driver> parse(Stream<String> lines) {
        return lines
                .map(parser::parse)
                .collect(Collectors.toMap(
                        Driver::getAbbreviation,
                        driver -> driver
                ));
    }
}
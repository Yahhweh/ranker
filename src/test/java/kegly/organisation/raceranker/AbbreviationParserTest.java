package kegly.organisation.raceranker;

import kegly.organisation.raceranker.models.Driver;
import kegly.organisation.raceranker.parsers.AbbreviationParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AbbreviationParserTest {

    AbbreviationParser abbreviationParser;

    @BeforeEach
    void setUp() {
        abbreviationParser = new AbbreviationParser();
    }

    @Test
    void parse_throwIllegalArgumentException_whenAbbreviationContainsDigits() {
        String invalidLine = "DR1_Daniel Ricciardo_RED BULL RACING TAG HEUER";
        Stream<String> invalidInput = Stream.of(invalidLine);

        assertThrows(
                IllegalArgumentException.class, () -> {
                    abbreviationParser.parse(invalidInput);
                });
    }

    @Test
    void parse_throwIllegalArgumentException_whenAbbreviationMoreThanThreeLetters() {
        String invalidLine = "DRRR_Daniel Ricciardo_RED BULL RACING TAG HEUER";
        Stream<String> invalidInput = Stream.of(invalidLine);

        assertThrows(
                IllegalArgumentException.class, () -> {
                    abbreviationParser.parse(invalidInput);
                });
    }


    @Test
    void parse_throwIllegalArgumentException_whenListContainsMoreThenThreeStrings() {
        String invalidLine = "DRR_Daniel Ricciardo_RED BULL RACING TAG HEUER_Hello";
        Stream<String> invalidInput = Stream.of(invalidLine);

        assertThrows(
                IllegalArgumentException.class, () -> {
                    abbreviationParser.parse(invalidInput);
                });
    }

    @Test
    void parse_returnMap_whenCorrectData() {
        Stream<String> validInput = Stream.of(
                "DRR_Daniel Ricciardo_RED BULL RACING TAG HEUER",
                "SVF_Sebastian Vettel_FERRARI"
        );

        Map<String, Driver> expected = new HashMap<>();
        expected.put("DRR", new Driver("DRR", "Daniel Ricciardo", "RED BULL RACING TAG HEUER"));
        expected.put("SVF", new Driver("SVF", "Sebastian Vettel", "FERRARI"));

        Map<String, Driver> result = abbreviationParser.parse(validInput);

        assertEquals(expected, result);
    }

}
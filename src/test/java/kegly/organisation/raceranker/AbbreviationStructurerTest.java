    package kegly.organisation.raceranker;

    import org.junit.jupiter.api.BeforeEach;
    import org.junit.jupiter.api.Test;

    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

    import static org.junit.jupiter.api.Assertions.assertEquals;
    import static org.junit.jupiter.api.Assertions.assertThrows;

    class AbbreviationStructurerTest {

        AbbreviationStructurer abbreviationStructurer;

        @BeforeEach
        void setUp(){
            abbreviationStructurer = new AbbreviationStructurer();
        }

        @Test
        void parse_returnIllegalArgumentException_whenAbbreviationContainsDigits(){
            String invalidLine = "DR1_Daniel Ricciardo_RED BULL RACING TAG HEUER";
            List<String> invalidInput = List.of(invalidLine);

            assertThrows(
                    IllegalArgumentException.class, () -> {
                        abbreviationStructurer.parse(invalidInput);
                    });
        }

        @Test
        void parse_returnIllegalArgumentException_whenAbbreviationMoreThanThreeLetters(){
            String invalidLine = "DRRR_Daniel Ricciardo_RED BULL RACING TAG HEUER";
            List<String> invalidInput = List.of(invalidLine);

            assertThrows(
                    IllegalArgumentException.class, () -> {
                        abbreviationStructurer.parse(invalidInput);
                    });
        }


        @Test
        void parse_returnIllegalArgumentException_whenListContainsMoreThenThreeStrings(){
            String invalidLine = "DRR_Daniel Ricciardo_RED BULL RACING TAG HEUER_Hello";
            List<String> invalidInput = List.of(invalidLine);

            assertThrows(
                    IllegalArgumentException.class, () -> {
                        abbreviationStructurer.parse(invalidInput);
                    });
        }

        @Test
        void parse_returnMap_whenCorrectData(){
            List<String> validInput = List.of(
                    "DRR_Daniel Ricciardo_RED BULL RACING TAG HEUER",
                    "SVF_Sebastian Vettel_FERRARI"
            );

            Map<String, Driver> expected = new HashMap<>();
            expected.put("DRR", new Driver("DRR", "Daniel Ricciardo", "RED BULL RACING TAG HEUER"));
            expected.put("SVF", new Driver("SVF", "Sebastian Vettel", "FERRARI"));

            Map<String, Driver> result = abbreviationStructurer.parse(validInput);

            assertEquals(expected, result);
        }

    }
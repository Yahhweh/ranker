package kegly.organisation.raceranker.parsers;

import java.util.*;
import java.util.stream.Stream;

public interface DataParser<T> {

    T parse(Stream<String> lines);
}

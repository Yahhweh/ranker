package kegly.organisation.raceranker.interfaces;

import java.util.*;
import java.util.stream.Stream;


public interface DataParser<V> {

    Map<String, V> parse(Stream<String> lines);
}

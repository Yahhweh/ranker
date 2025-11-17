package kegly.organisation.raceranker;

import java.util.*;

public interface DataStructurer<V> {

    public Map<String, V> parse(List<String> lines);
}

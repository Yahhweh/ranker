package kegly.organisation.raceranker.services;

import kegly.organisation.raceranker.models.LapResult;

import java.util.Comparator;
import java.util.List;

public class Ranking {

    public List<LapResult> sortResults(List<LapResult> unsortedResult) {

        return unsortedResult.stream()
                .sorted(Comparator.comparing(LapResult::getTime))
                .toList();
    }
}

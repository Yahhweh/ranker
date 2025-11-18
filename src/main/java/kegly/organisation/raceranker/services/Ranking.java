package kegly.organisation.raceranker.services;

import kegly.organisation.raceranker.models.LapResult;

import java.util.Comparator;
import java.util.List;


public class Ranking {

    public List<LapResult> sortResults(List<LapResult> unsortedResult) {
        Comparator<LapResult> byTime = Comparator.comparing(LapResult::getTime);

        unsortedResult.sort(byTime);

        return unsortedResult;
    }
}

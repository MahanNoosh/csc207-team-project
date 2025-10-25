package tut0301.group1.healthz.usecase.scorefood;

import tut0301.group1.healthz.usecase.scorefood.model.ScoreFoodRequestModel;

/**
 * <I> Input Boundary for the Score Food use case.
 */
public interface ScoreFoodInputBoundary {
    void execute(ScoreFoodRequestModel request);
}

package tut0301.group1.healthz.usecase.scorefood;

import tut0301.group1.healthz.usecase.scorefood.model.ScoreFoodResponseModel;

/** <I> Output Boundary for the Score Food use case (implemented by Presenter). */
public interface ScoreFoodOutputBoundary {
    void present(ScoreFoodResponseModel response);
}

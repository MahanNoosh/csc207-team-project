package healthz.tut0301.group1.usecase.setting;

public interface UpdateUserOutputBoundary {
    void prepareSuccessView(UpdateUserOutputData outputData);
    void prepareFailView(String error);
}

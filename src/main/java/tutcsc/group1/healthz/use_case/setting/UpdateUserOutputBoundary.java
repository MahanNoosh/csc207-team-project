package tutcsc.group1.healthz.use_case.setting;

public interface UpdateUserOutputBoundary {
    void prepareSuccessView(UpdateUserOutputData outputData);
    void prepareFailView(String error);
}

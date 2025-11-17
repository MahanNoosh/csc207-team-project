package tut0301.group1.healthz.usecase.macrosearch;

public class MacroDetailInteractor implements MacroDetailInputBoundary {

    private final MacroDetailGateway gateway;
    private final MacroDetailOutputBoundary presenter;

    public MacroDetailInteractor(MacroDetailGateway gateway, MacroDetailOutputBoundary presenter) {
        this.gateway = gateway;
        this.presenter = presenter;
    }

    @Override
    public void execute(MacroDetailInputData inputData) {
        if (inputData == null || inputData.getFoodId() <= 0) {
            presenter.prepareFailView("Missing food id for nutrition lookup.");
            return;
        }

        try {
            MacroDetailOutputData outputData = new MacroDetailOutputData(gateway.fetchDetails(inputData.getFoodId()));
            presenter.prepareSuccessView(outputData);
        } catch (Exception e) {
            presenter.prepareFailView("Could not load food details: " + e.getMessage());
        }
    }
}
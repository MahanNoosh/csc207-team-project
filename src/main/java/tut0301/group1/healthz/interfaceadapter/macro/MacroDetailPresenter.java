//package tut0301.group1.healthz.interfaceadapter.macro;
//
//import tut0301.group1.healthz.usecase.macrosearch.MacroDetailOutputBoundary;
//import tut0301.group1.healthz.usecase.macrosearch.MacroDetailOutputData;
//
///**
// * DEPRECATED: This presenter is part of the macrosearch use case which uses FoodNutritionDetails.
// * Use FoodDetailPresenter instead, which works with FoodDetails.
// *
// * This class is kept for backwards compatibility but should not be used in new code.
// */
//@Deprecated
//public class MacroDetailPresenter implements MacroDetailOutputBoundary {
//
//    private final MacroDetailViewModel viewModel;
//
//    public MacroDetailPresenter(MacroDetailViewModel viewModel) {
//        this.viewModel = viewModel;
//    }
//
//    @Override
//    public void prepareSuccessView(MacroDetailOutputData outputData) {
//        // Note: This causes a compile error because MacroDetailOutputData returns FoodNutritionDetails
//        // but MacroDetailViewModel now expects FoodDetails
//        // Use FoodDetailPresenter instead
//        throw new UnsupportedOperationException("This presenter is deprecated. Use FoodDetailPresenter instead.");
//    }
//
//    @Override
//    public void prepareFailView(String errorMessage) {
//        viewModel.setMessage(errorMessage);
//        viewModel.setDetails(null);
//        viewModel.setLoading(false);
//    }
//
//    public MacroDetailViewModel getViewModel() {
//        return viewModel;
//    }
//}
package tut0301.group1.healthz.usecase.macrosearch.metadata;

public class MacroSearchInputData {
    private final String searchQuary;

    public MacroSearchInputData(String searchQuary) {
        this.searchQuary = searchQuary;
    }

    public String getSearchQuary() {
        return searchQuary;
    }
}
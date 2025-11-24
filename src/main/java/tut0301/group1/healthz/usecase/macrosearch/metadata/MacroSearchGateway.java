package tut0301.group1.healthz.usecase.macrosearch.metadata;

public interface MacroSearchGateway {
    MacroSearchOutputData search(MacroSearchInputData input) throws Exception;
}
package tut0301.group1.healthz.usecase.macrosearch;

import tut0301.group1.healthz.entities.nutrition.MacroSearchResult;

import java.util.List;

public interface MacroSearchGateway {
    List<MacroSearchResult> search(String query) throws Exception;
}
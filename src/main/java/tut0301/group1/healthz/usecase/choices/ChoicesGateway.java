package tut0301.group1.healthz.usecase.choices;

import java.util.List;

public interface ChoicesGateway {
    void upsert(String userId, String key, String value) throws Exception;
    List<ChoiceRow> list() throws Exception;

    record ChoiceRow(String key, String value, String createdAt) {}
}

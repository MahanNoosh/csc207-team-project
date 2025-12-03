package tutcsc.group1.healthz.use_case.choices;

import java.util.List;

public interface ChoicesGateway {
    void upsert(String userId, String key, String value) throws Exception;
    List<ChoiceRow> list() throws Exception;

    record ChoiceRow(String key, String value, String createdAt) {}
}

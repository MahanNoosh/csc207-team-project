package tut0301.group1.healthz.dataaccess.supabase;

import org.json.JSONArray;
import org.json.JSONObject;
import tut0301.group1.healthz.usecase.dashboard.Profile;
import tut0301.group1.healthz.usecase.dashboard.UserDataGateway;
import tut0301.group1.healthz.usecase.dashboard.UserDashboardPort;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class SupabaseUserDataGateway implements UserDataGateway {
    private final SupabaseClient client;

    public SupabaseAuthGateway(SupabaseClient client) {
        this.client = client;
    }


}

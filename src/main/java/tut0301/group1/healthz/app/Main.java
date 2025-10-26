package tut0301.group1.healthz.app;

import tut0301.group1.healthz.dataaccess.supabase.SupabaseAuthGateway;
import tut0301.group1.healthz.interfaceadapter.auth.AuthController;
import tut0301.group1.healthz.presenter.auth.AuthLoginPresenter;
import tut0301.group1.healthz.dataaccess.supabase.SupabaseClient;
import tut0301.group1.healthz.usecase.auth.AuthGateway;
import tut0301.group1.healthz.usecase.auth.login.LoginInputBoundary;
import tut0301.group1.healthz.usecase.auth.login.LoginInteractor;
import tut0301.group1.healthz.view.auth.LoginView;

public class Main {
    public static void main(String[] args) {
        String url  = System.getenv("SUPABASE_URL");
        String anon = System.getenv("SUPABASE_ANON_KEY");
        if (url == null || anon == null) {
            System.err.println("Set SUPABASE_URL and SUPABASE_ANON_KEY");
            System.exit(1);
        }

        var client = new SupabaseClient(url, anon);
        AuthGateway gateway = new SupabaseAuthGateway(client);
        var presenter = new AuthLoginPresenter();
        LoginInputBoundary loginUC = new LoginInteractor(gateway, presenter);
        var controller = new AuthController(loginUC, presenter);

        // Demo — replace with your own test user
        LoginView vm = controller.login("test@example.com", "Mahan123");
        System.out.println(vm);
        System.out.println("Access token (starts): " + (vm.accessToken() == null ? "null" : vm.accessToken().substring(0, 20) + "..."));
    }
}

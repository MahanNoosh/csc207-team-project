package tut0301.group1.healthz.app;

import tut0301.group1.healthz.interfaceadapter.auth.login.LoginController;
import tut0301.group1.healthz.interfaceadapter.auth.login.LoginPresenter;
import tut0301.group1.healthz.usecase.auth.AuthGateway;
import tut0301.group1.healthz.interfaceadapter.auth.login.LoginViewModel;
import tut0301.group1.healthz.usecase.auth.login.LoginInteractor;
import tut0301.group1.healthz.usecase.auth.login.LoginInputBoundary;
import tut0301.group1.healthz.dataaccess.supabase.SupabaseClient;
import tut0301.group1.healthz.dataaccess.supabase.SupabaseAuthGateway;
import tut0301.group1.healthz.view.auth.LoginView;

public class Main {
    public static void main(String[] args) {
        // Retrieve environment variables
        String url = System.getenv("SUPABASE_URL");
        String anon = System.getenv("SUPABASE_ANON_KEY");

        if (url == null || anon == null) {
            System.err.println("Set SUPABASE_URL and SUPABASE_ANON_KEY");
            System.exit(1);
        }

        // Create SupabaseClient
        var client = new SupabaseClient(url, anon);
        AuthGateway gateway = new SupabaseAuthGateway(client);

        // Create LoginViewModel and Presenter
        LoginViewModel viewModel = new LoginViewModel();
        LoginPresenter presenter = new LoginPresenter(viewModel);

        // Create Interactor and Controller
        LoginInputBoundary loginUC = new LoginInteractor(gateway, presenter);
        LoginController controller = new LoginController(loginUC, presenter);

        // Perform login (CLI)
        controller.login("test@example.com", "Mahan123");
        LoginView loginView = new LoginView();
        loginView.display(viewModel.getMessage());
        // Retrieve the updated view model and print the result
        LoginViewModel vm = controller.getViewModel();
        System.out.println("User: " + vm.getUserId());
        System.out.println("Access token: " + (vm.getAccessToken() != null ? vm.getAccessToken() : "null"));
    }
}

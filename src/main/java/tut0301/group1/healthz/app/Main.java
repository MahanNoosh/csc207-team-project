package tut0301.group1.healthz.app;

import java.util.Scanner;

import tut0301.group1.healthz.dataaccess.supabase.SupabaseAuthGateway;
import tut0301.group1.healthz.dataaccess.supabase.SupabaseClient;
import tut0301.group1.healthz.interfaceadapter.auth.login.LoginController;
import tut0301.group1.healthz.interfaceadapter.auth.login.LoginPresenter;
import tut0301.group1.healthz.interfaceadapter.auth.login.LoginViewModel;
import tut0301.group1.healthz.interfaceadapter.auth.signup.SignupController;
import tut0301.group1.healthz.interfaceadapter.auth.signup.SignupPresenter;
import tut0301.group1.healthz.interfaceadapter.auth.signup.SignupViewModel;
import tut0301.group1.healthz.usecase.auth.AuthGateway;
import tut0301.group1.healthz.usecase.auth.login.LoginInputBoundary;
import tut0301.group1.healthz.usecase.auth.login.LoginInteractor;
import tut0301.group1.healthz.usecase.auth.signup.SignupInputBoundary;
import tut0301.group1.healthz.usecase.auth.signup.SignupInteractor;
import tut0301.group1.healthz.view.auth.LoginView;
import tut0301.group1.healthz.view.auth.SignupView;

public class Main {

    public static void main(String[] args) {
        // ---- Env wiring ----
        String url  = System.getenv("SUPABASE_URL");
        String anon = System.getenv("SUPABASE_ANON_KEY");
        if (url == null || anon == null) {
            System.err.println("Set SUPABASE_URL and SUPABASE_ANON_KEY");
            System.exit(1);
        }

        // ---- Gateways/Client shared for the session ----
        var client = new SupabaseClient(url, anon);
        AuthGateway gateway = new SupabaseAuthGateway(client);

        // ---- Login stack ----
        var loginVM = new LoginViewModel();
        var loginPresenter = new LoginPresenter(loginVM);      // presenter writes into VM
        LoginInputBoundary loginUC = new LoginInteractor(gateway, loginPresenter);
        var loginController = new LoginController(loginUC, loginPresenter);
        var loginView = new LoginView();                       // simple CLI printer

        // ---- Signup stack ----
        var signupView = new SignupView();                     // presenter prints via this view
        var signupVM = new SignupViewModel();
        var signupPresenter = new SignupPresenter(signupView, signupVM);
        SignupInputBoundary signupUC = new SignupInteractor(gateway, signupPresenter);
        var signupController = new SignupController(signupUC, signupPresenter);

        // ---- CLI loop ----
        var sc = new Scanner(System.in);
        System.out.println("=== HealthZ Auth CLI ===");
        while (true) {
            System.out.println();
            System.out.println("1) Sign up");
            System.out.println("2) Log in");
            System.out.println("3) Quit");
            System.out.print("Choose: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1" -> {
                    String email = prompt(sc, "Email: ");
                    String pw1   = prompt(sc, "Password: ");
                    String pw2   = prompt(sc, "Repeat password: ");
                    signupController.signup(email, pw1, pw2);
                    // SignupPresenter already prints via SignupView
                }
                case "2" -> {
                    String email = prompt(sc, "Email: ");
                    String pw    = prompt(sc, "Password: ");
                    loginController.login(email, pw);

                    // Show the message the presenter put into the VM (and print via view as well if you want)
                    if (loginVM.getMessage() != null && !loginVM.getMessage().isBlank()) {
                        loginView.display(loginVM.getMessage());
                    }

                    // Extra: show parsed results
                    boolean success = loginVM.getAccessToken() != null && loginVM.getUserId() != null;
                    if (success) {
                        System.out.println("User ID: " + loginVM.getUserId());
                        System.out.println("Access token: " + prefix(loginVM.getAccessToken(), 20));
                    } else {
                        System.out.println("Not logged in.");
                    }
                }
                case "3" -> {
                    System.out.println("Bye!");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static String prompt(Scanner sc, String label) {
        System.out.print(label);
        return sc.nextLine().trim();
    }

    private static String prefix(String s, int n) {
        if (s == null) return "null";
        return (s.length() <= n) ? s : (s.substring(0, n) + "...");
    }
}

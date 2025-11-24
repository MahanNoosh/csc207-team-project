package tut0301.group1.healthz.app;

import java.util.Scanner;

import tut0301.group1.healthz.dataaccess.supabase.SupabaseAuthDataAccessObject;
import tut0301.group1.healthz.dataaccess.supabase.SupabaseClient;
import tut0301.group1.healthz.dataaccess.supabase.SupabaseUserDataDataAccessObject;
import tut0301.group1.healthz.usecase.auth.AuthGateway;
import tut0301.group1.healthz.usecase.auth.login.LoginInputBoundary;
import tut0301.group1.healthz.usecase.auth.login.LoginInteractor;
import tut0301.group1.healthz.interfaceadapter.auth.login.LoginController;
import tut0301.group1.healthz.interfaceadapter.auth.login.LoginPresenter;
import tut0301.group1.healthz.interfaceadapter.auth.login.LoginViewModel;
import tut0301.group1.healthz.usecase.auth.signup.SignupInputBoundary;
import tut0301.group1.healthz.usecase.auth.signup.SignupInteractor;
import tut0301.group1.healthz.interfaceadapter.auth.signup.SignupController;
import tut0301.group1.healthz.interfaceadapter.auth.signup.SignupPresenter;
import tut0301.group1.healthz.interfaceadapter.auth.signup.SignupViewModel;
import tut0301.group1.healthz.view.auth.LandingView;
import tut0301.group1.healthz.view.auth.SignupView;

import tut0301.group1.healthz.usecase.dashboard.UserDataDataAccessInterface;
import tut0301.group1.healthz.usecase.dashboard.ProfileInputBoundary;
import tut0301.group1.healthz.usecase.dashboard.ProfileInteractor;
import tut0301.group1.healthz.entities.Profile;
import tut0301.group1.healthz.view.dashboard.ProfileCliRenderer;

public class Main {

    public static void main(String[] args) {
        String url  = System.getenv("SUPABASE_URL");
        String anon = System.getenv("SUPABASE_ANON_KEY");
        if (url == null || anon == null) {
            System.err.println("Set SUPABASE_URL and SUPABASE_ANON_KEY");
            System.exit(1);
        }

        var client = new SupabaseClient(url, anon);
        AuthGateway authGateway = new SupabaseAuthDataAccessObject(client);

        // Profile data gateway & interactor
        UserDataDataAccessInterface userDataDataAccessInterface = new SupabaseUserDataDataAccessObject(client);
        ProfileInputBoundary profileUC = new ProfileInteractor(userDataDataAccessInterface);

        // ---- Login stack ----
        var loginVM = new LoginViewModel();
        var loginPresenter = new LoginPresenter(loginVM);
        LoginInputBoundary loginUC = new LoginInteractor(authGateway, loginPresenter);
        var loginController = new LoginController(loginUC, loginPresenter);
        var loginView = new LandingView();

        // ---- Signup stack ----
        var signupView = new SignupView();
        var signupVM = new SignupViewModel();
        var signupPresenter = new SignupPresenter(signupView, signupVM);
        SignupInputBoundary signupUC = new SignupInteractor(authGateway, signupPresenter);
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
                    //signupController.signup(email, pw1, , signupData.fullName);
                }
                case "2" -> {
                    String email = prompt(sc, "Email: ");
                    String pw    = prompt(sc, "Password: ");
                    loginController.login(email, pw);

                    // if (loginVM.getMessage() != null && !loginVM.getMessage().isBlank()) {
                        // loginView.display(loginVM.getMessage());
                    // }

                    boolean success = loginVM.getAccessToken() != null && loginVM.getUserId() != null;
                    if (success) {
                        try {
                            // Use your port to fetch the profile and print a nice table
                            Profile profile = profileUC.getProfile(loginVM.getUserId());
                            System.out.println();
                            System.out.println(ProfileCliRenderer.render(profile));
                        } catch (Exception e) {
                            System.out.println("Failed to load profile: " + e.getMessage());
                        }
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
}

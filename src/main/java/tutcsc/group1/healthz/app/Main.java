package tutcsc.group1.healthz.app;

import java.util.Scanner;

import tutcsc.group1.healthz.data_access.supabase.SupabaseAuthDataAccessObject;
import tutcsc.group1.healthz.data_access.supabase.SupabaseClient;
import tutcsc.group1.healthz.data_access.supabase.SupabaseUserDataDataAccessObject;
import tutcsc.group1.healthz.use_case.auth.AuthGateway;
import tutcsc.group1.healthz.use_case.auth.log_in.LoginInputBoundary;
import tutcsc.group1.healthz.use_case.auth.log_in.LoginInteractor;
import tutcsc.group1.healthz.interface_adapter.auth.log_in.LoginController;
import tutcsc.group1.healthz.interface_adapter.auth.log_in.LoginPresenter;
import tutcsc.group1.healthz.interface_adapter.auth.log_in.LoginViewModel;
import tutcsc.group1.healthz.use_case.auth.sign_up.SignupInputBoundary;
import tutcsc.group1.healthz.use_case.auth.sign_up.SignupInteractor;
import tutcsc.group1.healthz.interface_adapter.auth.sign_up.SignupController;
import tutcsc.group1.healthz.interface_adapter.auth.sign_up.SignupPresenter;
import tutcsc.group1.healthz.interface_adapter.auth.sign_up.SignupViewModel;
import tutcsc.group1.healthz.view.auth.LandingView;
import tutcsc.group1.healthz.view.auth.SignupView;

import tutcsc.group1.healthz.use_case.dashboard.UserDataDataAccessInterface;
import tutcsc.group1.healthz.use_case.dashboard.ProfileInputBoundary;
import tutcsc.group1.healthz.use_case.dashboard.ProfileInteractor;
import tutcsc.group1.healthz.entities.dashboard.Profile;
import tutcsc.group1.healthz.view.dashboard.ProfileCliRenderer;

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
        var signupPresenter = new SignupPresenter(signupVM);
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
                    //signupController.sign_up(email, pw1, , signupData.fullName);
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

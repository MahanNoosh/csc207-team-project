package tut0301.group1.healthz.main;

import tut0301.group1.healthz.controllers.AuthController;
import tut0301.group1.healthz.controllers.RecipeController;

import tut0301.group1.healthz.dataaccess.inmemory.FakeRecipeCatalog;
import tut0301.group1.healthz.dataaccess.inmemory.FakeTokenIssuer;
import tut0301.group1.healthz.dataaccess.inmemory.InMemoryUserDashboardPort;
import tut0301.group1.healthz.dataaccess.inmemory.InMemoryUserRepo;
import tut0301.group1.healthz.dataaccess.inmemory.PlainPasswordHasher;
import tut0301.group1.healthz.dataaccess.inmemory.SystemClockGateway;

import tut0301.group1.healthz.entities.user.PasswordHash;
import tut0301.group1.healthz.entities.user.User;
import tut0301.group1.healthz.entities.user.UserId;

import tut0301.group1.healthz.presenter.AuthLoginPresenter;
import tut0301.group1.healthz.presenter.SearchRecipesPresenter;

import tut0301.group1.healthz.usecase.auth.LoginInputBoundary;
import tut0301.group1.healthz.usecase.auth.LoginInteractor;

import tut0301.group1.healthz.usecase.dashport.UserDashboardPort;
import tut0301.group1.healthz.usecase.policy.DashboardPolicyProvider;
import tut0301.group1.healthz.usecase.recipes.SearchRecipesInputBoundary;
import tut0301.group1.healthz.usecase.recipes.SearchRecipesInteractor;
import tut0301.group1.healthz.usecase.scoring.FoodFactsScorer;

import tut0301.group1.healthz.viewmodel.SearchRecipesViewModel;

import java.util.Set;

/**
 * Manual composition root (no frameworks).
 * Wires adapters to boundaries, then runs Login and Recipe search (with policy-aware healthy score).
 */
public class Main {
    public static void main(String[] args) {
        // --- Right-side adapters (gateways) ---
        var users = new InMemoryUserRepo();
        var hasher = new PlainPasswordHasher();
        var tokens = new FakeTokenIssuer();
        var clock = new SystemClockGateway();
        var catalog = new FakeRecipeCatalog(); // returns demo recipes (extend to include fiber/sugar if you want)

        // --- Dashboard port (source of user goals/metrics) ---
        var dashPort = new InMemoryUserDashboardPort();
        // Seed one demo profile so PolicyProvider can pick targets
        dashPort.upsert(new UserDashboardPort.UserProfile(
                "u1",
                73.0,                       // weightKg
                178.0,                      // heightCm
                26,                         // ageYears
                UserDashboardPort.Sex.MALE,
                UserDashboardPort.Goal.GENERAL_HEALTH,
                1.4                         // activity MET (optional)
        ));

        var policyProvider = new DashboardPolicyProvider(dashPort);
        var scorer = new FoodFactsScorer();

        // --- Seed a user for login demo ---
        var user = new User(
                UserId.newId(),
                "demo@acme.test",
                new PasswordHash(hasher.hash("secret")),
                "Demo User",
                Set.of("USER")
        );
        users.save(user);

        // --- Presenters ---
        var loginPresenter = new AuthLoginPresenter();
        var recipePresenter = new SearchRecipesPresenter();

        // --- Interactors ---
        LoginInputBoundary loginUC = new LoginInteractor(users, hasher, tokens, clock, loginPresenter);

        // NEW signature: (catalog, policyProvider, scorer, presenter)
        SearchRecipesInputBoundary searchUC =
                new SearchRecipesInteractor(catalog, policyProvider, scorer, recipePresenter);

        // --- Controllers ---
        var authController = new AuthController(loginUC, loginPresenter);

        // For demo we inject a default userId ("u1"); in a real app take it from auth/session.
        var recipeController = new RecipeController(searchUC, recipePresenter, "u1");

        // --- Demo: Login ---
        System.out.println("== Login ==");
        System.out.println(authController.login("demo@acme.test", "secret"));

        // --- Demo: Recipe search + show scores ---
        System.out.println("\n== Recipes ==");
        Object vmObj = recipeController.search("any"); // Presenter returns ViewModel

        // Pretty-print items with healthy scores if the VM type matches
        if (vmObj instanceof SearchRecipesViewModel vm) {
            vm.items().forEach(it -> System.out.printf(
                    "- %s (%s): kcal=%.0f P=%.1fg F=%.1fg C=%.1fg  → score=%s%n",
                    it.name(), it.id(), it.calories(), it.proteinG(), it.fatG(), it.carbsG(),
                    it.healthyScore() == null ? "n/a" : String.format("%.1f", it.healthyScore())
            ));
        } else {
            // Fallback: just print the object
            System.out.println(vmObj);
        }
    }
}


package tut0301.group1.healthz.view.auth;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import tut0301.group1.healthz.dataaccess.supabase.SupabaseAuthDataAccessObject;
import tut0301.group1.healthz.dataaccess.supabase.SupabaseClient;
import tut0301.group1.healthz.interfaceadapter.auth.signup.SignupPresenter;
import tut0301.group1.healthz.interfaceadapter.auth.signup.SignupViewModel;
import tut0301.group1.healthz.navigation.Navigator;
import tut0301.group1.healthz.usecase.auth.AuthGateway;
import tut0301.group1.healthz.usecase.auth.signup.SignupInputBoundary;
import tut0301.group1.healthz.usecase.auth.signup.SignupInteractor;
import tut0301.group1.healthz.view.auth.signuppanels.*;
import tut0301.group1.healthz.interfaceadapter.auth.signup.SignupController;
import java.util.List;


public class SignupView {

    private Scene scene;
    private BorderPane root;
    private ProgressBar progressBar;
    private StackPane contentArea;

    // current step tracking
    private int currentStep = 1;
    private static final int TOTAL_STEPS = 7;

    private Step1Panel step1Panel;
    private Step2Panel step2Panel;
    private Step3Panel step3Panel;
    private Step4Panel step4Panel;
    private Step5Panel step5Panel;
    private Step6Panel step6Panel;
    private Step7Panel step7Panel;

    private SignupData signupData;

    private Button loginLinkButton;

    public SignupView() {
        signupData = new SignupData();
        BorderPane mainLayout = createMainLayout();

        ScrollPane scrollPane = new ScrollPane(mainLayout);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(false);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle(
                "-fx-background: #F8FBF5; " +
                        "-fx-background-color: #F8FBF5; " +
                        "-fx-border-width: 0;"
        );
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setPannable(true);
        scrollPane.setVvalue(0);

        scene = new Scene(scrollPane, 1280, 900);

        showStep(1);
    }

    // main layout
    private BorderPane createMainLayout() {
        BorderPane layout = new BorderPane();
        layout.setStyle("-fx-background-color: #F8FBF5;");

        // logo and progress bar
        VBox topSection = createTopSection();
        layout.setTop(topSection);

        // content area
        contentArea = new StackPane();
        contentArea.setPadding(new Insets(20));
        layout.setCenter(contentArea);

        return layout;
    }

    private VBox createTopSection() {
        VBox topBox = new VBox(20);
        topBox.setPadding(new Insets(30, 40, 20, 40));
        topBox.setAlignment(Pos.CENTER);

        // Logo
        Label logo = new Label("HealthZ");
        logo.setFont(Font.font("Inter", FontWeight.BOLD, 32));
        logo.setTextFill(Color.web("#27692A"));

        // Progress bar
        progressBar = new ProgressBar(0);
        progressBar.setPrefWidth(600);
        progressBar.setPrefHeight(12);
        progressBar.setStyle(
                "-fx-accent: #578A69; " +
                        "-fx-background-color: #B9C4C0; " +
                        "-fx-background-radius: 6px;"
        );

        topBox.getChildren().addAll(logo, progressBar);
        return topBox;
    }

    // show a specific step
    private void showStep(int stepNumber) {
        currentStep = stepNumber;
        updateProgressBar();

        // clear current content
        contentArea.getChildren().clear();

        // show the appropriate step panel
        VBox stepPanel = null;
        switch (stepNumber) {
            case 1:
                if (step1Panel == null) step1Panel = new Step1Panel(signupData);
                stepPanel = step1Panel.getPanel();
                break;
            case 2:
                if (step2Panel == null) step2Panel = new Step2Panel(signupData);
                stepPanel = step2Panel.getPanel();
                break;
            case 3:
                if (step3Panel == null) step3Panel = new Step3Panel(signupData);
                stepPanel = step3Panel.getPanel();
                break;
            case 4:
                if (step4Panel == null) step4Panel = new Step4Panel(signupData);
                stepPanel = step4Panel.getPanel();
                break;
            case 5:
                if (step5Panel == null) step5Panel = new Step5Panel(signupData);
                stepPanel = step5Panel.getPanel();
                break;
            case 6:
                if (step6Panel == null) {
                    step6Panel = new Step6Panel(signupData);
                }
                step6Panel.updateGoalWeightFromData();
                stepPanel = step6Panel.getPanel();
                break;
            case 7:
                if (step7Panel == null) step7Panel = new Step7Panel(signupData);
                stepPanel = step7Panel.getPanel();
                break;
        }

        if (stepPanel != null) {
            // Create card wrapper
            VBox card = createCard(stepPanel);
            contentArea.getChildren().add(card);
        }
    }

    // create container for the step
    private VBox createCard(VBox stepContent) {
        VBox card = new VBox(30);
        card.setMaxWidth(500);
        card.setAlignment(Pos.TOP_CENTER);
        card.setPadding(new Insets(40));
        card.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 16px; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 20, 0, 0, 4);"
        );

        // Add step content
        card.getChildren().add(stepContent);

        // Add navigation buttons
        HBox buttonBox = createNavigationButtons();
        card.getChildren().add(buttonBox);

        HBox bottomRow = new HBox(5);
        bottomRow.setAlignment(Pos.CENTER);

        Label haveAccountLabel = new Label("Already have an account?");
        haveAccountLabel.setFont(Font.font("Inter", 13));
        haveAccountLabel.setTextFill(Color.web("#6B7280"));

        // Create button once, reuse for each step
        if (loginLinkButton == null) {
            loginLinkButton = new Button("Log in");
            loginLinkButton.setFont(Font.font("Inter", FontWeight.BOLD, 13));
            loginLinkButton.setStyle(
                    "-fx-background-color: transparent; " +
                            "-fx-text-fill: #27692A; " +
                            "-fx-underline: true; " +
                            "-fx-cursor: hand;"
            );
        }

        bottomRow.getChildren().addAll(haveAccountLabel, loginLinkButton);

        card.getChildren().add(bottomRow);

        return card;
    }

    // create next OR back buttons
    private HBox createNavigationButtons() {
        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);

        // Back button (except on first step)
        if (currentStep > 1) {
            Button backButton = new Button("Back");
            backButton.setPrefWidth(120);
            backButton.setPrefHeight(45);
            backButton.setStyle(
                    "-fx-background-color: #27692A; " +
                            "-fx-text-fill: #FFFFFF; " +
                            "-fx-font-size: 14px; " +
                            "-fx-font-weight: 600; " +
                            "-fx-background-radius: 25px; " +
                            "-fx-cursor: hand;"
            );
            backButton.setOnAction(e -> goToPreviousStep());
            buttonBox.getChildren().add(backButton);
        }

        // Next/Submit button
        Button nextButton = new Button(currentStep == TOTAL_STEPS ? "Create Account" : "Next");
        nextButton.setPrefWidth(currentStep == TOTAL_STEPS ? 160 : 120);
        nextButton.setPrefHeight(45);
        nextButton.setStyle(
                "-fx-background-color: #27692A; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 14px; " +
                        "-fx-font-weight: 600; " +
                        "-fx-background-radius: 25px; " +
                        "-fx-cursor: hand;"
        );

        nextButton.setOnMouseEntered(e ->
                nextButton.setStyle(
                        "-fx-background-color: #047857; " +
                                "-fx-text-fill: white; " +
                                "-fx-font-size: 14px; " +
                                "-fx-font-weight: 600; " +
                                "-fx-background-radius: 25px; " +
                                "-fx-cursor: hand;"
                )
        );

        nextButton.setOnMouseExited(e ->
                nextButton.setStyle(
                        "-fx-background-color: #27692A; " +
                                "-fx-text-fill: white; " +
                                "-fx-font-size: 14px; " +
                                "-fx-font-weight: 600; " +
                                "-fx-background-radius: 25px; " +
                                "-fx-cursor: hand;"
                )
        );

        nextButton.setOnAction(e -> goToNextStep());
        buttonBox.getChildren().add(nextButton);

        return buttonBox;
    }

    // update progress bar based on step
    private void updateProgressBar() {
        double progress = (double) currentStep / TOTAL_STEPS;
        progressBar.setProgress(progress);
    }

    private void goToNextStep() {
        // Collect data from current step
        boolean isValid = collectCurrentStepData();

        if (!isValid) {
            showError("Please fill in all required fields");
            return;
        }

        // Move to next step or submit
        if (currentStep < TOTAL_STEPS) {
            showStep(currentStep + 1);
        } else {
            submitSignup();
        }
    }

    private void goToPreviousStep() {
        if (currentStep > 1) {
            showStep(currentStep - 1);
        }
    }

    // collect data from current step
    private boolean collectCurrentStepData() {
        switch (currentStep) {
            case 1:
                signupData.fullName = step1Panel.getFullName();
                return !signupData.fullName.isEmpty();
            case 2:
                signupData.goal = step2Panel.getSelectedGoal();
                return signupData.goal != null;
            case 3:
                signupData.activityLevel = step3Panel.getSelectedActivity();
                return signupData.activityLevel != null;
            case 4:
                signupData.dietaryRestrictions = step4Panel.getSelectedDiets();
                return true;
            case 5:
                signupData.medicalConsiderations = step5Panel.getMedicalInfo();
                return true;
            case 6:
                signupData.sex = step6Panel.getSex();
                signupData.dateOfBirth = step6Panel.getDateOfBirth();
                signupData.height = step6Panel.getHeight();
                signupData.weight = step6Panel.getWeight();
                signupData.goalWeight = step6Panel.getGoalWeight();
                return signupData.sex != null && signupData.dateOfBirth != null &&
                        signupData.height > 0 && signupData.weight > 0;
            case 7:
                signupData.email = step7Panel.getEmail();
                signupData.password = step7Panel.getPassword();
                signupData.confirmPassword = step7Panel.getConfirmPassword();
                return !signupData.email.isEmpty() && !signupData.password.isEmpty();
            default:
                return true;
        }
    }

    // submit
    private void submitSignup() {
        System.out.println("Submitting signup with data:");
        System.out.println("Name: " + signupData.fullName);
        System.out.println("Email: " + signupData.email);
        System.out.println("Goal: " + signupData.goal);
        // ... etc

        // TODO: Call SignupController here
        var signupVM = new SignupViewModel();
        var signupPresenter = new SignupPresenter(this, signupVM);
        String url  = System.getenv("SUPABASE_URL");
        String anon = System.getenv("SUPABASE_ANON_KEY");
        if (url == null || anon == null) {
            System.err.println("Set SUPABASE_URL and SUPABASE_ANON_KEY");
            System.exit(1);
        }
        var client = new SupabaseClient(url, anon);
        AuthGateway authGateway = new SupabaseAuthDataAccessObject(client);
        SignupInputBoundary signupUC = new SignupInteractor(authGateway, signupPresenter);
        SignupController signupController = new SignupController(signupUC, signupPresenter);
        signupController.signup(signupData.email, signupData.password, signupData.confirmPassword, signupData.fullName);

        Navigator.getInstance().showEmailVerification(signupData);

        showSuccess("Account created successfully!");
    }

    // show error
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // show success
    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public Scene getScene() {
        return scene;
    }

    // data class for storing sign up info
    public static class SignupData {
        String fullName;
        public String goal;
        String activityLevel;
        List<String> dietaryRestrictions;
        String medicalConsiderations;
        String sex;
        String dateOfBirth;
        double height;
        double weight;
        double goalWeight;
        String email;
        String password;
        String confirmPassword;

        // getters

        public String getFullName() {
            return fullName;
        }

        public String getGoal() {
            return goal;
        }

        public String getActivityLevel() {
            return activityLevel;
        }

        public List<String> getDietaryRestrictions() {
            return dietaryRestrictions;
        }

        public String getMedicalConsiderations() {
            return medicalConsiderations;
        }

        public String getSex() {
            return sex;
        }

        public String getDateOfBirth() {
            return dateOfBirth;
        }

        public double getHeight() {
            return height;
        }

        public double getWeight() {
            return weight;
        }

        public double getGoalWeight() {
            return goalWeight;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }

        public String getConfirmPassword() {
            return confirmPassword;
        }
    }



    public void display(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notification");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public Button getLoginLinkButton() {
        return loginLinkButton;
    }
}

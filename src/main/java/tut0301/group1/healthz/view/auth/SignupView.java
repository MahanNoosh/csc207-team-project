package tut0301.group1.healthz.view.auth;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import tut0301.group1.healthz.view.auth.signup.*;


public class SignupView {

    private Scene scene;
    private BorderPane root;
    private ProgressBar progressBar;
    private StackPane stackPane;

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



}

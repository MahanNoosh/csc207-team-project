package tut0301.group1.healthz.view.auth;

public class SignupView {
    private String message;

    // Getter and Setter for message
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // Method to display the message to the console (CLI)
    public void display() {
        System.out.println(message);
    }
}

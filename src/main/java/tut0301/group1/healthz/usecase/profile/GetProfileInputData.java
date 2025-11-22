package tut0301.group1.healthz.usecase.profile;

/**
 * Input DTO for retrieving a user profile.
 */
public class GetProfileInputData {
    private final String userId;

    public GetProfileInputData(String userId) {
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}

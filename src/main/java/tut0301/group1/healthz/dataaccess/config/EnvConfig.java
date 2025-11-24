package tut0301.group1.healthz.dataaccess.config;

public class EnvConfig {

    public static String getClientId() {
        String id = System.getenv("FATSECRET_CLIENT_ID");
        if (id == null || id.isBlank()) {
            throw new IllegalStateException("Missing FATSECRET_CLIENT_ID environment variable");
        }
        return id;
    }

    public static String getClientSecret() {
        String secret = System.getenv("FATSECRET_CLIENT_SECRET");
        if (secret == null || secret.isBlank()) {
            throw new IllegalStateException("Missing FATSECRET_CLIENT_SECRET environment variable");
        }
        return secret;
    }
}


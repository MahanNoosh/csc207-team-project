package healthz.tut0301.group1.entities.user;

import java.util.Set;

/**
 * Entity: User aggregate (no framework).
 */
public class User {
    private final UserId id;
    private final String email;
    private final PasswordHash hash;
    private final String displayName;
    private final Set<String> roles;

    public User(UserId id, String email, PasswordHash hash, String displayName, Set<String> roles) {
        this.id = id;
        this.email = email;
        this.hash = hash;
        this.displayName = displayName;
        this.roles = roles;
    }

    public UserId id() {
        return id;
    }

    public String email() {
        return email;
    }

    public PasswordHash passwordHash() {
        return hash;
    }

    public String displayName() {
        return displayName;
    }

    public Set<String> roles() {
        return roles;
    }
}

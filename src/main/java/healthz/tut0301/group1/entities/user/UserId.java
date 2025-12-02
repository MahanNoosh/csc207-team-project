package healthz.tut0301.group1.entities.user;

import java.util.UUID;

/**
 * Entity VO: Strongly-typed UserId.
 */
public record UserId(String value) {
    public static UserId newId() {
        return new UserId(UUID.randomUUID().toString());
    }
}

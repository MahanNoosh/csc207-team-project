package tut0301.group1.healthz.dataaccess.inmemory;

import tut0301.group1.healthz.usecase.dashport.UserDashboardPort;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Demo adapter: holds profiles in memory. Replace with DB-backed adapter later.
 */
public class InMemoryUserDashboardPort implements UserDashboardPort {
    private final Map<String, UserProfile> store = new ConcurrentHashMap<>();

    @Override
    public Optional<UserProfile> getProfile(String userId) {
        return Optional.ofNullable(store.get(userId));
    }

    public void upsert(UserProfile profile) {
        store.put(profile.userId(), profile);
    }
}

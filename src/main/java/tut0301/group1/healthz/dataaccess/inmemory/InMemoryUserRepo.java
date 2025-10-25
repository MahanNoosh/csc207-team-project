package tut0301.group1.healthz.dataaccess.inmemory;

import tut0301.group1.healthz.dataaccess.UserDataAccessInterface;
import tut0301.group1.healthz.entities.user.*;
import tut0301.group1.healthz.entities.user.User;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Simple in-memory user store for demos/tests.
 */
public class InMemoryUserRepo implements UserDataAccessInterface {
    private final Map<String, User> byEmail = new ConcurrentHashMap<>();

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(byEmail.get(email));
    }

    @Override
    public User save(User u) {
        byEmail.put(u.email(), u);
        return u;
    }
}

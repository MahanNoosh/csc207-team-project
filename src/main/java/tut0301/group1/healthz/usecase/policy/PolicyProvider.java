package tut0301.group1.healthz.usecase.policy;

import tut0301.group1.healthz.entities.nutrition.HealthyScorePolicy;

import java.util.Optional;

/**
 * Use-case port: given a userId, return the scoring policy for that user.
 */
public interface PolicyProvider {
    Optional<HealthyScorePolicy> forUser(String userId);
}

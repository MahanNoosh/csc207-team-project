package tut0301.group1.healthz.usecase.policy;

import tut0301.group1.healthz.entities.nutrition.HealthyScorePolicy;
import tut0301.group1.healthz.entities.nutrition.HealthyScorePresets;
import tut0301.group1.healthz.usecase.dashboard.UserDashboardPort;

import java.util.Optional;

/**
 * Use-case adapter: asks UserDashboardPort for the profile and maps it to a HealthyScorePolicy.
 * Reuse this provider anywhere (Search, ScoreFood, Suggestions, Dashboard).
 */
public class DashboardPolicyProvider implements PolicyProvider {

    private final UserDashboardPort dashboard;

    public DashboardPolicyProvider(UserDashboardPort dashboard) {
        this.dashboard = dashboard;
    }

    @Override
    public Optional<HealthyScorePolicy> forUser(String userId) {
        return dashboard.getProfile(userId).map(this::toPolicy);
    }

    private HealthyScorePolicy toPolicy(UserDashboardPort.UserProfile p) {
        switch (p.goal()) {
            case WEIGHT_LOSS:
                return HealthyScorePresets.weightLoss();
            case ATHLETIC: {
                Double met = p.activityLevelMET();
                return (met != null && met >= 1.6)
                        ? HealthyScorePresets.athletic()
                        : HealthyScorePresets.athletic(); // same preset; keep for clarity if we add variants later
            }
            case GENERAL_HEALTH:
            default:
                return HealthyScorePresets.generalHealth();
        }
    }
}

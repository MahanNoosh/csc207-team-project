package tut0301.group1.healthz.usecase.dashboard;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ProfileInteractor implements ProfileInputBoundary {

    private final UserDashboardPort userDashboardPort;
    private final Map<String, Profile> userProfiles = new HashMap<>();

    public ProfileInteractor(UserDashboardPort userDashboardPort) {
        this.userDashboardPort = userDashboardPort;
    }
    @Override
    public void createProfile(Profile profile){
        userProfiles.put(profile.getUserId(), profile);
        userDashboardPort.saveProfile(toPort(profile));
    }
    @Override
    public void updateProfile(Profile profile){
        userProfiles.put(profile.getUserId(),  profile);
        userDashboardPort.saveProfile(toPort(profile));
    }
    @Override
    public Profile getProfile(String userId){
        Profile profile = userProfiles.get(userId);
        if (profile != null) return profile;

        Optional<UserDashboardPort.UserProfile> dto =  userDashboardPort.getProfile(userId);
        if(dto.isPresent()) {
            profile = fromPort(dto.get());
            userProfiles.put(userId, profile);
            return profile;
        }
        else{
            throw new RuntimeException("Profile not found");
        }
    }

    private static Profile fromPort(UserDashboardPort.UserProfile dto){
        return new Profile(
                dto.userId(),
                dto.weightKg(),
                dto.heightCm(),
                dto.ageYears(),
                dto.sex(),
                dto.goal(),
                dto.activityLevelMET(),
                dto.targetWeightKg(),
                dto.dailyCalorieTarget(),
                dto.healthCondition()
        );
    }
    private static UserDashboardPort.UserProfile toPort(Profile profile){
        return new UserDashboardPort.UserProfile(
                profile.getUserId(),
                profile.getWeightKg(),
                profile.getHeightCm(),
                profile.getAgeYears(),
                profile.getSex(),
                profile.getGoal(),
                profile.getActivityLevelMET(),
                profile.getTargetWeightKg(),
                profile.getDailyCalorieTarget(),
                profile.getHealthCondition()
        );

    }
}

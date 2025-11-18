// ==========================================
// 示例：FoodLogGateway 的完整使用流程
// ==========================================

// 1️⃣ Use Case 层定义接口（抽象）
package usecase.food.logging;

public interface FoodLogGateway {
    void saveFoodLog(String userId, FoodLog foodLog) throws Exception;
    List<FoodLog> getFoodLogs(String userId) throws Exception;
}

// 2️⃣ Use Case 层使用接口
package usecase.food.logging;

public class LogFoodIntakeInteractor {
    private final FoodLogGateway gateway;  // 依赖抽象

    public LogFoodIntakeInteractor(FoodLogGateway gateway) {
        this.gateway = gateway;
    }

    public void execute(LogFoodIntakeInputData inputData) {
        // 业务逻辑：创建 FoodLog
        FoodLog log = new FoodLog(
            inputData.getFood(),
            inputData.getServingInfo(),
            inputData.getServingMultiplier(),
            inputData.getMeal(),
            LocalDateTime.now()
        );

        // 调用 Gateway 保存（不关心实现）
        gateway.saveFoodLog(inputData.getUserId(), log);
    }
}

// 3️⃣ Data Access 层实现接口 - 方式1：保存到 Profile
package dataaccess;

public class ProfileFoodLogGateway implements FoodLogGateway {
    private final ProfileRepository profileRepo;

    @Override
    public void saveFoodLog(String userId, FoodLog foodLog) {
        // 具体实现：将 FoodLog 添加到 Profile
        Profile profile = profileRepo.findByUserId(userId);
        profile.addFoodLog(foodLog);
        profileRepo.save(profile);
    }

    @Override
    public List<FoodLog> getFoodLogs(String userId) {
        Profile profile = profileRepo.findByUserId(userId);
        return profile.getAllFoodLogs();
    }
}

// 4️⃣ Data Access 层实现接口 - 方式2：保存到独立的数据库表
package dataaccess;

public class DatabaseFoodLogGateway implements FoodLogGateway {
    private final FoodLogRepository foodLogRepo;

    @Override
    public void saveFoodLog(String userId, FoodLog foodLog) {
        // 具体实现：保存到独立的 food_logs 表
        FoodLogEntity entity = new FoodLogEntity(userId, foodLog);
        foodLogRepo.insert(entity);
    }

    @Override
    public List<FoodLog> getFoodLogs(String userId) {
        List<FoodLogEntity> entities = foodLogRepo.findByUserId(userId);
        return entities.stream()
            .map(e -> e.toDomain())
            .collect(Collectors.toList());
    }
}

// 5️⃣ Data Access 层实现接口 - 方式3：保存到 Supabase
package dataaccess;

public class SupabaseFoodLogGateway implements FoodLogGateway {
    private final SupabaseClient supabase;

    @Override
    public void saveFoodLog(String userId, FoodLog foodLog) {
        // 具体实现：通过 Supabase API 保存
        JsonObject json = FoodLogMapper.toJson(foodLog);
        supabase.from("food_logs")
            .insert(json)
            .eq("user_id", userId)
            .execute();
    }

    @Override
    public List<FoodLog> getFoodLogs(String userId) {
        String response = supabase.from("food_logs")
            .select("*")
            .eq("user_id", userId)
            .execute();
        return FoodLogMapper.fromJsonArray(response);
    }
}

// 6️⃣ 依赖注入 - 在应用启动时选择实现
package app;

public class Main {
    public static void main(String[] args) {
        // 根据配置选择不同的实现
        FoodLogGateway gateway;

        if (config.useSupabase()) {
            gateway = new SupabaseFoodLogGateway();
        } else if (config.useDatabase()) {
            gateway = new DatabaseFoodLogGateway();
        } else {
            gateway = new ProfileFoodLogGateway();
        }

        // Use Case 不需要改变！
        LogFoodIntakeInteractor interactor = new LogFoodIntakeInteractor(
            gateway,  // ← 注入具体实现
            presenter
        );

        // 使用
        interactor.execute(inputData);
    }
}

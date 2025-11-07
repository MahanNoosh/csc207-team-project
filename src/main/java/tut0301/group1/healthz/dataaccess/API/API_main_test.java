package tut0301.group1.healthz.dataaccess.API;

public class API_main_test {
    public static void main(String[] args) {
        try {
            String CLIENT_ID = "9ef37d375ad34d71a2e1f0703d79c93c";
            String CLIENT_SECRET = "c1d1657075174b2e93a8f4dc270a3aa5";

            FatSecretFoodSearchGateway search = new FatSecretFoodSearchGateway();
            String jsonResponse = search.searchFoods(CLIENT_ID, CLIENT_SECRET, "McDonald's");
            System.out.println("Final JSON Response:\n" + jsonResponse);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

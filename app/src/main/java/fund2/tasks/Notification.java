package fund2.tasks;

import org.json.JSONObject;

import fund2.App;

public class Notification {

    public static String SUCCESS = "5887123";
    public static String FAILURE = "15025485";
    public static String PENDING = "16246360";

    public static Task github(String commitHash, String status, String description) {
        JSONObject json = new JSONObject();
        json.put("state", status);
        json.put("description", description);
        String user = App.dotenv.get("GITHUBUSER");
        String token = App.dotenv.get("GITHUBTOKEN");
        String url = "https://api.github.com/repos/fund-team/fund2/statuses/" + commitHash;
        return new HttpTask(url, json.toString(), user, token);
    }

    public static Task discord(String commitHash, String branch, String status, String description,
            String color) {
        if (App.dotenv.get("DEBUG", "").equals("true")) {
            return new EmptyTask();
        }
        String title = "Commit " + commitHash + " on " + branch;
        String message = "Status: " + status + " " + description;
        JSONObject json = new JSONObject();
        JSONObject embeds = new JSONObject();
        json.put("embeds", new JSONObject[] { embeds });
        embeds.put("title", title);
        embeds.put("description", message);
        embeds.put("color", color);
        json.put("username", "Commit Status Notifier");

        return new HttpTask(App.dotenv.get("DISCORD"), json.toString());
    }
}

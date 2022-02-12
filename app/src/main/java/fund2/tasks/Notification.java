package fund2.tasks;

import org.json.JSONObject;

import fund2.App;

/**
 * Class containing static methods dealing with notifications. Each action
 * is transposed into a static method returning a Httptask "storing" the
 * information needed to send a POST request.
 *
 * Two kinds of notification can be sent. The first to GitHub to update
 * the status of the commit in the repository. The second to a Discord
 * Bot to automatically post a message in a discussion channel.
 */

public class Notification {

    /**
     * Definition of the decimal color used in Discord messages for each status
     */
    public static String SUCCESS = "5887123";
    public static String FAILURE = "15025485";
    public static String PENDING = "16246360";

    /**
     * Create a Httptask to update the commit status on GitHub
     *
     * @param commitHash Commit hash (unique identifier)
     * @param status Commit status, can either be "success", "failure", or "pending"
     * @param description Additional information about the commit status
     * @return A Httptask containing the URL, the content and the authentication
     *         parameters for the POST request to GitHub
     */
    public static Task github(String commitHash, String status, String description) {
        JSONObject json = new JSONObject();
        json.put("state", status);
        json.put("description", description);
        String user = App.dotenv.get("GITHUBUSER");
        String token = App.dotenv.get("GITHUBTOKEN");
        String url = "https://api.github.com/repos/fund-team/fund2/statuses/" + commitHash;
        return new HttpTask(url, json.toString(), user, token);
    }

    /**
     * Create a Httptask to notify the Discord bot
     *
     * @param commitHash Commit hash (unique identifier)
     * @param branch Name of a git branch
     * @param status Commit status, can either be "success", "failure", or "pending"
     * @param description Additional information about the commit status
     * @param color Decimal color to be used in the Discord message
     * @return A Httptask containing the URL and the content for the POST request
     *         to Discord
     */
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

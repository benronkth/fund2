package fund2.tasks;

import org.json.JSONObject;

public class Notification {

    public static Task updateStatus(String commitHash, String status, String description) {
        JSONObject json = new JSONObject();
        json.put("state", status);
        json.put("description", description);
        System.out.println("Update commit status to " + status);
        ///repos/{owner}/{repo}/statuses/{sha}
        String url = "https://api.github.com/repos/fund-team/fund2/statuses/" + commitHash;
        return new HttpTask(url, json.toString());
    }

    public static Task webhookDiscord (String commitHash, String status, String description) {
        String urlBOT = "https://discord.com/api/webhooks/940992021664002058/BBqOrNymR6SgjCUlgBv2BUvyahkNRdIZz-6GDjAzHIKvz5m1teqbO1qr4b0HYkTaY4ps";
        String title = "Commit "+commitHash;
        String message = "Status: "+status+(status.equals("failure")? ", "+description:"");
        String color = (status.equals("success")? "5887123":(status.equals("failure")? "15025485":"16246360"));
        JSONObject json = new JSONObject();
        JSONObject embeds = new JSONObject();
        json.put("embeds", new JSONObject[]{embeds});
        embeds.put("title", title);
        embeds.put("description", message);
        embeds.put("color", color);
        json.put("username", "Commit Status Notifier");

        return new HttpTask(urlBOT, json.toString());
    }
}

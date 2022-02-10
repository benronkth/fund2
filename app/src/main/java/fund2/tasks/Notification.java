package fund2.tasks;

public class Notification {

    public static Task updateStatus(String commitHash, String status, String description) {
        System.out.println("Update commit status to " + status);
        String url = "https://api.github.com/repos/fund-team/fund2/statuses/" + commitHash;
        return new Task("curl -X POST -H \"Accept: application/vnd.github.v3+json\" -d \"{\\\"state\\\": \\\"" + status + "\\\", \\\"description\\\": \\\"" + description + "\\\"}\" "+ url);
    }

    public static Task webhookDiscord (String commitHash, String status, String description) {
        String urlBOT = "https://discord.com/api/webhooks/940992021664002058/BBqOrNymR6SgjCUlgBv2BUvyahkNRdIZz-6GDjAzHIKvz5m1teqbO1qr4b0HYkTaY4ps";
        String title = "Commit "+commitHash;
        String message = "Status: "+status+(status.equals("failure")? ", "+description:"");
        String color = (status.equals("success")? "5887123":(status.equals("failure")? "15025485":"16246360"));
        String JSON = "{" +
                    "\\\"embeds\\\": [{" +
                        "\\\"title\\\": \\\""+title+"\\\", " +
                        "\\\"description\\\": \\\""+ message +"\\\", " +
                        "\\\"color\\\": \\\""+color+"\\\"}], " +
                    "\\\"username\\\": \\\"Commit Status Notifier\\\"" +
                "}";
        return new Task("curl -X POST -H \"Content-Type: application/json\" -d \""+JSON+"\" "+ urlBOT);
    }
}

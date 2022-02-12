package fund2.tasks;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class HttpTask extends Task {
    public String url;
    public String content;
    public String auth;

    public HttpTask(String url, String content) {
        super("HTTP:" + url + " " + content);
        this.url = url;
        this.content = content;
    }

    public HttpTask(String url, String content, String user, String token) {
        super("HTTP:" + url + " " + content);
        this.url = url;
        this.content = content;
        String authMessage = user + ":" + token;
        byte[] encodedAuth = Base64.getEncoder().encode(authMessage.getBytes(StandardCharsets.UTF_8));
        auth = "Basic " + new String(encodedAuth);
    }

    @Override
    public TaskResult execute() {
        TaskResult result = new TaskResult();
        try {
            URL url = new URL(this.url);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.setRequestProperty("Content-Type", "application/json");

            if (auth != null) {
                http.setRequestProperty("Authorization", auth);
            }

            byte[] out = content.getBytes(StandardCharsets.UTF_8);

            OutputStream stream = http.getOutputStream();
            stream.write(out);

            result.addLogLine(http.getResponseCode() + " " + http.getResponseMessage());
            http.disconnect();
            if (http.getResponseCode() >= 300) {
                result.exitCode = http.getResponseCode();
            } else {
                result.exitCode = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.addLogLine("Error occured, see CI server logs");
            result.exitCode = -1;
        }
        return result;

    }
}

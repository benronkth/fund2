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
    public int execute() {
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

            System.out.println(http.getResponseCode() + " " + http.getResponseMessage());
            http.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return 0;

    }
}

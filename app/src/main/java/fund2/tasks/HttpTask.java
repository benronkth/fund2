package fund2.tasks;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Define a type of task that stores all information needed to later send an
 * HTTP request. When the Httptask is executed, an HTTP request is sent to the
 * specified URL with the specified content. The request may contain an
 * authentication.
 */

public class HttpTask extends Task {
    public String url;
    public String content;
    public String auth;

    /**
     * Create a Httptask storing the information for a POST request without
     * authentication
     *
     * @param url String, URL of the target
     * @param content String, content of the POST request to be sent
     */

    public HttpTask(String url, String content) {
        super("HTTP:" + url + " " + content);
        this.url = url;
        this.content = content;
    }

    /**
     * Create a Httptask storing the information for a POST request with
     * authentication
     *
     * @param url URL of the target
     * @param content Content of the POST request to be sent
     * @param user Name of the GitHub user stored as an environment
     *             variable
     * @param token GitHub user token stored as an environment variable
     */

    public HttpTask(String url, String content, String user, String token) {
        super("HTTP:" + url + " " + content);
        this.url = url;
        this.content = content;
        String authMessage = user + ":" + token;
        byte[] encodedAuth = Base64.getEncoder().encode(authMessage.getBytes(StandardCharsets.UTF_8));
        auth = "Basic " + new String(encodedAuth);
    }

    /**
     * Execute the task sending a POST request with the information
     * contained in the Httptask element
     *
     * @return Integer: the exit value of the process. As
     *         {@link Task#execute()}, 0 indicates normal termination,
     *         -1 exception, and any other integer the code of an
     *         error that occurred.
     */

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

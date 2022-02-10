package fund2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.json.JSONException;
import org.json.JSONObject;

import fund2.tasks.Tasks;

/**
 * Skeleton of a ContinuousIntegrationServer which acts as webhook
 * @see <a href="https://github.com/KTH-DD2480/smallest-java-ci">https://github.com/KTH-DD2480/smallest-java-ci</a>
 * See the Jetty documentation for API documentation of those classes.
 */
public class ContinuousIntegrationServer extends AbstractHandler {

    /**
     * Handle requests sent from the GitHub webhook after push events on the project repository.
     * An action is performed only if the content is a JSON object sent on "/push" path.
     * Calls the "OnPush" method to perform the build and tests of the corresponding commit.
     */
    @Override
    public void handle(String target,
            Request baseRequest,
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        String result = "";

        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
            result += line;
        }
        reader.close();
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(result);
        } catch (JSONException err) {
            System.out.println(err.toString());
            return;
        }

        if (target.equals("/push")) {
            respondOK("CI received push payload", baseRequest, response);
            onPush(jsonObject);
        }

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);

        response.getWriter().println("CI job done");
    }

    /**
     * Send a 200 response to the GitHub server
     */
    public static void respondOK(String payload,
            Request baseRequest,
            HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);

        try {
            response.getWriter().println(payload);
            System.out.println("Sent response");
        } catch (IOException err) {
            System.out.println(err);
            return;
        }
    }

    /**
     * Launch the build and tests on the commit which hash and branch are contained in the JSON
     * object received from the GitHub webhook after a push event. The commit is build only if
     * on the main branch
     *
     * @param json A JSON object sent by GitHub webhook containing all the information about a commit
     */
    public static void onPush(JSONObject json) {
        System.out.println("Got push");
        String ref = json.getString("ref");
        String commit = json.getString("after");
        String branch = ref.split("/")[2];
        Tasks.gitTest(branch);
        if (branch.equals("main")) {
            Tasks.gitBuild(branch, commit);
        }
    }

}

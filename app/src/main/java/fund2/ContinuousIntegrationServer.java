package fund2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.util.ajax.JSONObjectConvertor;
import org.json.JSONException;
import org.json.JSONObject;

import fund2.tasks.Tasks;

/**
 * Skeleton of a ContinuousIntegrationServer which acts as webhook
 * See the Jetty documentation for API documentation of those classes.
 */
public class ContinuousIntegrationServer extends AbstractHandler {
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
            onPush(jsonObject);
        }

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);

        // here you do all the continuous integration tasks
        // for example
        // 1st clone your repository
        // 2nd compile the code

        response.getWriter().println("CI job done");
    }

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

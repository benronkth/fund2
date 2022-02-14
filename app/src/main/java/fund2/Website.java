package fund2;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

/**
 * Class containing static methods to generate website content
 */
public class Website {

    /**
     * Generates a HTML document with all tests and builds
     * 
     * @return the HTML document as a string
     */
    public static String getIndex() {
        try {
            HashMap<String, Object> scope = new HashMap<>();
            scope.put("branches", getBranches());
            MustacheFactory mf = new DefaultMustacheFactory();
            Mustache ms = mf.compile("web/index.html.mustache");
            StringWriter out = new StringWriter();
            Writer writer = ms.execute(out, scope);
            writer.flush();
            return out.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Error occured while generating index file";
    }

    /**
     * Reads all data necesery to generate HTML code for all branches
     * 
     * @return List of all branches
     */
    public static List<Object> getBranches() {
        File branchesFolder = new File("./results");
        List<Object> branches = new ArrayList<>();

        for (File branchFolder : branchesFolder.listFiles()) {
            if (branchFolder.isDirectory()) {
                HashMap<String, Object> branch = new HashMap<>();
                branch.put("name", branchFolder.getName());
                branch.put("commits", getCommits(branchFolder));
                branches.add(branch);
            }
        }
        return branches;
    }

    /**
     * Reads all data necesery to generate HTML code for all commits in a branch
     * 
     * @param branchFolder the folder to search for commits in
     * @return List of all commits in the folder
     */
    public static List<Object> getCommits(File branchFolder) {
        List<Object> commits = new ArrayList<>();
        for (File commitFolder : branchFolder.listFiles()) {
            if (commitFolder.isDirectory()) {
                HashMap<String, Object> commit = new HashMap<>();
                commit.put("hash", commitFolder.getName());
                String test = readOrNull(commitFolder.getPath() + "/test-result.txt");
                if (test != null) {
                    commit.put("test", test);
                }
                String build = readOrNull(commitFolder.getPath() + "/build-result.txt");
                if (build != null) {
                    commit.put("build", build);
                }
                commits.add(commit);
            }
        }
        return commits;
    }

    /**
     * Reads the contetn of a file, or return null
     * 
     * @param file Path of file to read from
     * @return file contents or null
     */
    public static String readOrNull(String file) {
        File f = new File(file);
        if (f.exists()) {
            try {
                String content = Files.readString(Path.of(file));
                return content;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    /**
     * Reads the logs from the specified target
     * 
     * @param target target branch + commit hash + test or build
     * @return the logs of the test or build
     */
    public static String getLogs(String target) {
        target = target.replace("/logs", "");
        return readOrNull("results" + target + "-log.txt");
    }

    /**
     * Downloads the build jar from the specified branch + commit hash
     * 
     * @param request  Jetty request
     * @param response Jetty response
     * @param target   branch + commit hash
     */
    public static void downloadBuild(HttpServletRequest request,
            HttpServletResponse response, String target) {
        try {
            target = target.replace("/builds", "");
            String filePath = "results" + target + "/build.jar";

            File downloadFile = new File(filePath);
            FileInputStream inStream = new FileInputStream(downloadFile);

            String mimeType = "application/java-archive";

            response.setContentType(mimeType);
            response.setContentLength((int) downloadFile.length());

            // forces download
            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
            response.setHeader(headerKey, headerValue);

            // obtains response's output stream
            OutputStream outStream = response.getOutputStream();

            byte[] buffer = new byte[4096];
            int bytesRead = -1;

            while ((bytesRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }

            inStream.close();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

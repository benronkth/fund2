package fund2.tasks;

import java.io.File;

/**
 * Class containing static methods dealing with file management. Each action is
 * transposed into a static method returning a Task that execute the specified
 * action
 */

public class FileUtils {

    public static Task copy(String source, String destination) {
        return new CmdTask("cp " + source + " " + destination);
    }

    public static Task ls() {
        return new CmdTask("ls");
    }

    public static Task remove(String dir) {
        return new CmdTask("rm -rf " + dir);
    }

    public static Task setWorkingDir(String dir) {
        return new JavaTask("Change working dir to " + dir, (result) -> {
            CmdTask.workingDirectory = new File(dir);
            result.exitCode = 0;
        });
    }

    public static Task mkDirs(String dir) {
        return new JavaTask("Making directory " + dir, (result) -> {
            new File(dir).mkdirs();
            result.exitCode = 0;
        });
    }
}

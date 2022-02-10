package fund2.tasks;

/**
 * Class containing the two static method to build and test a commit from its
 * branch and hash.
 * Those methods are built as series of tasks (Task instances).
 */

public class Tasks {

    /**
     * Run gradle tests on the latest commit of a branch
     *
     * @param branch Name of a git branch
     * @return Integer: the exit value of the series of tasks executions. As
     *         {@link Task#execute()},
     *         0 indicates normal termination, -1 exception, and any other integer
     *         the code of an error
     *         that occurred.
     */
    public static int gitTest(String branch) {
        return Tasks.all(new Task[] {
                Git.fetch(),
                Git.switchTo(branch),
                Gradle.test(),
        });
    }

    /**
     * Build a commit
     *
     * @param branch     Name of a git branch
     * @param commitHash Commit hash (unique identifier) of the commit to build
     * @return Integer: the exit value of the series of tasks executions. As
     *         {@link Task#execute()},
     *         0 indicates normal termination, -1 exception, and any other integer
     *         the code of an error
     *         that occurred.
     */
    public static int gitBuild(String branch, String commitHash) {
        String buildName = branch + "-" + commitHash + ".jar";
        int result = Tasks.all(new Task[] {
                // Notification.updateStatus(commitHash, "pending", ""),
                Notification.webhookDiscord(commitHash, "pending", ""),
                Git.fetch(),
                Git.switchTo(branch),
                Gradle.build(),
                File.copy("./app/build/libs/app-all.jar", "./app/build/archive/" + buildName),
                File.copy("./app/build/libs/app-all.jar", "./app/build/archive/latest.jar"),
                // Notification.updateStatus(commitHash, "success", ""),
                Notification.webhookDiscord(commitHash, "success", ""),
                Systemd.restart("fund2")
        });
        if (result != 0) {
            // Notification.updateStatus(commitHash, "failure", "Build failed with code
            // "+result).execute();
            Notification.webhookDiscord(commitHash, "failure", "Build failed with code " + result).execute();
        }
        return result;
    }

    /**
     * Iterate through a list of Tasks and execute them in order. The iteration
     * stops and the method
     * returns in case an error occurs or an exception is caught.
     *
     * @param tasks A list of Task instances, can be seen as a list of console
     *              commands to execute
     * @return Integer: the exit value of the series of tasks executions. As
     *         {@link Task#execute()},
     *         0 indicates normal termination, -1 exception, and any other integer
     *         the code of an error
     *         that occurred.
     */
    public static int all(Task[] tasks) {

        for (Task task : tasks) {
            System.out.println("=== Beginning task \"" + task.command + "\"");
            int result = task.execute();
            if (result != 0) {
                System.out.println("=== Task failed with code " + result);
                return result;
            }
            System.out.println("=== Task completed");
        }
        return 0;
    }
}

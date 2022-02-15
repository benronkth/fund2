package fund2.tasks;

import java.io.File;

/**
 * Class containing the two static method to build and test a commit from its
 * branch and hash.
 * Those methods are built as series of tasks (Task instances).
 */

public class Tasks {

    /**
     * Run gradle tests on the latest commit of a branch
     *
     * @param branch     Name of a git branch
     * @param commitHash Commit hash (unique identifier) of the commit to test
     * @return TaskResult: the results from executing all tasks
     *         {@link Task#execute()},
     *         exit code 0 indicates normal termination, -1 exception, and any other
     *         integer
     *         the code of an error
     *         that occurred.
     */
    public static TaskResult gitTest(String branch, String commitHash) {
        String workingDir = "./tests/" + branch.replaceAll("/", "-") + "/" + commitHash;
        String resultDir = "./results/" + branch.replaceAll("/", "-") + "/" + commitHash;
        TaskResult result = Tasks.all(new Task[] {
                Notification.github(commitHash, "pending", ""),
                Notification.discord(commitHash, branch, "test pending", "", Notification.PENDING),
                FileUtils.mkDirs(workingDir),
                FileUtils.mkDirs(resultDir),
                FileUtils.setWorkingDir(workingDir),
                Git.cloneRepo(),
                Git.switchTo(branch),
                Gradle.test(),
                FileUtils.setWorkingDir("."),
                FileUtils.remove(workingDir),
                Notification.github(commitHash, "success", ""),
                Notification.discord(commitHash, branch, "test success", "", Notification.SUCCESS),
        });
        if (result.exitCode != 0) {
            String error = "Test failed with code " + result.exitCode;
            Notification.github(commitHash, "failure", error).execute();
            Notification.discord(commitHash, branch, "test failure", error, Notification.FAILURE).execute();
        }
        result.save(resultDir, "test");
        return result;
    }

    /**
     * Build a commit
     *
     * @param branch     Name of a git branch
     * @param commitHash Commit hash (unique identifier) of the commit to build
     * @return TaskResult: the results from executing all tasks
     *         {@link Task#execute()},
     *         exit code 0 indicates normal termination, -1 exception, and any other
     *         integer
     *         the code of an error
     *         that occurred.
     */
    public static TaskResult gitBuild(String branch, String commitHash) {
        String workingDir = "./builds/" + branch.replaceAll("/", "-") + "/" + commitHash;
        String resultDir = "./results/" + branch.replaceAll("/", "-") + "/" + commitHash;
        TaskResult result = Tasks.all(new Task[] {
                Notification.github(commitHash, "pending", ""),
                Notification.discord(commitHash, branch, "build pending", "", Notification.PENDING),
                FileUtils.mkDirs(workingDir),
                FileUtils.mkDirs(resultDir),
                FileUtils.setWorkingDir(workingDir),
                Git.cloneRepo(),
                Git.switchTo(branch),
                Gradle.build(),
                FileUtils.setWorkingDir("."),
                FileUtils.copy(workingDir + "/app/build/libs/app-all.jar", resultDir + "/build.jar"),
                FileUtils.copy(workingDir + "/app/build/libs/app-all.jar", "./latest.jar"),
                FileUtils.remove(workingDir),
                Notification.github(commitHash, "success", ""),
                Notification.discord(commitHash, branch, "build success", "", Notification.SUCCESS),
        });
        if (result.exitCode == 0) {
            Systemd.restart("fund2").execute();
        } else {
            String error = "Build failed with code " + result.exitCode;
            Notification.github(commitHash, "failure", error).execute();
            Notification.discord(commitHash, branch, "build failure", error, Notification.FAILURE).execute();
        }
        result.save(resultDir, "build");
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
    public static TaskResult all(Task[] tasks) {
        TaskResult totalResult = new TaskResult();
        for (Task task : tasks) {
            totalResult.addLogLine("=== Beginning task \"" + task.name + "\"");
            TaskResult result = task.execute();
            totalResult.log.addAll(result.log);
            if (result.exitCode != 0) {
                totalResult.addLogLine("=== Task failed with code " + result.exitCode);
                totalResult.exitCode = result.exitCode;
                return totalResult;
            }
            totalResult.addLogLine("=== Task completed");
        }
        totalResult.exitCode = 0;
        return totalResult;
    }
}

package fund2.tasks;

public class Tasks {

    public static int gitTest(String branch) {
        return Tasks.all(new Task[] {
                Git.fetch(),
                Git.switchTo(branch),
                Gradle.test(),
        });
    }


    public static int gitBuild(String branch, String commitHash) {
        String buildName = branch + "-" + commitHash + ".jar";
        int result = Tasks.all(new Task[] {
                //Notification.updateStatus(commitHash, "pending", ""),
                Notification.webhookDiscord(commitHash, "pending", ""),
                Git.fetch(),
                Git.switchTo(branch),
                Gradle.build(),
                File.copy("./app/build/libs/app-all.jar", "./app/build/archive/" + buildName),
                File.copy("./app/build/libs/app-all.jar", "./app/build/archive/latest.jar"),
                //Notification.updateStatus(commitHash, "success", ""),
                Notification.webhookDiscord(commitHash, "success", ""),
        });
        if (result != 0) {
            //Notification.updateStatus(commitHash, "failure", "Build failed with code "+result).execute();
            Notification.webhookDiscord(commitHash, "failure", "Build failed with code "+result).execute();
        }
        return result;
    }

    public static int all(Task[] tasks) {

        for (Task task : tasks) {
            System.out.println("=== Beggining task \"" + task.command + "\"");
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

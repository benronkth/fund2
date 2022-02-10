package fund2.tasks;

/**
 * Class containing static methods dealing with git actions. Each action is transposed
 * into a static method returning a Task "storing" the corresponding console command.
 */

public class Git {
    public static Task fetch() {
        return new Task("git fetch");
    }

    public static Task switchTo(String branch) {
        return new Task("git switch " + branch);
    }
}

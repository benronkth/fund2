package fund2.tasks;

/**
 * Class containing static methods dealing with git actions. Each action is
 * transposed
 * into a static method returning a CmdTask "storing" the corresponding console
 * command.
 */

public class Git {
    public static Task fetch() {
        return new CmdTask("git fetch");
    }

    public static Task switchTo(String branch) {
        return new CmdTask("git switch " + branch);
    }

    public static Task pull() {
        return new CmdTask("git pull");
    }
}

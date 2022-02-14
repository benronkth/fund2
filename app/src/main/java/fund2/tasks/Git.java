package fund2.tasks;

import fund2.App;

/**
 * Class containing static methods dealing with git actions. Each action is
 * transposed
 * into a static method returning a CmdTask "storing" the corresponding console
 * command.
 */

public class Git {
    public static Task switchTo(String branch) {
        return new CmdTask("git switch " + branch);
    }

    public static Task cloneRepo() {
        String url = App.dotenv.get("REPOURL");
        return new CmdTask("git clone " + url + " .");
    }
}

package fund2.tasks;

public class Git {
    public static Task fetch() {
        return new Task("git fetch");
    }

    public static Task switchTo(String branch) {
        return new Task("git switch " + branch);
    }
}

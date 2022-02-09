package fund2.functions;

public class Git {
    public static void fetch() {
        Functions.exec("git fetch");
    }

    public static void switchTo(String branch) {
        Functions.exec("git switch " + branch);
    }
}

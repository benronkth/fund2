package fund2.functions;

public class Gradle {
    public static void build() {
        Functions.exec("./gradlew shadowJar");
    }

    public static void test() {
        Functions.exec("./gradlew test");
    }
}

package fund2.tasks;

public class Gradle {
    public static Task build() {
        return new Task("./gradlew shadowJar");
    }

    public static Task test() {
        return new Task("./gradlew test");
    }
}

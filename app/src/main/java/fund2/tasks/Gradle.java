package fund2.tasks;

/**
 * Class containing static methods dealing with gradle actions. Each action is
 * transposed into a static method returning a Task "storing" the corresponding console command.
 */

public class Gradle {
    public static Task build() {
        return new Task("./gradlew shadowJar");
    }

    public static Task test() {
        return new Task("./gradlew test");
    }
}

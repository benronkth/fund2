package fund2;

import fund2.tasks.*;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class GradleTest {

    // Positive tests
    @Test
    public void gradleBuildSuccessful() {
        // Create new directory and go in
        String workingDir = "./build/tmp/gradleBuildTest";
        FileUtils.mkDirs(workingDir).execute();
        FileUtils.setWorkingDir(workingDir).execute();

        // Clone the project in this directory
        Git.cloneRepo("https://github.com/fund-team/fund2.git").execute();

        // Move one folder ahead
        FileUtils.setWorkingDir(workingDir).execute();

        // Build with gradle and take the result
        TaskResult result = Gradle.build().execute();

        // Return to app directory and remove the test directory
        FileUtils.setWorkingDir(".").execute();
        FileUtils.remove(workingDir).execute();

        assertTrue(result.exitCode == 0);
    }

    // Negative test
    @Test
    public void gradleBuildFailing() {
        // Create new directory and go in
        String workingDir = "./build/tmp/gradleBuildTest";
        FileUtils.mkDirs(workingDir).execute();
        FileUtils.setWorkingDir(workingDir).execute();

        // Clone the project in this directory
        Git.cloneRepo("https://github.com/fund-team/fund2.git").execute();

        // Delete the gradlew file
        File gradlewFile = new File("./build/tmp/gradleBuildTest/gradlew");
        gradlewFile.delete();

        // Build with gradle
        TaskResult resultBuild = Gradle.build().execute();
        Boolean built = resultBuild.exitCode == 0;
        assertFalse(built);

        // Return to app directory and remove the test directory
        FileUtils.setWorkingDir(".").execute();
        FileUtils.remove(workingDir).execute();
    }
}

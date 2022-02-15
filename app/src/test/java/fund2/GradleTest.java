package fund2;

import fund2.tasks.*;
import org.checkerframework.checker.units.qual.C;
import org.junit.Test;

import java.io.File;
import java.util.logging.Filter;

import static org.junit.Assert.assertTrue;

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

    // Negative tests
}


package fund2;

import org.junit.Test;

import fund2.tasks.FileUtils;
import fund2.tasks.Task;
import fund2.tasks.TaskResult;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.Assert.assertFalse;

public class FileUtilsTest {

    // Successful tests

    @Test
    public void assertThatCopyCopiesAFileIntoNewFileCorreclty() {
        boolean result = false;
        String inputFilePath = "./src/test/java/fund2/RandomText";
        String outputFilePath = "./src/test/java/fund2/CopiedRandomText";
        File inputFile = new File(inputFilePath);
        File outputFile = new File(outputFilePath);

        try {
            inputFile.createNewFile();
            PrintWriter writer = new PrintWriter(inputFile, "UTF-8");
            for (int i = 0; i < 20; i++) {
                writer.println("Some random numbers" + Math.random());
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileUtils.copy(inputFilePath, outputFilePath).execute();

        try {
            byte[] f1 = Files.readAllBytes(Paths.get(inputFilePath));
            byte[] f2 = Files.readAllBytes(Paths.get(outputFilePath));
            result = Arrays.equals(f1, f2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // clean up
        inputFile.delete();
        outputFile.delete();

        assertTrue(result);
    }

    @Test
    public void assertThatRemoveDeletesADirectoryCorreclty() {
        boolean result = false;
        String pathToDir = "./src/test/java/fund2/ARandomDirThatShouldBeRemoved";
        File dir = new File(pathToDir);
        try {
            dir.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileUtils.remove(pathToDir).execute();
        result = dir.exists();

        // clean up in case file utils didnt remove the dir
        dir.delete();

        assertFalse(result);
    }

    @Test
    public void assertThatMkDirsCreatesDirectoryCorreclty() {
        boolean result = false;
        String pathToDir = "./src/test/java/fund2/ARandomDir";
        FileUtils.mkDirs(pathToDir).execute();
        File dir = new File(pathToDir);
        result = dir.exists();

        // clean up
        dir.delete();

        assertTrue(result);
    }

    @Test
    public void assertThatLsIsExecutedWithExitCode0() {
        Task ls = FileUtils.ls();
        TaskResult theResult = ls.execute();
        assertTrue(theResult.exitCode == 0);
    }

    @Test
    public void assertThatSetWorkingDirIsExecutedWithExitCode0() {

        String pathToDir = "./src/test/java/fund2";
        Task setWorkingDir = FileUtils.setWorkingDir(pathToDir);
        TaskResult theResult = setWorkingDir.execute();
        assertTrue(theResult.exitCode == 0);
    }

}

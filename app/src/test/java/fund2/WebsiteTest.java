package fund2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.junit.Test;

import fund2.tasks.FileUtils;

public class WebsiteTest {

    // Successful tests

    @Test
    public void assertThatReadOrNullReadsFile() {
        String text = "I am some text";
        String path = "./build/tmp/file.txt";
        File inputFile = new File(path);

        try {
            inputFile.createNewFile();
            PrintWriter writer = new PrintWriter(inputFile);
            writer.print(text);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String result = Website.readOrNull(path);

        assertEquals(text, result);

        inputFile.delete();
    }

    @Test
    public void assertThatgetLogsReadsFile() {
        String text = "I am some text";
        FileUtils.mkDirs("./results/tmp").execute();
        String target = "./results/tmp/test-log.txt";
        File inputFile = new File(target);

        try {
            inputFile.createNewFile();
            PrintWriter writer = new PrintWriter(inputFile);
            writer.print(text);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String result = Website.getLogs("/logs/tmp/test");

        assertEquals(text, result);

        FileUtils.remove("./results").execute();

        inputFile.delete();
    }

    // Negative tests

    @Test
    public void assertThatReadOrNullReturnsNullWhenNotFound() {
        String path = "./build/tmp/random.txt";

        String result = Website.readOrNull(path);

        assertNull(result);
    }

    @Test
    public void assertThatgetLogsReturnsNullWhenNotFound() {
        String target = "/logs/random/test";

        String result = Website.getLogs(target);

        assertNull(result);
    }
}

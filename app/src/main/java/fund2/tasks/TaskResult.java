package fund2.tasks;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * TaskResults store the results of executing a task.
 * It contains both the exit code of the task, and the logs during execution
 */
public class TaskResult {
    public int exitCode;
    public List<String> log = new ArrayList<>();

    public void addLogLine(String line) {
        System.out.println(line);
        log.add(line);
    }

    /**
     * Saves the log and success/failure as txt files to the specified folder
     * 
     * @param folder folder to save files into
     * @param name   name prefix for files
     */
    public void save(String folder, String name) {
        try {
            FileWriter logFile = new FileWriter(folder + "/" + name + "-log.txt");
            FileWriter resultFile = new FileWriter(folder + "/" + name + "-result.txt");
            for (String line : log) {
                logFile.write(line + "\n");
            }
            if (exitCode == 0) {
                resultFile.write("success");
            } else {
                resultFile.write("failure");
            }
            logFile.close();
            resultFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

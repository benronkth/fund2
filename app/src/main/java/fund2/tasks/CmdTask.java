package fund2.tasks;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;

/**
 * Command task that stores a console command as a string to later be executed
 * (through the "execute"
 * method). Most actions (pulling, building, testing) performed by the
 * server are simple wrappers around a console command
 */
public class CmdTask extends Task {

    public static File workingDirectory = new File(".");

    public String command;

    public CmdTask(String command) {
        super("CMD: " + command);
        this.command = command;
    }

    /**
     * Execute the command stored in the command attribute as a console command
     *
     * @return TaskResult: The task results contains the exit value of the process.
     *         0 indicates normal
     *         termination (no exception caught nor error),
     *         -1 is for an exception, and any other integer corresponds to the code
     *         of an error that occurred.
     *         it also contains the logs during execution
     */
    @Override
    public TaskResult execute() {
        TaskResult result = new TaskResult();
        try {
            Process process = Runtime.getRuntime().exec(command, null, workingDirectory);

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                result.addLogLine(line);
            }
            reader.close();

            while ((line = errorReader.readLine()) != null) {
                result.addLogLine(line);
            }
            errorReader.close();
            result.exitCode = process.waitFor();

        } catch (Exception e) {
            e.printStackTrace();
            result.addLogLine("Error occured, see CI server logs");
            result.exitCode = -1;
        }
        return result;
    }
}

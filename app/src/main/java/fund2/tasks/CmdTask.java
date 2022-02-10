package fund2.tasks;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Basic task that stores a console command as a string to later be executed
 * (through the "execute"
 * method). All actions (pulling, building, testing, notifying) performed by the
 * server are at some
 * point stored as Tasks then executed in the desired order.
 */
public class CmdTask extends Task {

    public String command;

    public CmdTask(String command) {
        super("CMD: " + command);
        this.command = command;
    }

    /**
     * Execute the command stored in the command attribute as a console command
     *
     * @return Integer: the exit value of the process. 0 indicates normal
     *         termination (no exception caught nor error),
     *         -1 is for an exception, and any other integer corresponds to the code
     *         of an error that occurred.
     */
    @Override
    public int execute() {
        try {
            Process process = Runtime.getRuntime().exec(command);

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();

            while ((line = errorReader.readLine()) != null) {
                System.out.println(line);
            }
            errorReader.close();
            return process.waitFor();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}

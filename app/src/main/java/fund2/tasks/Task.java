package fund2.tasks;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Task {
    
    public String command;

    public Task(String command) {
        this.command = command;
    }

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

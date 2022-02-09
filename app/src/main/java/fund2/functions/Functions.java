package fund2.functions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Functions {
    
    public static void exec(String command) {
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
         
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
}

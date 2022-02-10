package fund2.tasks;

/**
 * Class containing static methods dealing with file management. Each action is
 * transposed into a static method returning a Task "storing" the corresponding console command.
 */

public class File {
    
    public static Task copy(String source, String destination) {
        return new Task("cp " + source + " " + destination);
    }
}

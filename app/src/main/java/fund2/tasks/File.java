package fund2.tasks;

public class File {
    
    public static Task copy(String source, String destination) {
        return new Task("cp " + source + " " + destination);
    }
}

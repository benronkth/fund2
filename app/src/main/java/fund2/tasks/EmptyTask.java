package fund2.tasks;

/**
 * Empty task to use in place of other tasks during debugging
 */
public class EmptyTask extends Task {

    public EmptyTask() {
        super("Empty task");
    }

    public TaskResult execute() {
        TaskResult result = new TaskResult();
        result.exitCode = 0;
        return result;
    }
}

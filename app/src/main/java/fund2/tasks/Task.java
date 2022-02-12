package fund2.tasks;

/**
 * Abstract class represeting a single task to be executed in the future
 */

public abstract class Task {

    public String name;

    public Task(String name) {
        this.name = name;
    }

    /**
     * Execute the task and return the exit code
     *
     * @return Integer: the exit value of the process. 0 indicates normal
     *         termination (no exception caught nor error),
     *         -1 is for an exception, and any other integer corresponds to the code
     *         of an error that occurred.
     */
    public abstract TaskResult execute();
}

package fund2.tasks;

interface TaskFunction {
    void run(TaskResult result);
}

/**
 * A task type that executes internal java code instead of a console command
 */

public class JavaTask extends Task {
    TaskFunction function;

    JavaTask(String name, TaskFunction fun) {
        super(name);
        function = fun;
    }

    @Override
    public TaskResult execute() {
        TaskResult result = new TaskResult();
        function.run(result);
        return result;
    }
}

package fund2;

import org.junit.Test;

import fund2.tasks.FileUtils;
import fund2.tasks.Git;
import fund2.tasks.Task;
import fund2.tasks.TaskResult;

import static org.junit.Assert.assertTrue;

public class GitTest {

    // Successful tests

    @Test
    public void gitCloneSuccessfully() {
        String workingDir = "./build/tmp/gitTest";
        FileUtils.mkDirs(workingDir).execute();
        FileUtils.setWorkingDir(workingDir).execute();

        TaskResult results = Git.cloneRepo("https://github.com/fund-team/fund2.git").execute();

        FileUtils.setWorkingDir(".").execute();
        FileUtils.remove(workingDir).execute();

        assertTrue(results.exitCode == 0);
    }

    @Test
    public void gitSwitchSuccessfully() {
        String startingBranch = Git.getBranchName();

        TaskResult results = Git.switchTo("main").execute();
        if (!startingBranch.equals("main")) {
            Git.switchTo(startingBranch).execute();
        }

        assertTrue(results.exitCode == 0);
    }

    // Failing tests

    @Test
    public void gitCloneFailing() {
        TaskResult results = Git.cloneRepo("invalid-url").execute();
        assertTrue(results.exitCode != 0);
    }

    @Test
    public void gitSwitchFailing() {
        TaskResult results = Git.switchTo("invalid-branch.").execute();
        assertTrue(results.exitCode != 0);
    }

}

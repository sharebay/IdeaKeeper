package lv.javaguru.java3.core.commands.tasklists;

import lv.javaguru.java3.core.commands.DomainCommandResult;
import lv.javaguru.java3.core.domain.tasklist.TaskList;

/**
 * Created by Fatum on 05-Nov-15.
 */
public class UpdateTaskListResult implements DomainCommandResult {

    private TaskList taskList;

    public UpdateTaskListResult(TaskList taskList) {
        this.taskList = taskList;
    }

    public TaskList getTaskList() {
        return taskList;
    }
}

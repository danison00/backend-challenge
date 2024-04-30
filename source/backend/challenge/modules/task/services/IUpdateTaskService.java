package backend.challenge.modules.task.services;

import backend.challenge.modules.task.dtos.TaskDTO;
import backend.challenge.modules.task.exceptions.TaskAlterationNotAvailable;
import backend.challenge.modules.task.exceptions.TaskNotFound;
import backend.challenge.modules.task.models.Task;

public interface IUpdateTaskService{

	Task execute(Task task) throws TaskNotFound, TaskAlterationNotAvailable;

}

package backend.challenge.modules.task.services;

import java.util.UUID;
import backend.challenge.modules.task.exceptions.TaskNotFound;

public interface IDeleteTaskService {

	void execute(UUID taskId) throws TaskNotFound;

}

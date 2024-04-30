package backend.challenge.modules.task.services;

import java.util.UUID;

import backend.challenge.modules.task.exceptions.TaskNotFound;
import backend.challenge.modules.task.models.Task;

public interface IRetrieveTaskByIdService {

	Task execute(UUID taskId) throws TaskNotFound;

}

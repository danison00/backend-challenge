package backend.challenge.modules.task.services;

import java.util.Optional;
import java.util.UUID;

import backend.challenge.modules.task.models.Task;

public interface IRetrieveTaskByIdService {

	Optional<Task> execute(UUID taskId);

}

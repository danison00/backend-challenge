package backend.challenge.modules.task.services;

import java.util.Optional;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;
import backend.challenge.modules.task.exceptions.TaskNotFound;
import backend.challenge.modules.task.models.Task;
import backend.challenge.modules.task.repositories.ITaskRepository;
@Singleton
public class RetrieveTaskByIdService implements IRetrieveTaskByIdService {

	private final ITaskRepository taskRepository;

	@Inject
	public RetrieveTaskByIdService(final ITaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}

	@Override
	public Task execute(UUID taskId) throws TaskNotFound{
		Optional<Task>  taskOpt = this.taskRepository.index(taskId);
		if(taskOpt.isEmpty()) throw new TaskNotFound();
		return taskOpt.get();
	}

	
}  
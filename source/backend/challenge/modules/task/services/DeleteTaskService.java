package backend.challenge.modules.task.services;

import backend.challenge.modules.task.exceptions.TaskNotFound;
import backend.challenge.modules.task.repositories.ITaskRepository;

import java.util.UUID;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DeleteTaskService implements IDeleteTaskService {

	private final ITaskRepository taskRepository;

	@Inject
	public DeleteTaskService(final ITaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}

	@Override
	public void execute(UUID taskId) throws TaskNotFound{

		if(!taskRepository.existsById(taskId)) throw new TaskNotFound();

		this.taskRepository.delete(taskId);
	}

}

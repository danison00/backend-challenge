package backend.challenge.modules.task.services;

import java.util.Optional;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.plaf.OptionPaneUI;

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
	public Optional<Task> execute(UUID taskId) {
		return this.taskRepository.index(taskId);
	}

	
}  
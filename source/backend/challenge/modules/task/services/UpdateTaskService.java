package backend.challenge.modules.task.services;

import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Singleton;
import backend.challenge.modules.task.exceptions.TaskAlterationNotAvailable;
import backend.challenge.modules.task.exceptions.TaskNotFound;
import backend.challenge.modules.task.models.Task;
import backend.challenge.modules.task.repositories.ITaskRepository;

@Singleton
public class UpdateTaskService implements IUpdateTaskService {

    private final ITaskRepository iTaskRepository;

    @Inject
    public UpdateTaskService(final ITaskRepository iTaskRepository) {
        this.iTaskRepository = iTaskRepository;
    }

    @Override
    public Task execute(Task taskUp) throws TaskNotFound, TaskAlterationNotAvailable {
        Optional<Task> taskOpt = iTaskRepository.index(taskUp.getId());
        if (taskOpt.isEmpty())
            throw new TaskNotFound();

        Task task = taskOpt.get();

        task.setDescription(taskUp.getDescription());
        task.setTitle(taskUp.getTitle());
        this.iTaskRepository.update(task);
        return task;

    }
}

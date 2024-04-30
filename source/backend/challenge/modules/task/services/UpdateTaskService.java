package backend.challenge.modules.task.services;

import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Singleton;

import backend.challenge.modules.task.dtos.TaskDTO;
import backend.challenge.modules.task.exceptions.TaskAlterationNotAvaliable;
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
    public Task execute(Task taskUp) throws TaskNotFound, TaskAlterationNotAvaliable {
        Optional<Task> taskOpt = iTaskRepository.index(taskUp.getId());

        if (taskOpt.isEmpty())
            throw new TaskNotFound();

        Task task = taskOpt.get();

        if (taskUp.getCreatedAt() != null)
            if (!taskUp.getCreatedAt().equals(task.getCreatedAt()))
                throw new TaskAlterationNotAvaliable();
        if (taskUp.getStatus() != null)
            if (taskUp.getStatus().equals(task.getStatus()))
                throw new TaskAlterationNotAvaliable();

        if (taskUp.getProgress() != task.getProgress())
            throw new TaskAlterationNotAvaliable();

        task.setDescription(taskUp.getDescription());
        task.setTitle(taskUp.getTitle());
        return this.iTaskRepository.update(task);

    }
}

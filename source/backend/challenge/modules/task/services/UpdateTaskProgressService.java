package backend.challenge.modules.task.services;

import java.util.Optional;
import javax.inject.Inject;
import javax.inject.Singleton;
import backend.challenge.modules.task.dtos.TaskProgressDTO;
import backend.challenge.modules.task.enums.TaskStatus;
import backend.challenge.modules.task.exceptions.ProgressTaskUpdateNotAvailable;
import backend.challenge.modules.task.exceptions.TaskAlterationNotAvailable;
import backend.challenge.modules.task.exceptions.TaskNotFound;
import backend.challenge.modules.task.models.Task;
import backend.challenge.modules.task.repositories.ITaskRepository;

@Singleton
public class UpdateTaskProgressService implements IUpdateTaskProgressService {

    private final ITaskRepository iTaskRepository;

    @Inject
    public UpdateTaskProgressService(ITaskRepository iTaskRepository) {
        this.iTaskRepository = iTaskRepository;
    }

    @Override
    public Task execute(TaskProgressDTO taskProgressDTO) throws TaskAlterationNotAvailable, TaskNotFound {

        if (taskProgressDTO.getProgress() > 100 || taskProgressDTO.getProgress() < 0) {
            throw new ProgressTaskUpdateNotAvailable();
        }

        Optional<Task> taskOpt = iTaskRepository.index(taskProgressDTO.getId());
        if (taskOpt.isEmpty())
            throw new TaskNotFound();

        Task task = taskOpt.get();
        task.setProgress(taskProgressDTO.getProgress());
        if (taskProgressDTO.getProgress() == 100) {
            task.setStatus(TaskStatus.COMPLETE);
        } else {
            task.setStatus(TaskStatus.PROGRESS);
        }

        iTaskRepository.update(task);
        return task;
    }

}
package backend.challenge.modules.task.services;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

import backend.challenge.modules.task.dtos.TaskProgressDTO;
import backend.challenge.modules.task.enums.TaskStatus;
import backend.challenge.modules.task.models.Task;
import backend.challenge.modules.task.repositories.ITaskRepository;

/**
 * UpdateTaskProgressService
 */
@Singleton
public class UpdateTaskProgressService implements IUpdateTaskProgressService {

    private final ITaskRepository iTaskRepository;

    @Inject
    public UpdateTaskProgressService(ITaskRepository iTaskRepository) {
        this.iTaskRepository = iTaskRepository;
    }

    @Override
    public Task execute(TaskProgressDTO taskProgressDTO) {

        if (taskProgressDTO.getProgress() > 100 || taskProgressDTO.getProgress() < 0) {
            throw new RuntimeException("O progresso deve estar entre 0 e 100.");
        }

        Optional<Task> taskOpt = iTaskRepository.index(taskProgressDTO.getId());
        if (taskOpt.isEmpty())
            throw new RuntimeException("Tarefa não encontrada.");

        Task task = taskOpt.get();
        task.setProgress(taskProgressDTO.getProgress());
        iTaskRepository.update(task);
        return task;
    }


}
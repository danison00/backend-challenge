package backend.challenge.modules.task.services;

import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Singleton;

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
    public Task execute(Task taskUp) {
        Optional<Task> taskOpt = iTaskRepository.index(taskUp.getId());

        if (taskOpt.isEmpty())
            throw new RuntimeException("Tarefa não encontrada.");

        Task task = taskOpt.get();
        if (!taskUp.getId().equals(task.getId()) ||
                !taskUp.getCreatedAt().equals(task.getCreatedAt()) ||
                taskUp.getProgress() != task.getProgress() ||
                !task.getStatus().equals(taskUp.getStatus())) throw new RuntimeException("É apenas possível alterar o Título e Descrição nesta operação.");

    

        return this.iTaskRepository.update(task);
    }

}

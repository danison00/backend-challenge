package backend.challenge.modules.task.services;

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
    public Task execute(Task task) {

       return this.iTaskRepository.update(task);
    }

}

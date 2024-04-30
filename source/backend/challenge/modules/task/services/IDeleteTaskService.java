package backend.challenge.modules.task.services;

import java.util.UUID;

public interface IDeleteTaskService {

	void execute(UUID taskId);

}

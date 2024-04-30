package backend.challenge.modules.task.infra.http.controllers;

import backend.challenge.modules.task.dtos.TaskProgressDTO;
import backend.challenge.modules.task.exceptions.ProgressTaskUpdateNotAvailable;
import backend.challenge.modules.task.exceptions.TaskAlterationNotAvailable;
import backend.challenge.modules.task.exceptions.TaskNotFound;
import backend.challenge.modules.task.infra.http.views.TaskProgressView;
import backend.challenge.modules.task.services.*;
import kikaha.urouting.api.*;
import java.util.UUID;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
@Path("tasks/progress")
public class TaskProgressController {

	private final IUpdateTaskProgressService updateTaskProgressService;

	@Inject
	public TaskProgressController(final IUpdateTaskProgressService updateTaskProgressService) {
		this.updateTaskProgressService = updateTaskProgressService;
	}

	@PUT
	@Path("single/{taskId}")
	public Response updateProgress(@PathParam("taskId") String taskId, TaskProgressView taskProgressView) {
		TaskProgressDTO progressDTO = TaskProgressDTO.create().setId(UUID.fromString(taskId))
				.setProgress(taskProgressView.getProgress());

		try {
			this.updateTaskProgressService.execute(progressDTO);
			return DefaultResponse.ok();

		} catch (ProgressTaskUpdateNotAvailable | TaskNotFound e) {
			return DefaultResponse.badRequest().entity(e.getMessage());
		}

	}

}

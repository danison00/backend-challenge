package backend.challenge.modules.task.infra.http.controllers;

import backend.challenge.modules.task.dtos.TaskDTO;
import backend.challenge.modules.task.exceptions.TaskAlterationNotAvaliable;
import backend.challenge.modules.task.exceptions.TaskNotFound;
import backend.challenge.modules.task.infra.http.views.TaskView;
import backend.challenge.modules.task.models.Task;
import backend.challenge.modules.task.services.*;
import kikaha.urouting.api.*;
import java.util.Optional;
import java.util.UUID;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
@Path("tasks")
public class TaskController {

	private final ICreateTaskService createTaskService;
	private final IDeleteTaskService deleteTaskService;
	private final IRetrieveAllTasksService retrieveAllTasksService;
	private final IRetrieveTaskByIdService retrieveTaskByIdService;
	private final IUpdateTaskService updateTaskService;

	@Inject
	public TaskController(
			final ICreateTaskService createTaskService,
			final IDeleteTaskService deleteTaskService,
			final IRetrieveAllTasksService retrieveAllTasksService,
			final IRetrieveTaskByIdService retrieveTaskByIdService,
			final IUpdateTaskService iUpdateTaskService) {
		this.createTaskService = createTaskService;
		this.deleteTaskService = deleteTaskService;
		this.retrieveAllTasksService = retrieveAllTasksService;
		this.retrieveTaskByIdService = retrieveTaskByIdService;
		this.updateTaskService = iUpdateTaskService;
	}

	@GET
	public Response show() {
		this.retrieveAllTasksService.execute();
		return DefaultResponse.ok().entity(retrieveAllTasksService.execute());
	}

	@GET
	@Path("single/{taskId}")
	public Response index(@PathParam("taskId") String taskId) {

		try {
			Task taskOpt = this.retrieveTaskByIdService.execute(UUID.fromString(taskId));
			return DefaultResponse.ok().entity(taskOpt);

		} catch (TaskNotFound e) {
			return DefaultResponse.badRequest().entity(e.getMessage());
		}

	}

	@POST
	public Response create(TaskView taskView) {

		TaskDTO taskDto = TaskDTO.create().setTitle(taskView.getTitle()).setDescription(taskView.getDescription());
		this.createTaskService.execute(taskDto);

		return DefaultResponse.created();
	}

	@PUT
	@Path("single/{taskId}")
	public Response update(@PathParam("taskId") String taskId, TaskDTO taskDto) {

		try {
			Task task = new Task();
			task.setId(UUID.fromString(taskId));
			task.setDescription(taskDto.getDescription());
			task.setTitle(taskDto.getTitle());
			this.updateTaskService.execute(task);
			return DefaultResponse.ok();
		} catch (TaskAlterationNotAvaliable | TaskNotFound e) {

			return DefaultResponse.badRequest().entity(e.getMessage());
		} catch (Exception e) {
			return DefaultResponse.serverError();
		}

		// return DefaultResponse.ok();
	}

	@DELETE
	@Path("single/{taskId}")
	public Response delete(@PathParam("taskId") String taskId) {

		this.deleteTaskService.execute(UUID.fromString(taskId));
		return DefaultResponse.ok();
	}

}

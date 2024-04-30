package backend.challenge.modules.task.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import backend.challenge.modules.task.enums.TaskStatus;
import backend.challenge.modules.task.exceptions.TaskNotFound;
import backend.challenge.modules.task.infra.http.controllers.TaskController;
import backend.challenge.modules.task.models.Task;
import backend.challenge.modules.task.services.ICreateTaskService;
import backend.challenge.modules.task.services.IDeleteTaskService;
import backend.challenge.modules.task.services.IRetrieveAllTasksService;
import backend.challenge.modules.task.services.IRetrieveTaskByIdService;
import backend.challenge.modules.task.services.IUpdateTaskService;
import kikaha.core.test.KikahaRunner;
import kikaha.urouting.api.DefaultResponse;
import kikaha.urouting.api.Response;

@RunWith(KikahaRunner.class)
public class TaskControllerTest {

    @Mock
    private ICreateTaskService createTaskService;
    @Mock
    private IDeleteTaskService deleteTaskService;
    @Mock
    private IRetrieveAllTasksService retrieveAllTasksService;
    @Mock
    private IRetrieveTaskByIdService retrieveTaskByIdService;
    @Mock
    private IUpdateTaskService updateTaskService;

    @InjectMocks
    private TaskController controller;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void sholdReturnATasksList() {
        Task task1 = new Task(
                UUID.fromString("0402220c-6ba9-4e49-b7fb-42c443912db1"),
                "Estudar sobre KIKAHA",
                "Devo estudar a documentacao",
                0,
                TaskStatus.PROGRESS,
                LocalDate.of(2024, 4, 30));

        Task task2 = new Task(
                UUID.fromString("720981f8-19f9-4f13-815c-21e4294bed30"),
                "Estudar sobre Mockito",
                "Devo assistir 3 aulas",
                0,
                TaskStatus.PROGRESS,
                LocalDate.of(2024, 4, 30));
        Task task3 = new Task(UUID.fromString("85d519e9-0c94-4524-81b3-1b4795347c50"),
                "Completar o projeto Sizebay",
                "Devo realizar os teste unitarios",
                0,
                TaskStatus.PROGRESS,
                LocalDate.of(2024, 4, 30));

        List<Task> tasksExpect = List.of(task1, task2, task3);

        doReturn(tasksExpect).when(retrieveAllTasksService).execute();

        Assert.assertEquals(tasksExpect, retrieveAllTasksService.execute());
    }

    @Test
    public void sholdReturnATaskById() {
        UUID id = UUID.fromString("0402220c-6ba9-4e49-b7fb-42c443912db1");
        Task task = new Task(
                id,
                "Estudar sobre KIKAHA",
                "Devo estudar a documentacao",
                0,
                TaskStatus.PROGRESS,
                LocalDate.of(2024, 4, 30));

        doReturn(task).when(retrieveTaskByIdService).execute(id);
        Assert.assertEquals(task, controller.index(id.toString()).entity());

    }

    @Test
    public void sholdReturn400StatusIfIdNotExists() {
        UUID id = UUID.fromString("7a8853b1-7031-489a-b305-76e15b352154");

        when(retrieveTaskByIdService.execute(id)).thenThrow(new TaskNotFound());
        Response response = controller.index(id.toString());
        assertEquals(400, response.statusCode());
        assertEquals(new TaskNotFound().getMessage(), response.entity());

    }
}

package backend.challenge.modules.task.services;

import backend.challenge.modules.task.enums.TaskStatus;
import backend.challenge.modules.task.models.Task;
import backend.challenge.modules.task.repositories.ITaskRepository;
import kikaha.core.test.KikahaRunner;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

@RunWith(KikahaRunner.class)
public class RetrieveAllTasksServiceTest {

	@InjectMocks
	private RetrieveAllTasksService retrieveAllTasksService;

	@Mock
	private ITaskRepository taskRepository;

	@Before
	public void init() {
		MockitoAnnotations.openMocks(this);

	}

	@Test
	public void shouldBeAbleToListTheTasks() {

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

		Mockito.when(taskRepository.show()).thenReturn(tasksExpect);
		Assert.assertEquals(retrieveAllTasksService.execute(), tasksExpect);
		Mockito.verify(taskRepository, Mockito.times(1)).show();

	}

}
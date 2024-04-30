package backend.challenge.modules.task.services;

import backend.challenge.modules.task.dtos.TaskDTO;
import backend.challenge.modules.task.enums.TaskStatus;
import backend.challenge.modules.task.models.Task;
import backend.challenge.modules.task.repositories.ITaskRepository;
import kikaha.core.test.KikahaRunner;
import static org.junit.Assert.assertEquals;
import java.time.LocalDate;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

@RunWith(KikahaRunner.class)
public class CreateTaskServiceTest {

	@InjectMocks
	private CreateTaskService createTaskService;

	@Mock
	private ITaskRepository taskRepository;

	@Before
	public void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void shouldBeAbleToCreateANewTask() {
		TaskDTO taskDTO = TaskDTO.create().setTitle("Algum titulo").setDescription("Alguma descricao");
		
		Task expectedTask = new Task(
				UUID.fromString("e36be03f-5b17-4720-845e-68412f8248c6"),
				"Algum titulo",
				"Alguma descricao",
				0,
				TaskStatus.PROGRESS,
				LocalDate.of(2024, 4, 30));

		Mockito.when(taskRepository.create(taskDTO)).thenReturn(expectedTask);
		Task result = createTaskService.execute(taskDTO);
		assertEquals(expectedTask, result);
		Mockito.verify(taskRepository, Mockito.times(1)).create(taskDTO);

	}

}
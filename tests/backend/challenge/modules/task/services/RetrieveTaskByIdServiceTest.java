package backend.challenge.modules.task.services;

import backend.challenge.modules.task.enums.TaskStatus;
import backend.challenge.modules.task.exceptions.TaskNotFound;
import backend.challenge.modules.task.models.Task;
import backend.challenge.modules.task.repositories.ITaskRepository;
import kikaha.core.test.KikahaRunner;
import static org.mockito.Mockito.times;
import java.time.LocalDate;
import java.util.Optional;
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
public class RetrieveTaskByIdServiceTest {

	@InjectMocks
	private RetrieveTaskByIdService retrieveTaskByIdService;

	@Mock
	private ITaskRepository taskRepository;

	@Before
	public void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void shouldBeAbleToListTheTaskById() {
		UUID id = UUID.fromString("e5102dbd-c9f1-47ce-8bc0-a761dc620e13");

		Task taskExpect = new Task(
				id,
				"Algum titulo",
				"Alguma descricao",
				0,
				TaskStatus.PROGRESS,
				LocalDate.of(2024, 4, 30));

		Mockito.when(taskRepository.index(id)).thenReturn(Optional.of(taskExpect));

		Assert.assertEquals(taskExpect, retrieveTaskByIdService.execute(id));

	}
	@Test
	public void shouldThrowExceptionWhenTaskNotFoundById(){
		
		UUID id = UUID.fromString("e5102dbd-c9f1-47ce-8bc0-a761dc620e13");
		Mockito.when(taskRepository.index(id)).thenReturn(Optional.empty());

		Assert.assertThrows(TaskNotFound.class,() -> retrieveTaskByIdService.execute(id));
		Mockito.verify(taskRepository, times(1)).index(id);
	}

}

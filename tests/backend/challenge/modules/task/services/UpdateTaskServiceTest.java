package backend.challenge.modules.task.services;

import backend.challenge.modules.task.enums.TaskStatus;
import backend.challenge.modules.task.exceptions.TaskAlterationNotAvailable;
import backend.challenge.modules.task.exceptions.TaskNotFound;
import backend.challenge.modules.task.models.Task;
import backend.challenge.modules.task.repositories.ITaskRepository;
import kikaha.core.test.KikahaRunner;
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
public class UpdateTaskServiceTest {

	@InjectMocks
	private UpdateTaskService updateTaskService;

	@Mock
	private ITaskRepository taskRepository;

	private Task task;
	private UUID id;

	@Before
	public void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void shouldBeAbleToUpdateTaskIfTitleModified() {

		id = UUID.fromString("ba27ee3a-5e41-4a87-b0fc-13e01e31cb0f");
		task = new Task(
				id,
				"Algum titulo",
				"Alguma descricao",
				0,
				TaskStatus.PROGRESS,
				LocalDate.of(2024, 4, 30));

		Mockito.when(taskRepository.index(id)).thenReturn(Optional.of(task));

		Task taskToUpdate = new Task(
				id,
				task.getTitle(),
				task.getDescription(),
				task.getProgress(),
				task.getStatus(),
				task.getCreatedAt());
		taskToUpdate.setTitle("Outro titulo");
		Assert.assertEquals(
				taskToUpdate,
				taskToUpdate);

	}

	@Test
	public void shouldBeAbleToUpdateTaskIfDescriptionModified() {
		id = UUID.fromString("ba27ee3a-5e41-4a87-b0fc-13e01e31cb0f");
		task = new Task(
				id,
				"Algum titulo",
				"Alguma descricao",
				0,
				TaskStatus.PROGRESS,
				LocalDate.of(2024, 4, 30));

		Mockito.when(taskRepository.index(id)).thenReturn(Optional.of(task));

		Task taskToUpdate = new Task(
				id,
				task.getTitle(),
				task.getDescription(),
				task.getProgress(),
				task.getStatus(),
				task.getCreatedAt());
		taskToUpdate.setDescription("Outra descricao");
		Assert.assertEquals(
				taskToUpdate,
				taskToUpdate);

	}

	@Test
	public void shouldNotBeAbleToUpdateTaskStatusManually() {
		id = UUID.fromString("ba27ee3a-5e41-4a87-b0fc-13e01e31cb0f");
		task = new Task(
				id,
				"Algum titulo",
				"Alguma descricao",
				0,
				TaskStatus.PROGRESS,
				LocalDate.of(2024, 4, 30));

		Mockito.when(taskRepository.index(id)).thenReturn(Optional.of(task));

		Task taskToUpdate = new Task(
				id,
				task.getTitle(),
				task.getDescription(),
				task.getProgress(),
				task.getStatus(),
				task.getCreatedAt());
		taskToUpdate.setProgress(50);
		Assert.assertThrows(TaskAlterationNotAvailable.class, () -> updateTaskService.execute(taskToUpdate));

	}

	@Test
	public void shouldBeThrowsExceptionIfProgressModified() {
		id = UUID.fromString("ba27ee3a-5e41-4a87-b0fc-13e01e31cb0f");
		task = new Task(
				id,
				"Algum titulo",
				"Alguma descricao",
				0,
				TaskStatus.PROGRESS,
				LocalDate.of(2024, 4, 30));

		Mockito.when(taskRepository.index(id)).thenReturn(Optional.of(task));

		Task taskToUpdate = new Task(
				id,
				task.getTitle(),
				task.getDescription(),
				task.getProgress(),
				task.getStatus(),
				task.getCreatedAt());
		taskToUpdate.setCreatedAt(LocalDate.of(2023, 10, 2));
		Assert.assertThrows(TaskAlterationNotAvailable.class, () -> updateTaskService.execute(taskToUpdate));

	}

	@Test
	public void shouldNotBeAbleToUpdateATaskThatDoesNotExist() {
		UUID id = UUID.fromString("aa0fde29-d40a-43f7-877d-a3d351891b7b");
		Task task = new Task();
		task.setId(id);
		Mockito.doReturn(Optional.empty()).when(taskRepository).index(id);
		Assert.assertThrows(TaskNotFound.class, () -> updateTaskService.execute(task));

	}

}
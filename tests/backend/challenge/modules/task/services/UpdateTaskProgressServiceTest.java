package backend.challenge.modules.task.services;

import kikaha.core.test.KikahaRunner;
import static org.mockito.Mockito.doReturn;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import backend.challenge.modules.task.dtos.TaskProgressDTO;
import backend.challenge.modules.task.enums.TaskStatus;
import backend.challenge.modules.task.exceptions.ProgressTaskUpdateNotAvailable;
import backend.challenge.modules.task.models.Task;
import backend.challenge.modules.task.repositories.ITaskRepository;

@RunWith(KikahaRunner.class)
public class UpdateTaskProgressServiceTest {

	@InjectMocks
	private UpdateTaskProgressService progressService;

	@Mock
	private ITaskRepository taskRepository;

	private Task task;
	private UUID id;

	@Before
	public void init() {
		MockitoAnnotations.openMocks(this);

		id = UUID.fromString("34fb74a0-682c-43c0-b228-1eca6da09150");
		task = new Task(
				id,
				"Algum titulo",
				"Alguma descricao",
				0,
				TaskStatus.PROGRESS,
				LocalDate.of(2024, 4, 30));
	}

	@Test
	public void shouldBeAbleToUpdateTaskProgress() {

		int newProgress = 50;
		TaskProgressDTO dto = TaskProgressDTO.create().setId(id).setProgress(newProgress);

		Task taskUpdated = new Task(
				id,
				task.getTitle(),
				task.getDescription(),
				newProgress,
				task.getStatus(),
				task.getCreatedAt()

		);

		Mockito.when(taskRepository.index(id)).thenReturn(Optional.of(task));
		Assert.assertEquals(taskUpdated, progressService.execute(dto));

	}

	@Test
	public void shouldBeAbleToUpdateOnlyTaskStatusWhenProgressEqualsOneHundred() {

		int newProgress = 110;
		TaskProgressDTO progressDTO = TaskProgressDTO.create().setId(id).setProgress(newProgress);

		Assert.assertThrows(ProgressTaskUpdateNotAvailable.class,
				() -> progressService.execute(progressDTO));

		progressDTO.setProgress(-10);
		Assert.assertThrows(ProgressTaskUpdateNotAvailable.class,
				() -> progressService.execute(progressDTO));

	}

	@Test
	public void shouldChangeStatusIfCompleteProgress() {
		int newProgress = 100;
		TaskProgressDTO taskProgressDTO = TaskProgressDTO.create().setId(id).setProgress(newProgress);
		Task taskUpdated = new Task(
				task.getId(),
				task.getTitle(),
				task.getDescription(),
				newProgress,
				task.getStatus(),
				task.getCreatedAt());
		ArgumentCaptor<Task> argumentCaptor = ArgumentCaptor.forClass(Task.class);

		Mockito.when(taskRepository.index(id)).thenReturn(Optional.of(task));
		doReturn(taskUpdated).when(taskRepository).update(task);

		this.progressService.execute(taskProgressDTO);

		Mockito.verify(taskRepository, Mockito.times(1)).index(id);
		Mockito.verify(taskRepository).update(argumentCaptor.capture());

		Task taskCaptured = argumentCaptor.getValue();

		Assert.assertEquals( TaskStatus.COMPLETE, taskCaptured.getStatus());
	}

	@Test
	public void shouldChangeStatusIfNotCompleteProgress() {
		int newProgress = 50;
		int oldProgress = 100;
		Task task = new Task(
			id,
			"Algum titulo",
			"Alguma descricao",
			oldProgress,
			TaskStatus.COMPLETE,
			LocalDate.of(2024, 1, 1));
		
		Task taskUpdated = new Task(
			task.getId(),
			task.getTitle(),
			task.getDescription(),
			newProgress,
			TaskStatus.COMPLETE,
			task.getCreatedAt());
			
		TaskProgressDTO taskProgressDTO = TaskProgressDTO.create().setId(id).setProgress(newProgress);
		Mockito.when(taskRepository.index(id)).thenReturn(Optional.of(task));
		doReturn(taskUpdated).when(taskRepository).update(task);

		this.progressService.execute(taskProgressDTO);

		Mockito.verify(taskRepository, Mockito.times(1)).index(id);
		
		ArgumentCaptor<Task> argumentCaptor = ArgumentCaptor.forClass(Task.class);
		Mockito.verify(taskRepository).update(argumentCaptor.capture());

		Task taskCaptured = argumentCaptor.getValue();

		Assert.assertEquals(TaskStatus.PROGRESS, taskCaptured.getStatus());
	}

}

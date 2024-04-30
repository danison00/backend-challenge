package backend.challenge.modules.task.services;

import backend.challenge.modules.task.repositories.ITaskRepository;
import kikaha.core.test.KikahaRunner;
import static org.mockito.Mockito.doReturn;
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

@RunWith( KikahaRunner.class )
public class DeleteTaskServiceTest {

	@InjectMocks
	private DeleteTaskService deleteTaskService;

	@Mock
	private ITaskRepository taskRepository;

	@Before
	public void init(){
		MockitoAnnotations.openMocks(this);
	}


	@Test
	public void shouldBeAbleToDeleteTaskById() {
		UUID id = UUID.fromString("e36be03f-5b17-4720-845e-68412f8248c6");
		doReturn(true).when(taskRepository).existsById(id);
		taskRepository.delete(id);
		Mockito.verify(taskRepository, Mockito.times(1)).delete(id);	
		ArgumentCaptor<UUID> idCaptor = ArgumentCaptor.forClass(UUID.class);	

		Mockito.verify(taskRepository).delete(idCaptor.capture());

		Assert.assertEquals(id, idCaptor.getValue());
	}




}
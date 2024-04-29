package backend.challenge.modules.task.repositories;

import backend.challenge.modules.task.dtos.TaskDTO;
import backend.challenge.modules.task.enums.TaskStatus;
import backend.challenge.modules.task.models.Task;
import lombok.NoArgsConstructor;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Singleton
public class TaskRepository implements ITaskRepository {

	@Inject
	private DataSource db;

	@Override
	public Optional<Task> index(final UUID taskId) {
		// TODO: Criar método responsável por retornar tarefa por id
		
		return null;
	}

	@Override
	public List<Task> show() {
		// TODO: Criar método responsável por retornar todas as tarefas

		return null;
	}

	@Override
	public Task create(final TaskDTO taskDTO) {
		// TODO: Criar método responsável por criar uma tarefa
		try (Connection connection = this.db.getConnection()) {
			String sql = "INSERT INTO tasks(id, title, description, createdAt) VALUES(?, ?, ?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			Task newTask = new Task(
					UUID.randomUUID(),
					taskDTO.getTitle(),
					taskDTO.getDescription(),
					0,
					TaskStatus.PROGRESS,
					LocalDateTime.now()

			);

			preparedStatement.setString(1, newTask.getId().toString());
			preparedStatement.setString(2, newTask.getTitle());
			preparedStatement.setString(3, newTask.getDescription());
			preparedStatement.setTimestamp(4, Timestamp.valueOf(newTask.getCreatedAt()));
			preparedStatement.executeUpdate();

			return newTask;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public Task update(final Task task) {
		// TODO: Criar método responsável por atualizar uma tarefa

		return null;
	}

	@Override
	public void delete(final Long taskId) {
		// TODO: Criar método responsável por deletar tarefa por id

	}

}

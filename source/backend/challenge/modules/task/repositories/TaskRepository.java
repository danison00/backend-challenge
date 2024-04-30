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
import java.util.ArrayList;
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
		try (Connection connection = this.db.getConnection()) {
			String sql = "SELECT id, title, description, status, progress, createdAt FROM tasks WHERE id = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, taskId.toString());

			ResultSet resultSet = preparedStatement.executeQuery();
			Task task = null;
			if (resultSet.next()) {
				task = new Task();
				task.setId(UUID.fromString(resultSet.getString("id")));
				task.setTitle(resultSet.getString("title"));
				task.setDescription(resultSet.getString("description"));
				task.setStatus(TaskStatus.valueOf(resultSet.getString("status")));
				task.setProgress(resultSet.getInt("progress"));
				task.setCreatedAt(resultSet.getDate("createdAt").toLocalDate());
				return Optional.of(task);
			}
			return Optional.empty();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Task> show() {
		try (Connection connection = this.db.getConnection()) {
			String sql = "SELECT id, title, description, status, progress, createdAt FROM tasks";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			List<Task> tasks = new ArrayList<>();
			while (resultSet.next()) {
				Task task = new Task();
				task.setId(UUID.fromString(resultSet.getString("id")));
				task.setTitle(resultSet.getString("title"));
				task.setDescription(resultSet.getString("description"));
				task.setStatus(TaskStatus.valueOf(resultSet.getString("status")));
				task.setProgress(resultSet.getInt("progress"));
				task.setCreatedAt( resultSet.getDate("createdAt").toLocalDate());
				tasks.add(task);
			}
			return tasks;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Task create(final TaskDTO taskDTO) {
		try (Connection connection = this.db.getConnection()) {
			String sql = "INSERT INTO tasks(id, title, description, status, progress, createdAt) VALUES(?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			Task newTask = new Task(
					UUID.randomUUID(),
					taskDTO.getTitle(),
					taskDTO.getDescription(),
					0,
					TaskStatus.PROGRESS,
					LocalDate.now()

			);

			preparedStatement.setString(1, newTask.getId().toString());
			preparedStatement.setString(2, newTask.getTitle());
			preparedStatement.setString(3, newTask.getDescription());
			preparedStatement.setString(4, newTask.getStatus().name());
			preparedStatement.setInt(5, newTask.getProgress());
			preparedStatement.setDate(6, java.sql.Date.valueOf(newTask.getCreatedAt()));
			preparedStatement.executeUpdate();

			return newTask;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public Task update(final Task task) {
	System.out.println(task.toString());
		try (Connection connection = this.db.getConnection()) {
			String sql = "UPDATE tasks SET title = ?, description = ?, status = ?, progress = ? WHERE id = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, task.getTitle());
			preparedStatement.setString(2, task.getDescription());
			preparedStatement.setString(3, task.getStatus().name());
			preparedStatement.setInt(4, task.getProgress());
			preparedStatement.setString(5, task.getId().toString());
			preparedStatement.executeUpdate();

			return task;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void delete(final UUID taskId) {

		try (Connection connection = this.db.getConnection()) {
			String sql = "DELETE FROM tasks WHERE id=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, taskId.toString());
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public boolean existsById(UUID id) {
		try (Connection connection = this.db.getConnection()) {
			String sql = "SELECT COUNT(*) FROM tasks WHERE id = ?";

			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, id.toString());
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				int count = resultSet.getInt(1);
				resultSet.close();
				System.out.println(count > 0);
				return count > 0;
			}
			return false;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

}

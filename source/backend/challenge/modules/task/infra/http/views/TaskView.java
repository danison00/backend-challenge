package backend.challenge.modules.task.infra.http.views;

import backend.challenge.modules.task.enums.TaskStatus;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TaskView {

	private String title;
	private String description;

}

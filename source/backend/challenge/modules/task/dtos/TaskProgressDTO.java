package backend.challenge.modules.task.dtos;

import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor(staticName = "create")
public class TaskProgressDTO {

	private UUID id;
	private int progress;

}

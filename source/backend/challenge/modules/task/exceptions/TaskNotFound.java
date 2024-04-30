package backend.challenge.modules.task.exceptions;

public class TaskNotFound extends RuntimeException {

    public TaskNotFound() {
        super("Tarefa não encontrda.");
    }
}

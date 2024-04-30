package backend.challenge.modules.task.exceptions;

public class TaskAlterationNotAvailable  extends RuntimeException{
    public TaskAlterationNotAvailable() {
        super("É apenas possível alterar o Título e Descrição nesta operação.");
    }
}

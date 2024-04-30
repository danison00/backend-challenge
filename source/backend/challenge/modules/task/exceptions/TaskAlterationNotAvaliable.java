package backend.challenge.modules.task.exceptions;

public class TaskAlterationNotAvaliable  extends RuntimeException{
    public TaskAlterationNotAvaliable() {
        super("É apenas possível alterar o Título e Descrição nesta operação.");
    }
}

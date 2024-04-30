package backend.challenge.modules.task.exceptions;

public class ProgressTaskUpdateNotAvailable extends RuntimeException{
    public ProgressTaskUpdateNotAvailable(){
        super("O progresso deve estar entre 0 e 100.");
    }
}

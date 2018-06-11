package engine;

/**
 * Exception criada para quando não é encontrado o ID do User
 */
public class UserNaoEncontradoException extends Exception {
    public UserNaoEncontradoException(String e) {
        super(e);
    }
}

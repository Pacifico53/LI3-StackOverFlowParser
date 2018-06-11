package engine;

/**
 * Exceção criada para quando um post não é encontrado
 */
public class PostNaoEncontradoException extends Exception {
    public PostNaoEncontradoException(String e) {
        super(e);
    }
}

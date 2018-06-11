package engine;

/**
 * Exception criada para quando uma tag não é encontrada
 */
public class TagNaoEncontradaException extends Exception {
    public TagNaoEncontradaException(String e){
        super(e);
    }
}

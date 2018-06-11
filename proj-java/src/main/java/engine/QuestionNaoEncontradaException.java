package engine;

/**
 * Exception criada para quando uma pergunta não é encontrada
 */
public class QuestionNaoEncontradaException extends Exception{
    public QuestionNaoEncontradaException(String e){
        super(e);
    }
}

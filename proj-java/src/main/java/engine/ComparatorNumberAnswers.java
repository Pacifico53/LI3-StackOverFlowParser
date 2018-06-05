package engine;

import common.Question;

import java.util.Comparator;

/**
 * Classe comparator para comparar o numero de respostas de duas perguntas
 */
public class ComparatorNumberAnswers implements Comparator<Question>
{
    public int compare (Question q1 , Question q2){
        return Integer.compare(q2.getNumberAnswers(), q1.getNumberAnswers());
    }

}

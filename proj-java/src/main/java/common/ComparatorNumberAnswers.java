package common;

import java.util.Comparator;

/**
 * Classe comparator para comparar o numero de respostas de duas perguntas
 */
public class ComparatorNumberAnswers implements Comparator<Question>
{
    public int compare (Question q1 , Question q2){
        if (q1.getNumberAnswers() > q2.getNumberAnswers()) return -1;
        else if (q1.getNumberAnswers() < q2.getNumberAnswers()) return 1;
        else return 0;
    }

}

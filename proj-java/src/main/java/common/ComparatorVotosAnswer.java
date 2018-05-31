package common;

import java.util.Comparator;

/**
 * Classe comparator para numero de posts de dois User
 */
public class ComparatorVotosAnswer implements Comparator<Answer>
{
    /**
     * Compara dois valores dando duas Answers como argumento
     */
    public int compare(Answer a1, Answer a2) {
        if (a1.getScore_a() > a2.getScore_a()) return -1;
        else if (a1.getScore_a() < a2.getScore_a()) return 1;
        else return 0;
    }
}

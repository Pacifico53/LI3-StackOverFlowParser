package engine;

import common.Answer;

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
        return Integer.compare(a2.getScore_a(), a1.getScore_a());
    }
}

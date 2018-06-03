package common;

import java.util.Comparator;
import java.util.HashMap;

/**
 * Classe comparator para melhor Answer
 */
public class ComparatorBestAnswer implements Comparator<Answer>
{
    private HashMap<Long, User> hashUsers;

    public ComparatorBestAnswer(HashMap<Long, User> hashUsers){
            this.hashUsers = hashUsers;
    }

    /**
     * Compara duas answers usando a formula de melhor answer
     */
    public int compare(Answer a1, Answer a2) {
        int repUser1 = hashUsers.get(a1.getUser_id_a()).getReputation();
        int repUser2 = hashUsers.get(a2.getUser_id_a()).getReputation();

        double score1 = (a1.getScore_a() * 0.65) + (repUser1 * 0.25) + (a1.getComment_count_a() * 0.1);
        double score2 = (a2.getScore_a() * 0.65) + (repUser2 * 0.25) + (a2.getComment_count_a() * 0.1);

        if (score1 > score2) return -1;
        else if (score1 < score2) return 1;
        else return 0;
    }
}

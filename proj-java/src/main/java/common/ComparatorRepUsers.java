package common;

import java.util.Comparator;

/**
 * Classe comparator para comparar a reputation de dois Users
 */
public class ComparatorRepUsers implements Comparator<User>
{
    public int compare (User u1 , User u2){
        if (u1.getReputation() > u2.getReputation()) return -1;
        else if (u1.getReputation() < u2.getReputation()) return 1;
        else return 0;
    }

}

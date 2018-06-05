package engine;

import common.User;

import java.util.Comparator;

/**
 * Classe comparator para comparar a reputation de dois Users
 */
public class ComparatorRepUsers implements Comparator<User>
{
    public int compare (User u1 , User u2){
        return Integer.compare(u2.getReputation(), u1.getReputation());
    }

}

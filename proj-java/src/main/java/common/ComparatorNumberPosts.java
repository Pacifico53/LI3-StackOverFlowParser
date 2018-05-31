package common;

import java.util.Comparator;

/**
 * Classe comparator para numero de posts de dois User
 */
public class ComparatorNumberPosts implements Comparator<User>
{
    /**
     * Compara dois valores dando dois Users como argumento
     */
    public int compare(User u1, User u2) {
        if (u1.getNumberofPosts() > u2.getNumberofPosts()) return -1;
        else if (u1.getNumberofPosts() < u2.getNumberofPosts()) return 1;
        else return 0;
    }
}

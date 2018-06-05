package engine;

import common.User;

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
        return Integer.compare(u2.getNumberofPosts(), u1.getNumberofPosts());
    }
}

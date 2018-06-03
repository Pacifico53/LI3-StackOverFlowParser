package common;

import java.util.Comparator;

/**
 * Classe comparator para comparar o numero de count de duas tags
 */
public class ComparatorTagCount implements Comparator<Tag>
{
    public int compare (Tag t1 , Tag t2){
        if (t1.getCount() > t2.getCount()) return -1;
        else if (t1.getCount() < t2.getCount()) return 1;
        else return 0;
    }

}

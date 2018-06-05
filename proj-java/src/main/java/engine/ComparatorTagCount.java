package engine;

import common.Tag;

import java.util.Comparator;

/**
 * Classe comparator para comparar o numero de count de duas tags
 */
public class ComparatorTagCount implements Comparator<Tag>
{
    public int compare (Tag t1 , Tag t2){
        return Integer.compare(t2.getCount(), t1.getCount());
    }

}

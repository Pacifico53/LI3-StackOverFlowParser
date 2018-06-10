package common;

import java.util.Objects;

/**
 * Classe Tag que representa uma Tag do StackOverflow
 */
public class Tag {
    private String tagname;
    private int count;
    private long tag_id;

    /**
     * Construtor parametrizado de uma Tag
     * @param tagname Nome da tag
     * @param count Numero de vezes que a tag é usada
     * @param tag_id Id da tag
     */
    public Tag(String tagname, int count, long tag_id) {
        this.tagname = tagname;
        this.count = count;
        this.tag_id = tag_id;

    }

    /**
     * Construtor vazio de uma tag
     */
    public Tag(){ this("", 0, 0);}

    /**
     * Construtor de copia de uma Tag
     * @param t A tag que se quer fazer uma cópia
     */
    public Tag (Tag t){
        this.tagname = t.getTagname();
        this.count = t.getCount();
        this.tag_id = t.getTag_id();
    }

    //GETTERS E SETTERS
    public String getTagname() {
        return tagname;
    }

    public void setTagname(String tagname) {
        this.tagname = tagname;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getTag_id() {
        return tag_id;
    }

    public void setTag_id(long tag_id) {
        this.tag_id = tag_id;
    }

    /**
     * Método criado para a Query 11, que incrementa o número de vezes que a tag é usada
     */
    public void incrementCount(){
        this.count++;
    }

    /**
     * Método para comparar duas tags
     * @param o Um objeto (preferencialmente uma Tag)
     * @return Retorna true se o objeto recebido for igual à Tag
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tags = (Tag) o;
        return  count == tags.count &&
                tag_id == tags.tag_id &&
                Objects.equals(tagname, tags.tagname);
    }

    /**
     * Método para converter uma Tag numa String
     * @return As informações da Tag em String
     */
    public String toString() {
        return "ID: " + this.tag_id + "\n" +
                "Name: " + this.tagname + "\n" +
                "Count: " + this.count + "\n";
    }

    /**
     * Método para clonar uma Tag
     * @return Uma tag com os mesmos dados
     */
    public Tag clone (){
        return new Tag(this);
    }
}

package common;

import java.util.ArrayList;

/**
 * Classe Day representa um dia de um calendário, e onde se guarda os ID's de posts
 */

public class Day {
    private ArrayList<Long> ids; //Lista dos ids de posts publicados no respetivo dia

    /**
     * Construtor parameterizado
     * @param ids ArrayList de vários ids de posts
     */

    public Day(ArrayList<Long> ids) {
        this.ids = ids;
    }

    /**
     * Construtor de copia
     * @param d O Day que se quer copiar
     */

    public Day(Day d) {
        this.ids = d.getIds();
    }

    /**
     * Construtor vazio de um Day
     */

    public Day(){
        this.ids = new ArrayList<>();
    }

    //GETTERS E SETTERS
    public ArrayList<Long> getIds() {
        return ids;
    }

    public void setIds(ArrayList<Long> ids) {
        this.ids = ids;
    }

    /**
     * Metodo para clonar um Day
     * @return  Um Day com os mesmos dados
     */

    public Day clone(){
        return new Day(this);
    }
}

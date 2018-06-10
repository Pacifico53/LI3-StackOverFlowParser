package common;

import java.util.ArrayList;

/**
 * Classe Year representa um ano de um calendário
 */

public class Year {
    private ArrayList<MMonth> months; //O ano tem um conjunto de meses (Classe MMonth)

    /**
     * Construtor parameterizado
     * @param months ArrayList de vários MMonth
     */

    public Year(ArrayList<MMonth> months) {
        this.months = months;
    }

    /**
     * Construtor de copia
     * @param y O Year que se quer copiar
     */

    public Year(Year y){
        this.months = y.getMonths();
    }

    /**
     * Construtor vazio de um Year
     */

    public Year(){
        this.months = new ArrayList<>(12);
    }

    //GETTERS E SETTERS
    public ArrayList<MMonth> getMonths() {
        return months;
    }

    public void setMonths(ArrayList<MMonth> months) {
        this.months = months;
    }

    /**
     * Metodo para clonar um Year
     * @return  Um Year com os mesmos dados
     */

    public Year clone(){
        return new Year(this);
    }
}

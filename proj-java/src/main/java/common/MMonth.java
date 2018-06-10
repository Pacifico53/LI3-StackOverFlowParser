package common;

import java.util.ArrayList;

/**
 * Classe MMonth representa um mês de um calendário
 */

public class MMonth {
    private ArrayList<Day> days; //O mês tem um conjunto de dias (Classe Day)

    /**
     * Construtor parameterizado
     * @param days ArrayList de vários Day
     */

    public MMonth(ArrayList<Day> days) {
        this.days = days;
    }

    /**
     * Construtor de copia
     * @param m O MMonth que se quer copiar
     */

    public MMonth(MMonth m){
        this.days = m.getDays();
    }

    /**
     * Construtor vazio de um MMonth
     */

    public MMonth(){
        this.days = new ArrayList<>(31);
    }

    //GETTERS E SETTERS
    public ArrayList<Day> getDays() {
        return days;
    }

    public void setDays(ArrayList<Day> days) {
        this.days = days;
    }

    /**
     * Metodo para clonar um MMonth
     * @return  Um MMonth com os mesmos dados
     */

    public MMonth clone(){
        return new MMonth(this);
    }
}

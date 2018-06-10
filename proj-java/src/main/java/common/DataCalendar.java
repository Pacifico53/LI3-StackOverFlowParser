package common;

import java.util.ArrayList;

/**
 * Classe DataCalendar representa o calendário, e onde se guarda os ID's de posts, no seu devido dia
 */
public class DataCalendar {

    private ArrayList<Year> years; //O calendario tem um conjunto de anos (Classe Year)

    /**
     * Construtor parameterizado
     * @param years ArrayList de varios Year
     */
    public DataCalendar(ArrayList<Year> years) {
        this.years = years;
    }

    /**
     * Construtor vazio, inicializa o ArrayList de Year com capacidade de 10
     */
    public DataCalendar(){
        this.years = new ArrayList<>(10);
    }

    /**
     * Construtor de copia
     * @param d O calendario que se quer copiar
     */
    public DataCalendar(DataCalendar d){
        this.years = d.getYears();
    }

    //GETTERS E SETTERS
    public ArrayList<Year> getYears() {
        return years;
    }

    public void setYears(ArrayList<Year> years) {
        this.years = years;
    }

    /**
     * Funçao que vai inicializar o calendario, ou seja, inserir 10 anos, 12 meses em cada ano, e 31 dias em cada mes.
     * Isto utilizando as classes Year, MMonth e Day criadas por nos
     */
    public void init(){
        int i, j, k;
        for(i = 0; i < 10; i++){
            Year months = new Year();
            for (j = 0; j < 12; j++){
                MMonth days = new MMonth();
                for(k = 0; k < 31; k++) {
                    Day ids = new Day();
                    days.getDays().add(k, ids);
                }
                months.getMonths().add(j, days);
            }
            this.years.add(i, months);
        }
    }

    /**
     * Metodo para guardar um ID de um post no calendario, numa certa data
     * @param year  Ano onde se vai inserir o ID
     * @param month Mes onde se vai inserir o ID
     * @param day   Dia onde se vai inserir o ID
     * @param id    o ID que se quer guardar
     */
    public void addID(int year, int month, int day, long id){
        Year y = this.years.get(year);
        MMonth m = y.getMonths().get(month);
        Day d = m.getDays().get(day);
        d.getIds().add(id);
    }

    /**
     * Metodo para clonar um DataCalendar
     * @return um calendario com os mesmos dados
     */
    public DataCalendar clone(){
        return new DataCalendar(this);
    }
}

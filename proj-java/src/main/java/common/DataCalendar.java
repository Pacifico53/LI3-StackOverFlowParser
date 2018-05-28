package common;

import java.util.ArrayList;

public class DataCalendar {
    private ArrayList<Year> years;

    public DataCalendar(ArrayList<Year> years) {
        this.years = years;
    }

    public DataCalendar(DataCalendar d){
        this.years = d.getYears();
    }

    public DataCalendar(){
        this.years = new ArrayList<>(10);
    }

    public ArrayList<Year> getYears() {
        return years;
    }

    public void setYears(ArrayList<Year> years) {
        this.years = years;
    }


    public void init(){
<<<<<<< HEAD
        for(Year y: this.years){
            ArrayList<Month> months = new ArrayList<>(12);
            for (Month m: months){
                ArrayList<Day> days = new ArrayList<>(31);
                for(Day d: days) {
                    ArrayList<Long> ids = new ArrayList<>();
                    d.getIds().add(ids);
=======
        int i = 0, j = 0, k = 0;
        for(i = 0; i < 10; i++){
            Year months = new Year();
            for (j = 0; j < 12; j++){
                MMonth days = new MMonth();
                for(k = 0; k < 31; k++) {
                    Day ids = new Day();
                    days.getDays().add(k, ids);
>>>>>>> cdedbc33ac824298e645c64403d507d97a8ce5c2
                }
                months.getMonths().add(j, days);
            }
            this.years.add(i, months);
        }
    }

    public void addID(int year, int month, int day, long id){
        Year y = this.years.get(year);
        MMonth m = y.getMonths().get(month);
        Day d = m.getDays().get(day);
        d.getIds().add(id);
    }
}

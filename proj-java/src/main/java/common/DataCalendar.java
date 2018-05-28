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
        this.years = new ArrayList<Year>(9);
    }

    public ArrayList<Year> getYears() {
        return years;
    }

    public void setYears(ArrayList<Year> years) {
        this.years = years;
    }


    public void init(){
        for(Year y: this.years){
            ArrayList<Month> months = new ArrayList<>(12);
            for (Month m: months){
                ArrayList<Day> days = new ArrayList<>(31);
                for(Day d: days) {
                    ArrayList<Long> ids = new ArrayList<>();
                    d.getIds().add(ids);
                }
                m.getDays().add(days);
            }
            y.getMonths().add(months);
        }
    }
}

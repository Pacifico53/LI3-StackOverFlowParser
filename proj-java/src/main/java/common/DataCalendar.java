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

    public void addID(int year, int month, int day, long id){
        Year y = this.years.get(year);
        MMonth m = y.getMonths().get(month);
        Day d = m.getDays().get(day);
        d.getIds().add(id);
    }

    public DataCalendar clone(){
        return new DataCalendar(this);
    }
}

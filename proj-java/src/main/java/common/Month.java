package common;

import java.util.ArrayList;

public class Month {
    private ArrayList<Day> days;

    public Month(ArrayList<Day> days) {
        this.days = days;
    }

    public Month(Month m){
        this.days = m.getDays();
    }

    public Month(){
        this.days = new ArrayList<Day>(31);
    }

    public ArrayList<Day> getDays() {
        return days;
    }

    public void setDays(ArrayList<Day> days) {
        this.days = days;
    }
}

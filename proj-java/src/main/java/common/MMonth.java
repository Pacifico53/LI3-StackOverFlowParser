package common;

import java.util.ArrayList;

public class MMonth {
    private ArrayList<Day> days;

    public MMonth(ArrayList<Day> days) {
        this.days = days;
    }

    public MMonth(MMonth m){
        this.days = m.getDays();
    }

    public MMonth(){
        this.days = new ArrayList<Day>(31);
    }

    public ArrayList<Day> getDays() {
        return days;
    }

    public void setDays(ArrayList<Day> days) {
        this.days = days;
    }
}

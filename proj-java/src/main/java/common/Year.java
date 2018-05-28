package common;

import java.util.ArrayList;

public class Year {
    private ArrayList<Month> months;

    public Year(ArrayList<Month> months) {
        this.months = months;
    }

    public Year(Year y){
        this.months = y.getMonths();
    }

    public Year(){
        this.months = new ArrayList<Month>(12);
    }

    public ArrayList<Month> getMonths() {
        return months;
    }

    public void setMonths(ArrayList<Month> months) {
        this.months = months;
    }
}

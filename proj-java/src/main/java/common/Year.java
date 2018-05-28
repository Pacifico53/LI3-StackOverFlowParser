package common;

import java.util.ArrayList;

public class Year {
    private ArrayList<MMonth> months;

    public Year(ArrayList<MMonth> months) {
        this.months = months;
    }

    public Year(Year y){
        this.months = y.getMonths();
    }

    public Year(){
        this.months = new ArrayList<>(12);
    }

    public ArrayList<MMonth> getMonths() {
        return months;
    }

    public void setMonths(ArrayList<MMonth> months) {
        this.months = months;
    }
}

package common;

import java.util.ArrayList;

public class Day {
    private ArrayList<Long> ids;

    public Day(ArrayList<Long> ids) {
        this.ids = ids;
    }

    public Day(Day d) {
        this.ids = d.getIds();
    }

    public Day(){
        this.ids = new ArrayList<>();
    }

    public ArrayList<Long> getIds() {
        return ids;
    }

    public void setIds(ArrayList<Long> ids) {
        this.ids = ids;
    }

}

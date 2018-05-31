package common;

import java.util.Objects;

public class Tag {
    private String tagname;
    private int count;
    private long tag_id;

    public Tag(String tagname, int count, long tag_id) {
        this.tagname = tagname;
        this.count = count;
        this.tag_id = tag_id;

    }

    public Tag(){ this("", 0, 0);}

    public Tag (Tag t){
        this.tagname = t.getTagname();
        this.count = t.getCount();
        this.tag_id = t.getTag_id();
    }

    public String getTagname() {
        return tagname;
    }

    public void setTagname(String tagname) {
        this.tagname = tagname;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getTag_id() {
        return tag_id;
    }

    public void setTag_id(long tag_id) {
        this.tag_id = tag_id;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tags = (Tag) o;
        return  count == tags.count &&
                tag_id == tags.tag_id &&
                Objects.equals(tagname, tags.tagname);
    }

    public String toString() {
        String sb = "ID: " + this.tag_id + "\n" +
                "Name: " + this.tagname + "\n" +
                "Count: " + this.count + "\n";
        return sb;
    }

    public Tag clone (){
        return new Tag(this);
    }
}

package common;

public class User {
    private long id;
    private String name, aboutme;
    private int reputation, numberOfPosts;

    public User(long id, String name, String aboutme, int reputation, int numberOfPosts){
        this.id = id;
        this.name = name;
        this.aboutme = aboutme;
        this.reputation = reputation;
        this.numberOfPosts = numberOfPosts;
    }

    public User() {
        this(0, "", "", 0, 0);
    }

    public User(User u){
        this.id = u.getId();
        this.name = u.getName();
        this.aboutme = u.getAboutme();
        this.reputation = u.getReputation();
        this.numberOfPosts = u.getNumberofPosts();
    }

    public long getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getAboutme(){
        return aboutme;
    }

    public int getReputation(){
        return reputation;
    }

    public int getNumberofPosts(){
        return numberOfPosts;
    }

    public void setId(long id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setAboutme(String aboutme){
        this.aboutme = aboutme;
    }

    public void setReputation(int reputation){
        this.reputation = reputation;
    }

    public void setNumberOfPosts(int numberOfPosts){
        this.numberOfPosts = numberOfPosts;
    }


    public boolean equals(Object o){
        if(this == o)
            return true;
        if ((o==null) || (this.getClass() != o.getClass()))
            return false;
        User m = (User) o;
        return (this.id == getId() &&
                this.name.equals(m.getName()) &&
                this.aboutme.equals(m.getAboutme())&&
                this.reputation == getReputation() &&
                this.numberOfPosts == m.getNumberofPosts());
    }

    public String toString() {
        String sb = "ID: " + id + "\n" +
                "Name: " + name + "\n" +
                "AboutMe: " + aboutme + "\n" +
                "Reputation: " + reputation + "\n" +
                "NumberOfPosts: " + numberOfPosts + "\n";
        return sb;
    }

    public void incrementNumberOfPosts(){
        this.numberOfPosts ++;
    }

    public User clone (){
        return new User (this);
    }
}

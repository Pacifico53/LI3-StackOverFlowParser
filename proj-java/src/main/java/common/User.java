package common;

/**
 * Classe User que representa um User do Stack overflow
 */
public class User {

    private long id;                        //ID do User
    private String name, aboutme;           //Nome e "AboutMe" do User
    private int reputation, numberOfPosts;  //Reputation e numero de posts do user

    /**
     * Construtor parameterizado de um User
     * @param id    ID do User
     * @param name  Nome do User
     * @param aboutme   AboutMe (informaçao do User)
     * @param reputation    Reputation do User
     * @param numberOfPosts Numero de posts do User (isto é sempre inicializado a 0)
     */
    public User(long id, String name, String aboutme, int reputation, int numberOfPosts){
        this.id = id;
        this.name = name;
        this.aboutme = aboutme;
        this.reputation = reputation;
        this.numberOfPosts = numberOfPosts;
    }

    /**
     * Construtor vazio de um User
     */
    public User() {
        this(0, "", "", 0, 0);
    }

    /**
     * Construtor de copia de um User
     * @param u O User que se quer fazer uma copia
     */
    public User(User u){
        this.id = u.getId();
        this.name = u.getName();
        this.aboutme = u.getAboutme();
        this.reputation = u.getReputation();
        this.numberOfPosts = u.getNumberofPosts();
    }

    //GETTERS E SETTERS
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

    /**
     * Metodo para comparar dois Users
     * @param o Um objecto (preferivelmente um User)
     * @return  Retorna true apenas se "o" for igual ao User
     */
    public boolean equals(Object o){
        if(this == o)
            return true;
        if ((o==null) || (this.getClass() != o.getClass()))
            return false;
        User m = (User) o;
        return (this.id == m.getId() &&
                this.name.equals(m.getName()) &&
                this.aboutme.equals(m.getAboutme())&&
                this.reputation == m.getReputation() &&
                this.numberOfPosts == m.getNumberofPosts());
    }

    /**
     * Metodo para converter um User numa String
     * @return As informaçoes do User em formato String
     */
    public String toString() {
        return "ID: " + id + "\n" +
                "Name: " + name + "\n" +
                "AboutMe: " + aboutme + "\n" +
                "Reputation: " + reputation + "\n" +
                "NumberOfPosts: " + numberOfPosts + "\n";
    }

    /**
     * Método utilizado para incrementar o numero de posts de um User, utilizado na Query 2
     */
    public void incrementNumberOfPosts(){
        this.numberOfPosts++;
    }

    /**
     * Metodo para clonar um User
     * @return  Um User com os mesmos dados
     */
    public User clone (){
        return new User (this);
    }
}

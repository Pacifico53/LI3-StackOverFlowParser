package common;

/**
 * Classe Answer que representa uma resposta no Stack overflow
 */

public class Answer {
    private long id_a;              //ID da Answer
    private int score_a;            //Score da Answer
    private long user_id_a;         //ID do User que publicou essa Answer
    private int comment_count_a;    //Número de comentários dessa Answer
    private long parent_id;         //ID da Question a que essa Answer responde

    /**
     * Construtor parameterizado de um User
     * @param id_a  ID da Answer
     * @param score_a  Score da Answer
     * @param user_id_a  ID do User que publicou essa Answer
     * @param comment_count_a Número de comentários dessa Answer
     * @param parent_id ID da Question a que essa Answer responde
     */

    public Answer(long id_a, int score_a, long user_id_a, int comment_count_a, long parent_id) {
        this.id_a = id_a;
        this.score_a = score_a;
        this.user_id_a = user_id_a;
        this.comment_count_a = comment_count_a;
        this.parent_id = parent_id;
    }
    /**
     * Construtor de copia de uma Answer
     * @param a A Answer que se quer fazer uma copia
     */

    public Answer(Answer a){
        this.id_a = a.getId_a();
        this.score_a = a.getScore_a();
        this.user_id_a = a.getUser_id_a();
        this.comment_count_a = a.getComment_count_a();
        this.parent_id = a.getParent_id();
    }
    /**
     * Construtor vazio de uma Answer
     */
    public Answer () {
        this(0, 0, 0, 0, 0);
    }

    //GETTERS E SETTERS
    public long getId_a() {
        return id_a;
    }

    public void setId_a(long id_a) {
        this.id_a = id_a;
    }

    public int getScore_a() {
        return score_a;
    }

    public void setScore_a(int score_a) {
        this.score_a = score_a;
    }

    public long getUser_id_a() {
        return user_id_a;
    }

    public void setUser_id_a(long user_id_a) {
        this.user_id_a = user_id_a;
    }

    public int getComment_count_a() {
        return comment_count_a;
    }

    public void setComment_count_a(int comment_count_a) {
        this.comment_count_a = comment_count_a;
    }

    public long getParent_id() {
        return parent_id;
    }

    public void setParent_id(long parent_id) {
        this.parent_id = parent_id;
    }

    /**
     * Metodo para comparar duas Answers
     * @param o Um objecto (preferivelmente uma Answer)
     * @return  Retorna true apenas se "o" for igual à Answer
     */

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return id_a == answer.id_a &&
                score_a == answer.score_a &&
                user_id_a == answer.user_id_a &&
                comment_count_a == answer.comment_count_a &&
                parent_id == answer.parent_id;
    }

    /**
     * Metodo para converter uma Answer numa String
     * @return As informaçoes da Answer em formato String
     */

    public String toString() {
        return "ID: " + this.id_a + "\n" +
                "Score: " + this.score_a + "\n" +
                "ParentID: " + this.parent_id + "\n" +
                "UserID: " + this.user_id_a + "\n" +
                "CommentCount: " + this.comment_count_a + "\n";
    }

    /**
     * Metodo para clonar uma Answer
     * @return  Uma Answer com os mesmos dados
     */

    public Answer clone () {
        return new Answer(this);
    }

}

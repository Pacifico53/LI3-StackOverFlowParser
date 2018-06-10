package common;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Classe Question que representa uma pergunta no Stack overflow
 */

public class Question {
    private long id_q;              //ID da Question
    private int score_q;            //Score da Question
    private long user_id;           //ID do User que publicou dessa Question
    private String titulo;          //Titulo da Question
    private int comment_count;      //Número de comentários da Question
    private String tags;            //Tags da Question
    private int numberAnswers;      //Número de Answers da Question

    /**
     * Construtor parameterizado de um Question
     * @param id_q  ID da Question
     * @param score_q  Score da Question
     * @param user_id  ID do User que publicou essa Question
     * @param titulo Titulo da Question
     * @param comment_count Número de comentários dessa Question
     * @param tags  Tags da Question
     * @param numberAnswers Número de Answers da Question
     */

    public Question(long id_q, int score_q, long user_id, String titulo, int comment_count, String tags, int numberAnswers) {
        this.id_q = id_q;
        this.score_q = score_q;
        this.user_id = user_id;
        this.titulo = titulo;
        this.comment_count = comment_count;
        this.tags = tags;
        this.numberAnswers = numberAnswers;
    }

    /**
     * Construtor de copia de uma Question
     * @param q A Question que se quer fazer uma copia
     */

    public Question (Question q){
        this.id_q = q.getId_q();
        this.score_q = q.getScore_q();
        this.user_id = q.getUser_id();
        this.titulo = q.getTitulo();
        this.comment_count = q.getComment_count();
        this.tags = q.getTags();
        this.numberAnswers = q.getNumberAnswers();
    }

    /**
     * Construtor vazio de uma Question
     */

    public Question(){
        this(0 , 0 , 0, "" , 0 , "" , 0);
    }

    //GETTERS E SETTERS
    public long getId_q() {
        return id_q;
    }

    public void setId_q(long id_q) {
        this.id_q = id_q;
    }

    public int getScore_q() {
        return score_q;
    }

    public void setScore_q(int score_q) {
        this.score_q = score_q;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getNumberAnswers() {
        return numberAnswers;
    }

    public void setNumberAnswers(int numberAnswers) {
        this.numberAnswers = numberAnswers;
    }

    /**
     * Esta funçao retorna uma lista com as tags de uma Question, primeiro separando as tags e depois inserindo
     * num ArrayList<String>, retornando este mesmo.
     */

    public ArrayList<String> getSeparateTags(){
        ArrayList<String> result = new ArrayList<>();
        String tagsCopy = this.tags;

        for(String t : tagsCopy.split(">")){
            t = t.substring(1, t.length());
            result.add(t);
        }

        return result;
    }

    /**
     * Metodo para comparar duas Questions
     * @param o Um objecto (preferivelmente uma Question)
     * @return  Retorna true apenas se "o" for igual à Question
     */

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return id_q == question.id_q &&
                score_q == question.score_q &&
                user_id == question.user_id &&
                comment_count == question.comment_count &&
                numberAnswers == question.numberAnswers &&
                Objects.equals(titulo, question.titulo) &&
                Objects.equals(tags, question.tags);
    }

    /**
     * Metodo para converter uma Question numa String
     * @return As informaçoes da Question em formato String
     */

    public String toString() {
        return "ID: " + this.id_q + "\n" +
                "Title: " + this.titulo + "\n" +
                "Score: " + this.score_q + "\n" +
                "UserID: " + this.user_id + "\n" +
                "Tags: " + this.tags + "\n" +
                "CommentCount: " + this.comment_count + "\n" +
                "NumberAnswers: " + this.numberAnswers + "\n";
    }

    /**
     * Metodo para clonar uma Question
     * @return  Uma Question com os mesmos dados
     */

    public Question clone (){
        return new Question (this);
    }
}

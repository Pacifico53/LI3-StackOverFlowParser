package common;

import java.util.Objects;

public class Question {
    private long id_q;
    private int score_q;
    private long user_id;
    private String titulo;
    private int comment_count;
    private String tags;
    private int numberAnswers;

    public Question(long id_q, int score_q, long user_id, String titulo, int comment_count, String tags, int numberAnswers) {
        this.id_q = id_q;
        this.score_q = score_q;
        this.user_id = user_id;
        this.titulo = titulo;
        this.comment_count = comment_count;
        this.tags = tags;
        this.numberAnswers = numberAnswers;
    }

    public Question (Question q){
        this.id_q = q.getId_q();
        this.score_q = q.getScore_q();
        this.user_id = q.getUser_id();
        this.titulo = q.getTitulo();
        this.comment_count = q.getComment_count();
        this.tags = q.getTags();
        this.numberAnswers = q.getNumberAnswers();
    }

    public Question(){
        this(0 , 0 , 0, "" , 0 , "" , 0);
    }

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

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(this.id_q).append("\n");
        sb.append("Title: ").append(this.titulo).append("\n");
        sb.append("Score: ").append(this.score_q).append("\n");
        sb.append("UserID: ").append(this.user_id).append("\n");
        sb.append("Tags: ").append(this.tags).append("\n");
        sb.append("CommentCount: ").append(this.comment_count).append("\n");
        sb.append("NumberAnswers: ").append(this.numberAnswers).append("\n");
        return sb.toString();
    }

    public Question clone (){
        return new Question (this);
    }
}
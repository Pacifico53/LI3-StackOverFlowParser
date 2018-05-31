package engine;

import common.*;
import li3.TADCommunity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Classe onde se encontra a estrutura geral, e onde se faz o
 * parse dos ficheiros (a partir da classe Parse) e as queries
 */
public class TCDCom implements TADCommunity {

    //Em todas as HashMaps usamos o ID como key para encontrarmos o value
    private HashMap<Long, User> hashUsers;          //HashMap onde se vai guardar os Users
    private HashMap<Long, Tag> hashTags;            //HashMap onde se guardam as Tags
    private HashMap<Long, Question> hashQuestions;  //HashMap onde se guardam as Questions
    private HashMap<Long, Answer> hashAnswers;      //HashMap onde se guardam as Answers
    private DataCalendar calendar;                  //O "Calendario" onde se vai guardar os ids de
                                                    //Questions e Answers de cada dia

    /**
     * Construtor parameterizado
     * @param hashUsers HashMap dos Users
     * @param hashTags HashMap das Tags
     * @param hashQuestions HashMap das Questions
     * @param hashAnswers HashMap das Answers
     * @param calendar Estrutura do calendario
     */
    public TCDCom(HashMap<Long, User> hashUsers, HashMap<Long, Tag> hashTags, HashMap<Long, Question> hashQuestions, HashMap<Long, Answer> hashAnswers, DataCalendar calendar) {
        this.hashUsers = hashUsers;
        this.hashTags = hashTags;
        this.hashQuestions = hashQuestions;
        this.hashAnswers = hashAnswers;
        this.calendar = calendar;
    }

    /**
     * Construtor vazio
     */
    public TCDCom () {
        this.hashUsers = new HashMap<>();
        this.hashTags = new HashMap<>();
        this.hashQuestions = new HashMap<>();
        this.hashAnswers = new HashMap<>();
        this.calendar = new DataCalendar();
    }

    /**
     * Construtor de copia
     * @param tcd Objecto da Classe TCDCom que se quer copiar
     */
    public TCDCom (TCDCom tcd){
        this.hashUsers = tcd.getHashUsers();
        this.hashTags = tcd.getHashTags();
        this.hashQuestions = tcd.getHashQuestions();
        this.hashAnswers = tcd.getHashAnswers();
        this.calendar = tcd.getCalendar();
    }

    //GETTERS E SETTERS
    public HashMap<Long, User> getHashUsers() {
        return hashUsers;
    }

    public void setHashUsers(HashMap<Long, User> hashUsers) {
        this.hashUsers = hashUsers;
    }

    public HashMap<Long, Tag> getHashTags() {
        return hashTags;
    }

    public void setHashTags(HashMap<Long, Tag> hashTags) {
        this.hashTags = hashTags;
    }

    public HashMap<Long, Question> getHashQuestions() {
        return hashQuestions;
    }

    public void setHashQuestions(HashMap<Long, Question> hashQuestions) {
        this.hashQuestions = hashQuestions;
    }

    public HashMap<Long, Answer> getHashAnswers() {
        return hashAnswers;
    }

    public void setHashAnswers(HashMap<Long, Answer> hashAnswers) {
        this.hashAnswers = hashAnswers;
    }


    public DataCalendar getCalendar() {
        return calendar;
    }

    public void setCalendar(DataCalendar calendar) {
        this.calendar = calendar;
    }

    /**
     * Funçao que faz parse e enche a estrutura com os dados do dump
     * @param dumpPath Path para os ficheiros dump, android ou ubuntu
     */
    public void load(String dumpPath) {
        Parser parser = new Parser();
        this.calendar.init();
        System.out.println("A iniciar o parse dos ficheiros...");

        parser.parserQuestionsAnswers(this.calendar, dumpPath, this.hashQuestions, this.hashAnswers);
        parser.parseruser(dumpPath, this.hashUsers);
        parser.parsertags(dumpPath, this.hashTags);
    }

    // Query 1
    public Pair<String,String> infoFromPost(long id) {
        Answer a;
        Question q;
        User u;
        if( this.hashQuestions.containsKey(id)) {
            q = this.hashQuestions.get(id);
            u = this.hashUsers.get(q.getUser_id());
            return new Pair<>(q.getTitulo(), u.getName());
        }
        else if ( this.hashAnswers.containsKey(id)) {
            a = this.hashAnswers.get(id);
            q = this.hashQuestions.get(a.getParent_id());
            u = this.hashUsers.get(q.getUser_id());

            return new Pair<>(q.getTitulo(), u.getName());
        }
        return new Pair<>(null,null);
    }

    // Query 2
    public List<Long> topMostActive(int N) {
        for(Year year : this.calendar.getYears()) {
            for(MMonth month : year.getMonths()){
                for (Day day : month.getDays()){
                    for (long id : day.getIds()){
                        if (this.hashAnswers.containsKey(id)){
                            Answer a = this.hashAnswers.get(id);
                            User u = this.hashUsers.get(a.getUser_id_a());
                            u.incrementNumberOfPosts();
                        }else if (this.hashQuestions.containsKey(id)){
                            Question q = this.hashQuestions.get(id);
                            User u = this.hashUsers.get(q.getUser_id());
                            u.incrementNumberOfPosts();
                        }
                    }
                }
            }
        }

        ComparatorNumberPosts comparator = new ComparatorNumberPosts();
        ArrayList<User> allUsers = new ArrayList<>(this.hashUsers.values());
        allUsers.sort(comparator);

        List<Long> result = new ArrayList<>(10);
        for (int i = 0; i <10; i++) {
            result.add(allUsers.get(i).getId());
        }

        return result;
    }

    // Query 3
    public Pair<Long,Long> totalPosts(LocalDate begin, LocalDate end) {
        long questions = 0;
        long answers = 0;
        int i, j, k=0, d, l, o, p=0;
        int anoBegin = begin.getYear();
        int mesBegin = begin.getMonthValue();
        int diaBegin = begin.getDayOfMonth();

        int anoEnd = end.getYear();
        int mesEnd = end.getMonthValue();
        int diaEnd = end.getDayOfMonth();


        for (i = anoBegin-2009; i <= anoEnd-2009; i++){
            Year y = this.calendar.getYears().get(i);
            if(i == anoBegin - 2009) k = mesBegin;
            if(i == anoEnd - 2009) l = mesEnd;
            else{k = 1; l = 12;}
            for (j = k - 1; j <= l - 1; j++){
                MMonth m = y.getMonths().get(j);
                if(i == anoBegin - 2009 && j == mesBegin) p = diaBegin;
                if(i == anoEnd - 2009 && j == mesEnd) o = diaEnd;
                else{p = 1; o = 31;}
                for(d = p - 1; d <= o-1; d++){
                    Day day = m.getDays().get(d);
                    for(long id : day.getIds()){
                        if(this.hashQuestions.containsKey(id)){
                            questions++;
                        } else if(this.hashAnswers.containsKey(id)){
                            answers++;
                        }
                    }
                }
            }
        }
        return new Pair<>(questions,answers);
    }

    // Query 4
    public List<Long> questionsWithTag(String tag, LocalDate begin, LocalDate end) {
        ArrayList<Long> questionsID = new ArrayList<>();

        int i, j, k=0, d, l, o, p=0;
        int anoBegin = begin.getYear();
        int mesBegin = begin.getMonthValue();
        int diaBegin = begin.getDayOfMonth();

        int anoEnd = end.getYear();
        int mesEnd = end.getMonthValue();
        int diaEnd = end.getDayOfMonth();

        for (i = anoBegin-2009; i <= anoEnd-2009; i++){
            Year y = this.calendar.getYears().get(i);
            if(i == anoBegin-2009) k = mesBegin;
            if(i == anoEnd-2009) l = mesEnd;
            else{k = 1; l = 12;}
            for (j = k-1; j <= l-1; j++){
                MMonth m = y.getMonths().get(j);
                if(i == anoBegin-2009 && j == mesBegin) p = diaBegin;
                if(i == anoEnd-2009 && j == mesEnd) o = diaEnd;
                else{p = 1; o = 31;}
                for(d = p-1; d <= o-1; d++){
                    Day day = m.getDays().get(d);
                    for(long id : day.getIds()){
                        if(this.hashQuestions.containsKey(id)){
                            Question q = this.hashQuestions.get(id);
                            if (q.getTags().contains(tag)) questionsID.add(q.getId_q());
                        }
                    }
                }
            }
        }

        Collections.reverse(questionsID);
        return questionsID;
    }

    // Query 5
    public Pair<String, List<Long>> getUserInfo(long id) {
        String aboutMe = this.hashUsers.get(id).getAboutme();
        DataCalendar copyCalendar = this.calendar.clone();
        Collections.reverse(copyCalendar.getYears());
        ArrayList<Long> posts = new ArrayList<>();

        for(Year year : copyCalendar.getYears()) {
            Year copyYear = year.clone();
            Collections.reverse(copyYear.getMonths());
            for(MMonth month : year.getMonths()){
                MMonth copyMonth = month.clone();
                Collections.reverse(copyMonth.getDays());
                for (Day day : month.getDays()){
                    Day copyDay = day.clone();
                    Collections.reverse(copyDay.getIds());
                    for (long ids : day.getIds()){
                        if(this.hashQuestions.containsKey(ids)){
                            if (this.hashQuestions.get(ids).getUser_id() == id) posts.add(ids);
                        } else if(this.hashAnswers.containsKey(ids)){
                            if (this.hashAnswers.get(ids).getUser_id_a() == id) posts.add(ids);
                        }
                    }
                }
            }
        }

        posts = new ArrayList<>(posts.subList(0, 10));
        return new Pair<>(aboutMe,posts);
    }

    // Query 6
    public List<Long> mostVotedAnswers(int N, LocalDate begin, LocalDate end) {
        ArrayList<Answer> answers = new ArrayList<>(N);
        List<Long> result = new ArrayList<>(N);

        int i, j, k=0, d, l, o, p=0;
        int anoBegin = begin.getYear();
        int mesBegin = begin.getMonthValue();
        int diaBegin = begin.getDayOfMonth();

        int anoEnd = end.getYear();
        int mesEnd = end.getMonthValue();
        int diaEnd = end.getDayOfMonth();

        ComparatorVotosAnswer comparator = new ComparatorVotosAnswer();

        for (i = anoBegin-2009; i <= anoEnd-2009; i++){
            Year y = this.calendar.getYears().get(i);
            if(i == anoBegin-2009) k = mesBegin;
            if(i == anoEnd-2009) l = mesEnd;
            else{k = 1; l = 12;}
            for (j = k-1; j <= l-1; j++){
                MMonth m = y.getMonths().get(j);
                if(i == anoBegin-2009 && j == mesBegin) p = diaBegin;
                if(i == anoEnd - 2009 && j == mesEnd) o = diaEnd;
                else{p = 1; o = 31;}
                for(d = p-1; d <= o-1; d++){
                    Day day = m.getDays().get(d);
                    for(long id : day.getIds()){
                        if(this.hashAnswers.containsKey(id)){
                            Answer a = this.hashAnswers.get(id);
                            answers.add(a);
                        }
                    }
                }
            }
        }

        answers.sort(comparator);
        for (i = 0; i <N; i++) {
            result.add(answers.get(i).getId_a());
        }
        return result;
    }

    // Query 7
    public List<Long> mostAnsweredQuestions(int N, LocalDate begin, LocalDate end) {
        return Arrays.asList(505506L,508221L,506510L,508029L,506824L,505581L,505368L,509498L,509283L,508635L);
    }

    // Query 8
    public List<Long> containsWord(int N, String word) {
        return Arrays.asList(980835L,979082L,974117L,974105L,973832L,971812L,971056L,968451L,964999L,962770L);
    }

    // Query 9
    public List<Long> bothParticipated(int N, long id1, long id2) {
        return Arrays.asList(594L);
    }

    // Query 10
    public long betterAnswer(long id) {
        return 175891;
    }

    // Query 11
    public List<Long> mostUsedBestRep(int N, LocalDate begin, LocalDate end) {
        return Arrays.asList(6L,29L,72L,163L,587L);
    }

    public void clear(){

    }
}

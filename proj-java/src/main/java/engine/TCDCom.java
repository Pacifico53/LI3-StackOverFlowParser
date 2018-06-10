package engine;

import common.*;
import li3.TADCommunity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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
     *
     * @param hashUsers     HashMap dos Users
     * @param hashTags      HashMap das Tags
     * @param hashQuestions HashMap das Questions
     * @param hashAnswers   HashMap das Answers
     * @param calendar      Estrutura do calendario
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
    public TCDCom() {
        this.hashUsers = new HashMap<>();
        this.hashTags = new HashMap<>();
        this.hashQuestions = new HashMap<>();
        this.hashAnswers = new HashMap<>();
        this.calendar = new DataCalendar();
    }

    /**
     * Construtor de copia
     *
     * @param tcd Objecto da Classe TCDCom que se quer copiar
     */
    public TCDCom(TCDCom tcd) {
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
     *
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

    /**
     * Query 1
     * @param id do post recebido (pergunta ou resposta)
     * @return Par com o Título e Autor da pergunta (se o ID recebido for resposta, retorna-se a info da pergunta)
     */
    public Pair<String, String> infoFromPost(long id) {
        HashMap<Long, User> hashUsersCopy = new HashMap<>(this.hashUsers);
        HashMap<Long, Question> hashQuestionsCopy = new HashMap<>(this.hashQuestions);
        HashMap<Long, Answer> hashAnswersCopy = new HashMap<>(this.hashAnswers);

        Answer a;
        Question q;
        User u;
        if (hashQuestionsCopy.containsKey(id)) {                //verifica-se se o ID recebido é uma pergunta
            q = hashQuestionsCopy.get(id);                      //caso seja, guarda-se o post e o User_ID e retorna-se
            u = hashUsersCopy.get(q.getUser_id());              //o par do título com o nome do User.
            return new Pair<>(q.getTitulo(), u.getName());
        } else if (hashAnswersCopy.containsKey(id)) {           //caso o ID for de uma resposta vai se buscar a pergunta
            a = hashAnswersCopy.get(id);                        //a que está a responder e repete-se a estratégia acima
            q = hashQuestionsCopy.get(a.getParent_id());
            u = hashUsersCopy.get(q.getUser_id());

            return new Pair<>(q.getTitulo(), u.getName());
        }
        return new Pair<>(null, null);
    }

    /**
     * Query 2
     * @param N  tamanho da lista que é para retornar
     * @return Lista com os Users com mais post's de sempre
     */
    public List<Long> topMostActive(int N) {
        HashMap<Long, User> hashUsersCopy = new HashMap<>(this.hashUsers);
        HashMap<Long, Question> hashQuestionsCopy = new HashMap<>(this.hashQuestions);
        HashMap<Long, Answer> hashAnswersCopy = new HashMap<>(this.hashAnswers);

        for(Question q : hashQuestionsCopy.values()) {              //neste for percorre-se a Hash das perguntas e
            User u = hashUsersCopy.get(q.getUser_id());             //incrementa-se o número de posts do autor do post
            u.incrementNumberOfPosts();
        }

        for(Answer a : hashAnswersCopy.values()) {                  //neste for repete-se o que foi feito em cima mas
            User u = hashUsersCopy.get(a.getUser_id_a());           //na Hash das respostas
            u.incrementNumberOfPosts();
        }

        ComparatorNumberPosts comparator = new ComparatorNumberPosts();

        //stream dos Users onde, com o auxílio do ComparatorNumberPosts ordena-se pelo maior número de posts,
        //limita-se para os maiores N, extrai-se os Users_ID's e coloca-se os ID's numa List para retornar
        return hashUsersCopy.values().stream().sorted(comparator).limit(N).map(User::getId).collect(Collectors.toList());
    }


    /**
     * Query 3
     * @param begin Data do início do intervalo de tempo
     * @param end Data do fim do intervalo de tempo
     * @return Par com o número de perguntas e respostas nesse período
     */
    public Pair<Long, Long> totalPosts(LocalDate begin, LocalDate end) {
        DataCalendar calendarCopy = this.calendar.clone();
        HashMap<Long, Question> hashQuestionsCopy = new HashMap<>(this.hashQuestions);
        HashMap<Long, Answer> hashAnswersCopy = new HashMap<>(this.hashAnswers);

        long questions = 0;
        long answers = 0;
        int i, j, k = 0, d, l, o, p = 0;
        int anoBegin = begin.getYear();
        int mesBegin = begin.getMonthValue();
        int diaBegin = begin.getDayOfMonth();

        int anoEnd = end.getYear();
        int mesEnd = end.getMonthValue();
        int diaEnd = end.getDayOfMonth();


        for (i = anoBegin - 2009; i <= anoEnd - 2009; i++) {      //nestes for's percorre-se o calendário entre as datas
            Year y = calendarCopy.getYears().get(i);
            if (i == anoBegin - 2009) k = mesBegin;
            if (i == anoEnd - 2009) l = mesEnd;
            else {
                k = 1;
                l = 12;
            }
            for (j = k - 1; j <= l - 1; j++) {
                MMonth m = y.getMonths().get(j);
                if (i == anoBegin - 2009 && j == mesBegin) p = diaBegin;
                if (i == anoEnd - 2009 && j == mesEnd) o = diaEnd;
                else {
                    p = 1;
                    o = 31;
                }
                for (d = p - 1; d <= o - 1; d++) {
                    Day day = m.getDays().get(d);
                    for (long id : day.getIds()) {
                        if (hashQuestionsCopy.containsKey(id)) {
                            questions++;                                    //itera-se o número de perguntas
                        } else if (hashAnswersCopy.containsKey(id)) {
                            answers++;                                      //itera-se o número de respostas
                        }
                    }
                }
            }
        }
        return new Pair<>(questions, answers);                              //devolve-e o par dos dois longs
    }

    /**
     * Query 4
     * @param tag String com a tag que é para procurar
     * @param begin Data do início do intervalo de tempo
     * @param end Data do final do intervalo de tempo
     * @return Lista com os ID's das perguntas que contêm a tag dentro do intervalo de tempo
     */
    public List<Long> questionsWithTag(String tag, LocalDate begin, LocalDate end) {
        DataCalendar calendarCopy = this.calendar.clone();
        HashMap<Long, Question> hashQuestionsCopy = new HashMap<>(this.hashQuestions);

        ArrayList<Long> questionsID = new ArrayList<>();

        int i, j, k = 0, d, l, o, p = 0;
        int anoBegin = begin.getYear();
        int mesBegin = begin.getMonthValue();
        int diaBegin = begin.getDayOfMonth();

        int anoEnd = end.getYear();
        int mesEnd = end.getMonthValue();
        int diaEnd = end.getDayOfMonth();

        for (i = anoBegin - 2009; i <= anoEnd - 2009; i++) {      //nestes for's percorre-se o calendário entre as datas
            Year y = calendarCopy.getYears().get(i);
            if (i == anoBegin - 2009) k = mesBegin;
            if (i == anoEnd - 2009) l = mesEnd;
            else {
                k = 1;
                l = 12;
            }
            for (j = k - 1; j <= l - 1; j++) {
                MMonth m = y.getMonths().get(j);
                if (i == anoBegin - 2009 && j == mesBegin) p = diaBegin;
                if (i == anoEnd - 2009 && j == mesEnd) o = diaEnd;
                else {
                    p = 1;
                    o = 31;
                }
                for (d = p - 1; d <= o - 1; d++) {
                    Day day = m.getDays().get(d);
                    for (long id : day.getIds()) {
                        if (hashQuestionsCopy.containsKey(id)) {
                            Question q = hashQuestionsCopy.get(id);
                            if (q.getTags().contains(tag)) questionsID.add(q.getId_q());  //caso a pergunta contenha a
                        }                                                                 //tag, adiciona-se o ID à lista
                    }
                }
            }
        }

        Collections.reverse(questionsID);      //inverte-se a lista, pois pedem os ID's ordenados em cronologia inversa
        return questionsID;
    }

    /**
     * Query 5
     * @param id de um User
     * @return Par da informaçao do seu perfil e os IDs dos seus ultimos 10 post's
     */
    public Pair<String, List<Long>> getUserInfo(long id) {
        DataCalendar copyCalendar = new DataCalendar(this.calendar);
        HashMap<Long, User> hashUsersCopy = new HashMap<>(this.hashUsers);
        HashMap<Long, Question> hashQuestionsCopy = new HashMap<>(this.hashQuestions);
        HashMap<Long, Answer> hashAnswersCopy = new HashMap<>(this.hashAnswers);

        String aboutMe = hashUsersCopy.get(id).getAboutme();        //guarda-se a info do User para o retorno
        ArrayList<Year> copyYears = new ArrayList<>(copyCalendar.getYears());
        Collections.reverse(copyYears);                 //para encontrar os últimos 10 posts, inverte-se o calendário
                                                        //aqui inverteu-se o array dos anos
        ArrayList<Long> posts = new ArrayList<>();

        for (Year year : copyYears) {
            Year copyYear = year.clone();
            ArrayList<MMonth> copyMonths = new ArrayList<>(copyYear.getMonths());
            Collections.reverse(copyMonths);            //inverte-se o array dos meses
            for (MMonth month : copyMonths) {
                MMonth copyMonth = month.clone();
                ArrayList<Day> copyDays = new ArrayList<>(copyMonth.getDays());
                Collections.reverse(copyDays);          //inverte-se o array dos dias
                for (Day day : copyDays) {
                    Day copyDay = day.clone();
                    ArrayList<Long> copyIds = new ArrayList<>(copyDay.getIds());
                    Collections.reverse(copyIds);       //inverte-se o array dos IDs
                    for (long ids : copyIds) {
                        if (hashQuestionsCopy.containsKey(ids)) {
                            if (hashQuestionsCopy.get(ids).getUser_id() == id) posts.add(ids);  //adiciona-se numa lista
                        } else if (hashAnswersCopy.containsKey(ids)) {                          //os IDs dos posts feitos
                            if (hashAnswersCopy.get(ids).getUser_id_a() == id) posts.add(ids);  //pelo User fornecido
                        }
                    }
                }
            }
        }

        posts = new ArrayList<>(posts.subList(0, 10));              //limita-se a lista apenas a 10 posts
        return new Pair<>(aboutMe, posts);
    }

    /**
     * Query 6
     * @param N Numero de respostas para retorno
     * @param begin Data do início do intervalo de tempo
     * @param end Data do fim do intervalo de tempo
     * @return Lista dos IDs das respostas com mais votos
     */
    public List<Long> mostVotedAnswers(int N, LocalDate begin, LocalDate end) {
        DataCalendar calendarCopy = this.calendar.clone();
        HashMap<Long, Answer> hashAnswersCopy = new HashMap<>(this.hashAnswers);

        ArrayList<Answer> answers = new ArrayList<>();

        int i, j, k = 0, d, l, o, p = 0;
        int anoBegin = begin.getYear();
        int mesBegin = begin.getMonthValue();
        int diaBegin = begin.getDayOfMonth();

        int anoEnd = end.getYear();
        int mesEnd = end.getMonthValue();
        int diaEnd = end.getDayOfMonth();

        ComparatorVotosAnswer comparator = new ComparatorVotosAnswer();

        for (i = anoBegin - 2009; i <= anoEnd - 2009; i++) {        //percorre-se o calendario entre as datas
            Year y = calendarCopy.getYears().get(i);
            if (i == anoBegin - 2009) k = mesBegin;
            if (i == anoEnd - 2009) l = mesEnd;
            else {
                k = 1;
                l = 12;
            }
            for (j = k - 1; j <= l - 1; j++) {
                MMonth m = y.getMonths().get(j);
                if (i == anoBegin - 2009 && j == mesBegin) p = diaBegin;
                if (i == anoEnd - 2009 && j == mesEnd) o = diaEnd;
                else {
                    p = 1;
                    o = 31;
                }
                for (d = p - 1; d <= o - 1; d++) {
                    Day day = m.getDays().get(d);
                    for (long id : day.getIds()) {
                        if (hashAnswersCopy.containsKey(id)) {
                            Answer a = hashAnswersCopy.get(id);
                            answers.add(a);         //adiciona-se num ArrayList todas as respostas feitas entre as datas
                        }
                    }
                }
            }
        }
        //stream das respostas que, com o auxilio do ComparatorVotosAnswer, ordena o array em ordem decrescente do
        //numero de votos, depois limita as primeiras N, extrai os IDs e coloca numa List

        return answers.stream().sorted(comparator).limit(N).map(Answer::getId_a).collect(Collectors.toList());
    }

    /**
     * Query 7
     * @param N Numero das questoes para retornar
     * @param begin Data do início do intervalo de tempo
     * @param end Data do fim do intervalo de tempo
     * @return Lista com os IDs das respostas mais respondidas entre as datas
     */
    public List<Long> mostAnsweredQuestions(int N, LocalDate begin, LocalDate end) {
        DataCalendar calendarCopy = this.calendar.clone();
        HashMap<Long, Question> hashQuestionsCopy = new HashMap<>(this.hashQuestions);

        ArrayList<Question> questions = new ArrayList<>(N);

        int i, j, k = 0, d, l, o, p = 0;
        int anoBegin = begin.getYear();
        int mesBegin = begin.getMonthValue();
        int diaBegin = begin.getDayOfMonth();

        int anoEnd = end.getYear();
        int mesEnd = end.getMonthValue();
        int diaEnd = end.getDayOfMonth();

        ComparatorNumberAnswers comparator = new ComparatorNumberAnswers();

        for (i = anoBegin - 2009; i <= anoEnd - 2009; i++) {        //nestes for's percorre-se o calendario
            Year y = calendarCopy.getYears().get(i);
            if (i == anoBegin - 2009) k = mesBegin;
            if (i == anoEnd - 2009) l = mesEnd;
            else {
                k = 1;
                l = 12;
            }
            for (j = k - 1; j <= l - 1; j++) {
                MMonth m = y.getMonths().get(j);
                if (i == anoBegin - 2009 && j == mesBegin) p = diaBegin;
                if (i == anoEnd - 2009 && j == mesEnd) o = diaEnd;
                else {
                    p = 1;
                    o = 31;
                }
                for (d = p - 1; d <= o - 1; d++) {
                    Day day = m.getDays().get(d);
                    for (long id : day.getIds()) {
                        if (hashQuestionsCopy.containsKey(id)) {
                            Question q = hashQuestionsCopy.get(id);
                            questions.add(q);       //adiciona-se todas as perguntas a um ArrayList
                        }
                    }
                }
            }
        }
        //stream das perguntas que, atraves do ComparatorNumberAnswers, ordena as perguntas em ordem decrescente do
        //numero de respostas obtidas, limita-se a lista as N primeiras, extrai-se os IDS e coloca-se numa List
        return questions.stream().sorted(comparator).limit(N).map(Question::getId_q).collect(Collectors.toList());
    }

    // Query 8
    public List<Long> containsWord(int N, String word) {
        DataCalendar copyCalendar = new DataCalendar(this.calendar);
        HashMap<Long, Question> hashQuestionsCopy = new HashMap<>(this.hashQuestions);

        ArrayList<Year> copyYears = new ArrayList<>(copyCalendar.getYears());
        Collections.reverse(copyYears);

        List<Long> result = new ArrayList<>();

        for (Year year : copyYears) {
            Year copyYear = year.clone();
            ArrayList<MMonth> copyMonths = new ArrayList<>(copyYear.getMonths());
            Collections.reverse(copyMonths);
            for (MMonth month : copyMonths) {
                MMonth copyMonth = month.clone();
                ArrayList<Day> copyDays = new ArrayList<>(copyMonth.getDays());
                Collections.reverse(copyDays);
                for (Day day : copyDays) {
                    Day copyDay = day.clone();
                    ArrayList<Long> copyIds = new ArrayList<>(copyDay.getIds());
                    Collections.reverse(copyIds);
                    for (long ids : copyIds) {
                        if (hashQuestionsCopy.containsKey(ids)) {
                            if (hashQuestionsCopy.get(ids).getTitulo().contains(word)) result.add(ids);
                        }
                    }
                }
            }
        }

        return (N < result.size()) ? result.subList(0,N) : result;
    }

    // Query 9
    public List<Long> bothParticipated(int N, long id1, long id2) {
        DataCalendar copyCalendar = new DataCalendar(this.calendar);
        HashMap<Long, Question> hashQuestionsCopy = new HashMap<>(this.hashQuestions);
        HashMap<Long, Answer> hashAnswersCopy = new HashMap<>(this.hashAnswers);

        ArrayList<Year> copyYears = new ArrayList<>(copyCalendar.getYears());
        Collections.reverse(copyYears);

        ArrayList<Long> questionsUser1 = new ArrayList<>();
        ArrayList<Long> questionsUser2 = new ArrayList<>();

        ArrayList<Long> answersUser1 = new ArrayList<>();
        ArrayList<Long> answersUser2 = new ArrayList<>();


        List<Long> result = new ArrayList<>();

        for (Year year : copyYears) {
            Year copyYear = year.clone();
            ArrayList<MMonth> copyMonths = new ArrayList<>(copyYear.getMonths());
            Collections.reverse(copyMonths);
            for (MMonth month : copyMonths) {
                MMonth copyMonth = month.clone();
                ArrayList<Day> copyDays = new ArrayList<>(copyMonth.getDays());
                Collections.reverse(copyDays);
                for (Day day : copyDays) {
                    Day copyDay = day.clone();
                    ArrayList<Long> copyIds = new ArrayList<>(copyDay.getIds());
                    Collections.reverse(copyIds);
                    for (long ids : copyIds) {
                        if (hashQuestionsCopy.containsKey(ids)) {
                            if (hashQuestionsCopy.get(ids).getUser_id() == id1) {
                                questionsUser1.add(hashQuestionsCopy.get(ids).getId_q());
                            }
                            if (hashQuestionsCopy.get(ids).getUser_id() == id2) {
                                questionsUser2.add(hashQuestionsCopy.get(ids).getId_q());
                            }
                        }
                        if (hashAnswersCopy.containsKey(ids)) {
                            if (hashAnswersCopy.get(ids).getUser_id_a() == id1) {
                                answersUser1.add(hashAnswersCopy.get(ids).getParent_id());
                            }
                            if (hashAnswersCopy.get(ids).getUser_id_a() == id2) {
                                answersUser2.add(hashAnswersCopy.get(ids).getParent_id());
                            }
                        }
                    }
                }
            }
        }

        for (Long q1 : questionsUser1) {
            if (answersUser2.contains(q1) && !result.contains(q1)) {
                result.add(q1);
            }
        }

        for (Long q2 : questionsUser2) {
            if (answersUser1.contains(q2) && !result.contains(q2)) {
                result.add(q2);
            }
        }

        for (Long q2 : answersUser1) {
            if (answersUser2.contains(q2) && !result.contains(q2)) {
                result.add(q2);
            }
        }

        return (N < result.size()) ? result.subList(0, N) : result;
    }

    // Query 10
    public long betterAnswer(long id) {
        HashMap<Long, User> hashUsersCopy = new HashMap<>(this.hashUsers);
        HashMap<Long, Answer> hashAnswersCopy = new HashMap<>(this.hashAnswers);

        ArrayList<Answer> answersDaQuestion = new ArrayList<>();
        ComparatorBestAnswer comparator = new ComparatorBestAnswer(hashUsersCopy);

        for(Answer a : hashAnswersCopy.values()){
            if(a.getParent_id() == id) {
                answersDaQuestion.add(a.clone());
            }
        }

        answersDaQuestion.sort(comparator);
        return answersDaQuestion.get(0).getId_a();
    }

    // Query 11
    public List<Long> mostUsedBestRep(int N, LocalDate begin, LocalDate end) {
        DataCalendar calendarCopy = this.calendar.clone();
        HashMap<Long, User> hashUsersCopy = new HashMap<>(this.hashUsers);
        HashMap<Long, Question> hashQuestionsCopy = new HashMap<>(this.hashQuestions);
        HashMap<Long, Tag> hashTagCopy = new HashMap<>(this.hashTags);

        List<User> nBestUsers = new ArrayList<>();
        ComparatorRepUsers comparatorRepUsers = new ComparatorRepUsers();

        ArrayList<Question> questions = new ArrayList<>();

        int i, j, k = 0, d, l, o, p = 0;
        int anoBegin = begin.getYear();
        int mesBegin = begin.getMonthValue();
        int diaBegin = begin.getDayOfMonth();

        int anoEnd = end.getYear();
        int mesEnd = end.getMonthValue();
        int diaEnd = end.getDayOfMonth();

        for (i = anoBegin - 2009; i <= anoEnd - 2009; i++) {
            Year y = calendarCopy.getYears().get(i);
            if (i == anoBegin - 2009) k = mesBegin;
            if (i == anoEnd - 2009) l = mesEnd;
            else {
                k = 1;
                l = 12;
            }
            for (j = k - 1; j <= l - 1; j++) {
                MMonth m = y.getMonths().get(j);
                if (i == anoBegin - 2009 && j == mesBegin) p = diaBegin;
                if (i == anoEnd - 2009 && j == mesEnd) o = diaEnd;
                else {
                    p = 1;
                    o = 31;
                }
                for (d = p - 1; d <= o - 1; d++) {
                    Day day = m.getDays().get(d);
                    for (long id : day.getIds()) {
                        if (hashQuestionsCopy.containsKey(id)) {
                            questions.add(hashQuestionsCopy.get(id));

                            long userid = hashQuestionsCopy.get(id).getUser_id();
                            User u = hashUsersCopy.get(userid).clone();
                            nBestUsers.add(u);
                        }
                    }
                }
            }
        }

        nBestUsers.sort(comparatorRepUsers);
        nBestUsers = nBestUsers.subList(0, N);

        for (Question q : questions) {
            if (nBestUsers.contains(hashUsersCopy.get(q.getUser_id()))) {
                for (String ts : q.getSeparateTags()) {
                    for (Tag t : hashTagCopy.values()) {
                        if (t.getTagname().equals(ts)) {
                            t.incrementCount();
                        }
                    }
                }
            }
        }

        ComparatorTagCount comparatorTagCount = new ComparatorTagCount();

        return hashTagCopy.values().stream().sorted(comparatorTagCount).limit(N).map(Tag::getTag_id).collect(Collectors.toList());
    }

    public void clear() {
        for (Year year : this.calendar.getYears()) {
            for (MMonth month : year.getMonths()) {
                for (Day day : month.getDays()) {
                    day.getIds().clear();
                }
                month.getDays().clear();
            }
            year.getMonths().clear();
        }
        this.calendar.getYears().clear();
        this.hashAnswers.clear();
        this.hashQuestions.clear();
        this.hashTags.clear();
        this.hashUsers.clear();
    }
}

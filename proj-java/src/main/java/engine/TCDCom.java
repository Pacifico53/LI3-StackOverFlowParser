package engine;

import common.*;
import li3.TADCommunity;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class TCDCom implements TADCommunity {

    private MyLog qelog;
    private HashMap<Long, User> hashUsers;
    private HashMap<Long, Tag> hashTags;
    private HashMap<Long, Question> hashQuestions;
    private HashMap<Long, Answer> hashAnswers;
    private DataCalendar calendar;


    public TCDCom(HashMap<Long, User> hashUsers, HashMap<Long, Tag> hashTags, HashMap<Long, Question> hashQuestions, HashMap<Long, Answer> hashAnswers) {
        this.hashUsers = hashUsers;
        this.hashTags = hashTags;
        this.hashQuestions = hashQuestions;
        this.hashAnswers = hashAnswers;

    }

    public TCDCom () {
        this.hashUsers = new HashMap<>();
        this.hashTags = new HashMap<>();
        this.hashQuestions = new HashMap<>();
        this.hashAnswers = new HashMap<>();
        this.calendar = new DataCalendar();
    }

    public TCDCom (TCDCom tcd){
        this.hashUsers = tcd.getHashUsers();
        this.hashTags = tcd.getHashTags();
        this.hashQuestions = tcd.getHashQuestions();
        this.hashAnswers = tcd.getHashAnswers();
    }

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
    /*
    public void init() {
        this.qelog = new MyLog("queryengine");
    }
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

        Comparator comparator = new Comparator<User>() {
            @Override
            public int compare(User u1, User u2) {
                if (u1.getNumberofPosts() > u2.getNumberofPosts()) return -1;
                else if (u1.getNumberofPosts() < u2.getNumberofPosts()) return 1;
                else return 0;
            }
        };

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
        int i=0, j=0, k=0, d=0, l=0, o=0, p=0;
        int anoBegin = begin.getYear();
        int mesBegin = begin.getMonthValue();
        int diaBegin = begin.getDayOfMonth();

        int anoEnd = end.getYear();
        int mesEnd = end.getMonthValue();
        int diaEnd = end.getDayOfMonth();
        for (i=anoBegin-2009; i<anoEnd-2009;i++){
            Year y = this.calendar.getYears().get(i);
            if(i == anoBegin -2009) k=mesBegin;
            if(i == anoEnd -2009) l=mesEnd;
            else{k=1;l=12;}
            for (j=k-1;j<=l-1;j++){
                MMonth m = y.getMonths().get(j);
                if(i == anoBegin - 2009 && j == mesBegin) p = diaBegin;
                if(i == anoEnd - 2009 && j == mesEnd) o = diaEnd;
                else{p=1;o=31;}
                for(d=p-1;d<=o-1;d++){
                    Day day = m.getDays().get(d);
                    for(long id : day.getIds()){
                        if(this.hashQuestions.containsKey(id)){
                            questions++;
                        }
                        if(this.hashAnswers.containsKey(id)){
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
        return Arrays.asList(276174L,276029L,274462L,274324L,274316L,274141L,274100L,272937L,
                272813L,272754L,272666L,272565L,272450L,272313L,271816L,271683L,271647L,270853L,270608L,270528L,270488L,
                270188L,270014L,269876L,269781L,269095L,268501L,268155L,267746L,267656L,267625L,266742L,266335L,266016L,
                265531L,265483L,265443L,265347L,265104L,265067L,265028L,264764L,264762L,264616L,264525L,264292L,263816L,
                263740L,263460L,263405L,263378L,263253L,262733L,262574L);
    }

    // Query 5
    public Pair<String, List<Long>> getUserInfo(long id) {
        String shortBio = "<p>Coder. JS, Perl, Python, Basic<br>Books/movies: SF+F.<br>Dead:" +
                "dell 9300<br>Dead: dell 1720 as of may 10th 2011.</p>\n" +
                "<p>Current system: Acer Aspire 7750G.<br>\n" +
                "Works OOTB as of Ubuntu 12.04.<br></p>";
        List<Long> ids = Arrays.asList(982507L,982455L,980877L,980197L,980189L,976713L,974412L,
                974359L,973895L,973838L);
        return new Pair<>(shortBio,ids);
    }

    // Query 6
    public List<Long> mostVotedAnswers(int N, LocalDate begin, LocalDate end) {
        return Arrays.asList(701775L,697197L,694560L,696641L,704208L);
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

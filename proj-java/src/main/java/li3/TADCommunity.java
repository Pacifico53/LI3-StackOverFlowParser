package li3;

import java.time.LocalDate;
import java.util.List;
import common.Pair;
import engine.PostNaoEncontradoException;
import engine.QuestionNaoEncontradaException;
import engine.TagNaoEncontradaException;
import engine.UserNaoEncontradoException;

public interface TADCommunity {
    public void load(String dumpPath);

    // Query 1
    public Pair<String,String> infoFromPost(long id) throws PostNaoEncontradoException;

    // Query 2
    public List<Long> topMostActive(int N);

    // Query 3
    public Pair<Long,Long> totalPosts(LocalDate begin, LocalDate end);

    // Query 4
    public List<Long> questionsWithTag(String tag, LocalDate begin, LocalDate end) throws TagNaoEncontradaException;

    // Query 5
    public Pair<String, List<Long>> getUserInfo(long id) throws UserNaoEncontradoException;

    // Query 6
    public List<Long> mostVotedAnswers(int N, LocalDate begin, LocalDate end);

    // Query 7
    public List<Long> mostAnsweredQuestions(int N, LocalDate begin, LocalDate end);

    // Query 8
    public List<Long> containsWord(int N, String word);

    // Query 9
    public List<Long> bothParticipated(int N, long id1, long id2) throws UserNaoEncontradoException;

    // Query 10
    public long betterAnswer(long id) throws QuestionNaoEncontradaException;

    // Query 11
    public List<Long> mostUsedBestRep(int N, LocalDate begin, LocalDate end);

    public void clear();
}




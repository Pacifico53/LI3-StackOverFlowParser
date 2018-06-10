package common;

import java.io.File;
import java.time.LocalDate;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

/**
 * Classe utilizada para fazer parse dos ficheiros dump, isto é utilizado no "load", e vai encher as estruturas com os dados do dump
 */
public class Parser {

    /**
     * Funcao utilizada para converter uma String que contem uma data, como
     * aparece no dump, para uma LocalDate
     * @param date Uma data em formato string, por exemplo "2016-07-20T22:55:21.177"
     * @return A mesma data no formato LocalDate
     */
    private LocalDate xmlToDate(String date) {
        LocalDate date1;
        int year;
        int month;
        int day;
        date = date.substring(0, 10);
        year = Integer.parseInt(date.substring(0, 4));
        month = Integer.parseInt(date.substring(5, 7));
        day = Integer.parseInt(date.substring(8, 10));

        date1 = LocalDate.of(year, month, day);
        return date1;
    }

    /**
     * Metodo para percorrer o ficheiro Users.xml e encher a HashMap de users com os dados necessarios
     * @param path Path para a pasta que contem o dump
     * @param hashUsers HashMap dos users
     */
    public void parseruser(String path, HashMap<Long,User> hashUsers) {
        try {
            String pathFile = path.concat("/Users.xml");
            File inputFile = new File(pathFile);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("row");
            System.out.println("-------------PARSE USERS---------------");
            for (int temp = 0; temp < nList.getLength(); temp++) {      //neste for percorre-se todos os Users do ficheiro
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    long id = Long.parseLong(eElement.getAttribute("Id"));
                    int rep = Integer.parseInt(eElement.getAttribute("Reputation"));
                    String aboutme = eElement.getAttribute("AboutMe");
                    String name = eElement.getAttribute("DisplayName");

                    //cria-se novo User e adiciona-se na HashTable dos Users
                    User u = new User(id, name, aboutme, rep , 0);
                    hashUsers.put(id,u);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo para percorrer o ficheiro Posts.xml, vai colocar os ids dos posts na devida data,
     * e guardar os dados das Answers e Questions nas HashMaps correspondentes
     * @param calendar A estrutura do calendario
     * @param path  Path para a pasta contendo o dump
     * @param hashQuestions HashMap das Questions
     * @param hashAnswers   HashMap das Answers
     */
    public void parserQuestionsAnswers(DataCalendar calendar, String path, HashMap<Long,Question> hashQuestions , HashMap<Long,Answer> hashAnswers) {
        try {
            String pathFile = path.concat("/Posts.xml");
            File inputFile = new File(pathFile);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("row");
            NodeList nList2 = doc.getElementsByTagName("row");
            System.out.println("--------------PARSE QUESTIONS--------------");
            for (int temp = 0; temp < nList.getLength(); temp++) {                      //este for percorre todos post's do ficheiro
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    if(eElement.getAttribute("PostTypeId").equals("1")) {         //verifica se o post é uma pergunta
                        long id_q = Long.parseLong(eElement.getAttribute("Id"));  //caso seja guarda-se os atributos desejados
                        int score_q = Integer.parseInt(eElement.getAttribute("Score"));
                        long user_id_q = Long.parseLong(eElement.getAttribute("OwnerUserId"));
                        String title_q = eElement.getAttribute("Title");
                        int comment_count_q = Integer.parseInt(eElement.getAttribute("CommentCount"));
                        String tags = eElement.getAttribute("Tags");
                        int number_answers = Integer.parseInt(eElement.getAttribute("AnswerCount"));

                        //cria-se uma nova pergunta e adiciona-se na HashTable das perguntas
                        Question q = new Question(id_q, score_q, user_id_q, title_q, comment_count_q, tags, number_answers);
                        hashQuestions.put(id_q,q);

                        //converte-se a data para LocalDate através da função auxiliar xmlToDate e adiciona-se o ID no calendário
                        LocalDate date1 = xmlToDate(eElement.getAttribute("CreationDate"));
                        calendar.addID(date1.getYear() - 2009, date1.getMonthValue() - 1, date1.getDayOfMonth() - 1, Long.parseLong(eElement.getAttribute("Id")));
                    }
                }
            }

            System.out.println("-------------PARSE ANSWERS----------------");
            for (int temp = 0; temp < nList2.getLength(); temp++) {
                Node nNode = nList2.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    if(eElement.getAttribute("PostTypeId").equals("2")) {         //verifica-se se o post é uma resposta
                        long id_a = Long.parseLong(eElement.getAttribute("Id"));  //caso seja, guarda-se os atributos desejados
                        int score_a = Integer.parseInt(eElement.getAttribute("Score"));
                        long user_id_a = Long.parseLong(eElement.getAttribute("OwnerUserId"));
                        int comment_count_a = Integer.parseInt(eElement.getAttribute("CommentCount"));
                        long parent_id = Long.parseLong(eElement.getAttribute("ParentId"));

                        //cria-se uma nova resposta e adiciona-se na HashTable das respostas
                        Answer a = new Answer(id_a,score_a,user_id_a,comment_count_a,parent_id);
                        hashAnswers.put(id_a,a);

                        //converte-se a data para LocalDate através da função auxiliar xmlToDate e adiciona-se o ID no calendário
                        LocalDate date2 = xmlToDate(eElement.getAttribute("CreationDate"));
                        calendar.addID(date2.getYear() - 2009, date2.getMonthValue() - 1, date2.getDayOfMonth() - 1, Long.parseLong(eElement.getAttribute("Id")));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo para percorrer o ficheiro Tags.xml e encher a HashMap de tags com os dados necessarios
     * @param path Path para a pasta com o dump
     * @param hashTags HashMap das tags
     */
    public void parsertags(String path, HashMap<Long,Tag> hashTags) {
        try {
            String pathFile = path.concat("/Tags.xml");
            File inputFile = new File(pathFile);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("row");
            System.out.println("--------------PARSE TAGS--------------");
            for (int temp = 0; temp < nList.getLength(); temp++) {      //neste for percorre-se todas as Tags do ficheiro
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    long id = Long.parseLong(eElement.getAttribute("Id"));
                    String tagname = eElement.getAttribute("TagName");

                    //cria-se uma nova Tag e adiciona-se na HashTable das tags
                    Tag t = new Tag(tagname, 0, id);
                    hashTags.put(id,t);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}











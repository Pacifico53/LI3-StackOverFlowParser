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


public class Parser {

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
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    long id = Long.parseLong(eElement.getAttribute("Id"));
                    int rep = Integer.parseInt(eElement.getAttribute("Reputation"));
                    String aboutme = eElement.getAttribute("AboutMe");
                    String name = eElement.getAttribute("DisplayName");

                    User u = new User(id, name, aboutme, rep , 0);
                    hashUsers.put(id,u);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    if(eElement.getAttribute("PostTypeId").equals("1")) {
                        long id_q = Long.parseLong(eElement.getAttribute("Id"));
                        int score_q = Integer.parseInt(eElement.getAttribute("Score"));
                        long user_id_q = Long.parseLong(eElement.getAttribute("OwnerUserId"));
                        String title_q = eElement.getAttribute("Title");
                        int comment_count_q = Integer.parseInt(eElement.getAttribute("CommentCount"));
                        String tags = eElement.getAttribute("Tags");
                        int number_answers = Integer.parseInt(eElement.getAttribute("AnswerCount"));

                        Question q = new Question(id_q, score_q, user_id_q, title_q, comment_count_q, tags, number_answers);
                        hashQuestions.put(id_q,q);

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
                    if(eElement.getAttribute("PostTypeId").equals("2")) {
                        long id_a = Long.parseLong(eElement.getAttribute("Id"));
                        int score_a = Integer.parseInt(eElement.getAttribute("Score"));
                        long user_id_a = Long.parseLong(eElement.getAttribute("OwnerUserId"));
                        int comment_count_a = Integer.parseInt(eElement.getAttribute("CommentCount"));
                        long parent_id = Long.parseLong(eElement.getAttribute("ParentId"));

                        Answer a = new Answer(id_a,score_a,user_id_a,comment_count_a,parent_id);
                        hashAnswers.put(id_a,a);

                        LocalDate date2 = xmlToDate(eElement.getAttribute("CreationDate"));
                        calendar.addID(date2.getYear() - 2009, date2.getMonthValue() - 1, date2.getDayOfMonth() - 1, Long.parseLong(eElement.getAttribute("Id")));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    long id = Long.parseLong(eElement.getAttribute("Id"));
                    String tagname = eElement.getAttribute("TagName");

                    Tag t = new Tag(tagname, 0, id);
                    hashTags.put(id,t);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}











package common;

import java.io.File;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;


public class Parser {

    private LocalDate xmlToDate(String date) throws ParseException {
        LocalDate date1;
        int year = 0;
        int month = 0;
        int day = 0;
        date = date.substring(0, 10);
        year = Integer.parseInt(date.substring(0, 4));
        month = Integer.parseInt(date.substring(5, 7));
        day = Integer.parseInt(date.substring(8, 10));

        date1 = LocalDate.of(year, month, day);
        return date1;
    }

    public void parseruser(String path, HashMap<Long,User> hashusers) {
        try {
            String pathFile = path.concat("/Users.xml");
            File inputFile = new File(pathFile);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("row");
            System.out.println("-------------PARSE USERS---------------");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                System.out.println("-----------------------");
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    long id = Long.parseLong(eElement.getAttribute("Id"));
                    int rep = Integer.parseInt(eElement.getAttribute("Reputation"));
                    String aboutme = eElement.getAttribute("AboutMe");
                    String name = eElement.getAttribute("DisplayName");

                    User u = new User(id, name, aboutme, rep , 0);
                    hashusers.put(id,u);

                    System.out.println("Reputação : "
                            + eElement.getAttribute("Reputation"));
                    System.out.println("ID : "
                            + eElement.getAttribute("Id"));
                    System.out.println("AboutMe : "
                            + eElement.getAttribute("AboutMe"));
                    System.out.println("Name : "
                            + eElement.getAttribute("DisplayName"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
<<<<<<< HEAD
    public void parserQuestionsAnswers(String path, HashMap<Long,Question> hashquestions , HashMap<Long,Answer> hashanswers) {
=======
    public void parserQuestionsAnswers(DataCalendar calendar, String path) {
>>>>>>> cdedbc33ac824298e645c64403d507d97a8ce5c2
        try {
            String pathFile = path.concat("/Posts.xml");
            File inputFile = new File(pathFile);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("row");
            NodeList nList2 = doc.getElementsByTagName("row");
            System.out.println("--------------PARSE QUESTIONS--------------");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    if(eElement.getAttribute("PostTypeId").equals("1")) {
                        System.out.println("-----------------------");

                        long idq = Long.parseLong(eElement.getAttribute("Id"));
                        int scoreq = Integer.parseInt(eElement.getAttribute("Score"));
                        long user_idq = Long.parseLong(eElement.getAttribute("OwnerUserId"));
                        String titleq = eElement.getAttribute("Title");
                        int commentcountq = Integer.parseInt(eElement.getAttribute("CommentCount"));
                        String tagsq = eElement.getAttribute("Tags");
                        int numberanswersq = Integer.parseInt(eElement.getAttribute("AnswerCount"));

                        Question q = new Question(idq,scoreq,user_idq,titleq,commentcountq,tagsq,numberanswersq);
                        hashquestions.put(idq,q);


                        System.out.println("tipo : "
                                + eElement.getAttribute("PostTypeId"));
                        System.out.println("ID : "
                                + eElement.getAttribute("Id"));
                        System.out.println("Score : "
                                + eElement.getAttribute("Score"));
                        System.out.println("OwnerUserId : "
                                + eElement.getAttribute("OwnerUserId"));
                        System.out.println("Title : "
                                + eElement.getAttribute("Title"));
                        System.out.println("Tags : "
                                + eElement.getAttribute("Tags"));
                        System.out.println("CommentCount : "
                                + eElement.getAttribute("CommentCount"));
                        System.out.println("AnswerCount : "
                                + eElement.getAttribute("AnswerCount"));
                        System.out.println("data : "
                                + eElement.getAttribute("CreationDate").substring(0, 10));
                        LocalDate datateste = xmlToDate(eElement.getAttribute("CreationDate"));
                        calendar.addID(datateste.getYear() - 2009, datateste.getMonthValue() - 1, datateste.getDayOfMonth() - 1, Long.parseLong(eElement.getAttribute("Id")));
                    }
                }
            }

            System.out.println("-------------PARSE ANSWERS----------------");
            for (int temp = 0; temp < nList2.getLength(); temp++) {
                Node nNode = nList2.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    if(eElement.getAttribute("PostTypeId").equals("2")) {
                        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1");

                        long ida = Long.parseLong(eElement.getAttribute("Id"));
                        int scorea = Integer.parseInt(eElement.getAttribute("Score"));
                        long user_ida = Long.parseLong(eElement.getAttribute("OwnerUserId"));
                        int commentcounta = Integer.parseInt(eElement.getAttribute("CommentCount"));
                        long parentida = Long.parseLong(eElement.getAttribute("ParentId"));

                        Answer a = new Answer(ida,scorea,user_ida,commentcounta,parentida);
                        hashanswers.put(ida,a);

                        System.out.println("tipo : "
                                + eElement.getAttribute("PostTypeId"));
                        System.out.println("ID : "
                                + eElement.getAttribute("Id"));
                        System.out.println("Score : "
                                + eElement.getAttribute("Score"));
                        System.out.println("OwnerUserId : "
                                + eElement.getAttribute("OwnerUserId"));
                        System.out.println("CommentCount : "
                                + eElement.getAttribute("CommentCount"));
                        System.out.println("ParentId : "
                                + eElement.getAttribute("ParentId"));
                        System.out.println("data : "
                                + eElement.getAttribute("CreationDate"));
                        LocalDate datateste = xmlToDate(eElement.getAttribute("CreationDate"));
                        calendar.addID(datateste.getYear() - 2009, datateste.getMonthValue() - 1, datateste.getDayOfMonth() - 1, Long.parseLong(eElement.getAttribute("Id")));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parsertags(String path, HashMap<Long,Tag> hashtags) {
        try {
            String pathFile = path.concat("/Tags.xml");
            File inputFile = new File(pathFile);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("row");
            System.out.println("\n----------------------------");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                System.out.println("-----------------------");
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    long id = Long.parseLong(eElement.getAttribute("Id"));
                    String tagname = eElement.getAttribute("TagName");

                    Tag t = new Tag(tagname, 0, id);
                    hashtags.put(id,t);

                    System.out.println("ID : "
                            + eElement.getAttribute("Id"));
                    System.out.println("TagName : "
                            + eElement.getAttribute("TagName"));

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}











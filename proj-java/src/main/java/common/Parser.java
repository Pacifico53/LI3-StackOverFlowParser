package common;

import java.io.File;
import java.text.ParseException;
import java.time.LocalDate;
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

    public void parseruser(String path) {
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
    public void parserQuestionsAnswers(String path) {
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
                        System.out.println("DATA :::::::::> " + datateste);
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
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parsertags(String path) {
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











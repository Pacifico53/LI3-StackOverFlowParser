import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;


public class Parser {
    public void parseruser() {
        try {
            File inputFile = new File("input.txt");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("row");
            System.out.println("----------------------------");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                System.out.println("\nCurrent Element :" + nNode.getNodeName());
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
    public void parserquestions() {
        try {
            File inputFile = new File("input.txt");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("row");
            System.out.println("----------------------------");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                System.out.println("\nCurrent Element :" + nNode.getNodeName());
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    if(eElement.getAttribute("PostTypeId") == "1") {
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
                                + eElement.getAttribute("CreationDate"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void parseranswers() {
        try {
            File inputFile = new File("input.txt");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("row");
            System.out.println("----------------------------");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                System.out.println("\nCurrent Element :" + nNode.getNodeName());
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    if(eElement.getAttribute("PostTypeId") == "2") {
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
    public void parsertags() {
        try {
            File inputFile = new File("input.txt");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("row");
            System.out.println("----------------------------");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                System.out.println("\nCurrent Element :" + nNode.getNodeName());
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











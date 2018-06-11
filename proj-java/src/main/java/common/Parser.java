package common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.HashMap;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 * Classe utilizada para fazer parse dos ficheiros dump, isto é utilizado no "load", e vai encher as estruturas com os dados do dump
 */
public class Parser {

    /**
     * Funcao utilizada para converter uma String que contem uma data, como
     * aparece no dump, para uma LocalDate
     *
     * @param date Uma data em formato string, por exemplo "2016-07-20T22:55:21.177"
     * @return A mesma data no formato LocalDate
     */
    private LocalDate xmlToDate(String date) {
        LocalDate date1;
        int year;
        int month;
        int day;
        date = date.substring(0, 10);                       //na variavel date vamos guardar a informação de uma data
        year = Integer.parseInt(date.substring(0, 4));      //na variavel year vamos guardar a informação do ano dessa data
        month = Integer.parseInt(date.substring(5, 7));     //na variavel month vamos guardar a informação do mês dessa data
        day = Integer.parseInt(date.substring(8, 10));      //na variavel day vamos guardar a informação do dia dessa data

        date1 = LocalDate.of(year, month, day);             //criar uma data do tipo LocalDate com as informações que retiramos anteriormente
        return date1;
    }

    /**
     * Metodo para percorrer o ficheiro Users.xml e encher a HashMap de users com os dados necessarios
     *
     * @param path      Path para a pasta que contem o dump
     * @param hashUsers HashMap dos users
     */
    public void parseruser(String path, HashMap<Long, User> hashUsers) {
        try {
            String aboutme = null;
            String name = null;
            long id = 0;
            int rep = 0;
            String pathFile = path.concat("/Users.xml");
            System.out.println("----------PARSE USERS----------");
            User u = null;

            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLEventReader evReader = factory.createXMLEventReader(new FileInputStream(pathFile));

            while (evReader.hasNext()) {
                XMLEvent xmlEvent = evReader.nextEvent();
                if (xmlEvent.isStartElement()) {
                    StartElement startElement = xmlEvent.asStartElement();

                    if (startElement.getName().getLocalPart().equals("row")) {
                        Attribute attrID = startElement.getAttributeByName(new QName("Id"));
                        if(attrID != null) id = Long.parseLong(attrID.getValue());
                        //System.out.print("ID = " + id);

                        Attribute attrRep = startElement.getAttributeByName(new QName("Reputation"));
                        if(attrRep != null) rep = Integer.parseInt(attrRep.getValue());
                        //System.out.print("REP = " + rep);

                        Attribute attrAbMe = startElement.getAttributeByName(new QName("AboutMe"));
                        if(attrAbMe != null) aboutme = attrAbMe.getValue();
                        //System.out.print("ABOUTME = " + aboutme);

                        Attribute attrName = startElement.getAttributeByName(new QName("DisplayName"));
                        if(attrName != null) name = attrName.getValue();
                        //System.out.print("name = " + name);
                        u = new User(id, name, aboutme, rep, 0);
                    }
                }
                if (xmlEvent.isEndElement()) {
                    EndElement endElement = xmlEvent.asEndElement();
                    if (endElement.getName().getLocalPart().equals("row")) {
                        hashUsers.put(id, u);
                    }
                }
            }

        } catch (FileNotFoundException | XMLStreamException e) {
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
            String title = null;
            String  tags = null;
            long id = 0;
            int score = 0;
            int numberAns = 0;
            int commentCount = 0;
            long user_id = 0;
            long parent_id = 0;
            String dateXML = null;
            LocalDate date;

            String pathFile = path.concat("/Posts.xml");
            System.out.println("----------PARSE POSTS----------");

            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLEventReader evReader = factory.createXMLEventReader(new FileInputStream(pathFile));

            while (evReader.hasNext()) {
                Question q;
                Answer a = null;

                XMLEvent xmlEvent = evReader.nextEvent();
                if (xmlEvent.isStartElement()) {
                    StartElement startElement = xmlEvent.asStartElement();

                    if (startElement.getName().getLocalPart().equals("row")) {
                        Attribute attrDate = startElement.getAttributeByName(new QName("CreationDate"));
                        if(attrDate != null) dateXML = attrDate.getValue();

                        Attribute attrID = startElement.getAttributeByName(new QName("Id"));
                        if(attrID != null) id = Long.parseLong(attrID.getValue());

                        Attribute attrScore = startElement.getAttributeByName(new QName("Score"));
                        if(attrScore != null) score = Integer.parseInt(attrScore.getValue());

                        Attribute attrCommCount = startElement.getAttributeByName(new QName("CommentCount"));
                        if(attrCommCount != null) commentCount = Integer.parseInt(attrCommCount.getValue());

                        Attribute attrUserId = startElement.getAttributeByName(new QName("OwnerUserId"));
                        if(attrUserId != null) user_id = Integer.parseInt(attrUserId.getValue());

                        if (startElement.getAttributeByName(new QName("PostTypeId")).getValue().equals("2")){
                            Attribute attrParentId = startElement.getAttributeByName(new QName("ParentId"));
                            if(attrParentId != null) parent_id = Long.parseLong(attrParentId.getValue());

                            a = new Answer(id, score, user_id, commentCount, parent_id);
                            hashAnswers.put(id,a);
                        }

                        if (startElement.getAttributeByName(new QName("PostTypeId")).getValue().equals("1")){
                            Attribute attrTitle = startElement.getAttributeByName(new QName("Title"));
                            if(attrTitle != null) title = attrTitle.getValue();

                            Attribute attrTags = startElement.getAttributeByName(new QName("Tags"));
                            if(attrTags != null) tags = attrTags.getValue();

                            Attribute attrNumAns = startElement.getAttributeByName(new QName("AnswerCount"));
                            if(attrNumAns != null) numberAns = Integer.parseInt(attrNumAns.getValue());

                            q = new Question(id, score, user_id, title, commentCount, tags, numberAns);
                            hashQuestions.put(id,q);
                        }
                    }
                }
                if (xmlEvent.isEndElement()) {
                    EndElement endElement = xmlEvent.asEndElement();
                    if (endElement.getName().getLocalPart().equals("row")) {
                        date = xmlToDate(dateXML);
                        calendar.addID(date.getYear() - 2009, date.getMonthValue() - 1, date.getDayOfMonth() - 1, id);
                    }
                }
            }

        } catch (FileNotFoundException | XMLStreamException e) {
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
            String tagname = null;
            long id = 0;
            String pathFile = path.concat("/Tags.xml");
            System.out.println("----------PARSE TAGS----------");
            Tag t = null;

            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLEventReader evReader = factory.createXMLEventReader(new FileInputStream(pathFile));

            while (evReader.hasNext()) {
                XMLEvent xmlEvent = evReader.nextEvent();
                if (xmlEvent.isStartElement()) {
                    StartElement startElement = xmlEvent.asStartElement();

                    if (startElement.getName().getLocalPart().equals("row")) {
                        Attribute attrID = startElement.getAttributeByName(new QName("Id"));
                        if(attrID != null) id = Long.parseLong(attrID.getValue());

                        Attribute attrName = startElement.getAttributeByName(new QName("TagName"));
                        if(attrName != null) tagname = attrName.getValue();
                        t = new Tag(tagname, 0, id);
                    }
                }
                if (xmlEvent.isEndElement()) {
                    EndElement endElement = xmlEvent.asEndElement();
                    if (endElement.getName().getLocalPart().equals("row")) {
                        hashTags.put(id, t);
                    }
                }
            }

        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }
    }
}











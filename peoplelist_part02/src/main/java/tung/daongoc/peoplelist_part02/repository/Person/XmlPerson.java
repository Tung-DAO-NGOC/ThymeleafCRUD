package tung.daongoc.peoplelist_part02.repository.Person;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import tung.daongoc.peoplelist_part02.model.Person;
import tung.daongoc.peoplelist_part02.repository.PersonDAO;

@Component("xml")
public class XmlPerson extends PersonDAO {

    private final String PATH = "classpath:static/People.xml";

    public XmlPerson() {
        try {
            File file = ResourceUtils.getFile(PATH);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            NodeList nodeList = doc.getElementsByTagName("person");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    Person person = new Person();
                    person.setId(Integer.parseInt(element.getElementsByTagName("id").item(0).getTextContent()));
                    person.setName(element.getElementsByTagName("name").item(0).getTextContent());
                    person.setJob(element.getElementsByTagName("job").item(0).getTextContent());
                    person.setEmail(element.getElementsByTagName("email").item(0).getTextContent());
                    person.setGender(element.getElementsByTagName("gender").item(0).getTextContent());
                    listObject.add(person);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

package utilities;

import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class XmlUtil {
    DocumentBuilderFactory documentBuilderFactory;
    DocumentBuilder documentBuilder;

    public XmlUtil() {
        documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public String getDocument(String fileName, String tagName) {
        try {
            String filePath = System.getProperty("user.dir") + "/src/test/resources/data/xml/" + fileName;
            Document document = documentBuilder.parse(new File(filePath));
            document.getDocumentElement().normalize();
            document.getElementsByTagName(tagName);

            NodeList nodeList = document.getElementsByTagName(tagName);
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    System.out.println("element name : " + element.getAttribute("name"));

                    NodeList elementList = node.getChildNodes();
                    for (int j = 0; j < elementList.getLength(); j++) {
                        Node elementNode = elementList.item(j);
                        if (elementNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element elementDetails = (Element) elementNode;
                            System.out.println("  " + elementDetails.getTagName() +"    "+ elementDetails.getAttribute("value"));
                        }
                    }
                }
            }

        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "";
    }

    public String getElement() {

        return "";
    }

    @Test
    public void testXml(){
        getDocument("testdata.xml","laptop");

    }
}

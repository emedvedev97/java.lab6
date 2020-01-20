/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.medvedev.labxml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author emedvedev
 */
public class XmlDOM {
    
    private static final String NEW_PATH_XML = "src/main/resources/newWorld.xml";
    
    Logger logger = LogManager.getLogger();
    
    
    public void run(org.w3c.dom.Document document) throws IOException, Exception {
        logger.debug("XML DOM запуск");
        org.w3c.dom.Element root = document.getDocumentElement();
        System.out.println(viewXML(root));
        writeXML(document);
        logger.debug("XML DOM завершние");
    }
        
    
    public org.w3c.dom.Document init(File fXml) throws SAXException, IOException, Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringElementContentWhitespace(true);
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        org.w3c.dom.Document document = builder.parse(fXml);
        return document;
    }
    
    public boolean checkXMLforXSD(String pathXml, String pathXsd)
            throws Exception {

        try {
            File xml = new File(pathXml);
            File xsd = new File(pathXsd);

            if (!xml.exists()) {
                logger.debug("Не найден XML " + pathXml);
            }

            if (!xsd.exists()) {
                logger.debug("Не найден XSD " + pathXsd);
            }

            if (!xml.exists() || !xsd.exists()) {
                return false;
            }

            SchemaFactory factory = SchemaFactory
                    .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new StreamSource(pathXsd));
            javax.xml.validation.Validator validator = schema.newValidator();
            validator.validate(new StreamSource(pathXml));
            logger.debug("XML соответствует схеме");
            return true;
        } catch (SAXException e) {
            logger.debug("XML не соответствует схеме " + e);
            return false;
        }

    }
    
    private void writeXML(org.w3c.dom.Document document) {
        try {
            Source source = new DOMSource(document);
            File xmlFile = new File(NEW_PATH_XML);
            StreamResult result = new StreamResult(new OutputStreamWriter(
                    new FileOutputStream(xmlFile), "UTF-8"));
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, result);
            logger.debug("XML DOM записан");
        } catch (FileNotFoundException | UnsupportedEncodingException | IllegalArgumentException | TransformerException e) {
            logger.debug("XML DOM не записан");
        }
    }
    
    
    private StringBuilder viewXML(Element root) {
        StringBuilder sb = new StringBuilder();

        NodeList elements = root.getChildNodes();

        sb.append(viewXMLtree(elements,new StringBuilder()));

        return sb;
    }

    private StringBuilder viewXMLtree(NodeList nodeList, StringBuilder space) {
        StringBuilder sb = new StringBuilder();
        sb.append(space);

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            NodeList node1 = node.getChildNodes();

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                sb.append(node.getNodeName());
                if (node1.getLength() == 1) {
                    sb.append(", Data: ");
                    sb.append(node.getTextContent());
                }

                NamedNodeMap nnm = node.getAttributes();

                if (nnm.getLength() > 0) {
                    sb.append(", Attributes:");
                }

                for (int j = 0; j < nnm.getLength(); j++) {
                    sb.append(" ");
                    sb.append(nnm.item(j));
                }

                sb.append("\n");
            }
            sb.append(viewXMLtree(node1,space));
        }

        return sb;
    }
}

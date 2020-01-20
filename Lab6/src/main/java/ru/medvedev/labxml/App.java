/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.medvedev.labxml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import ru.medvedev.labxml.pojo.Planet;

/**
 *
 * @author emedvedev elc (логирование) + графина (монитоиинг)
 */
public class App {

    /**
     * @param args the command line arguments
     * @throws javax.xml.parsers.ParserConfigurationException
     * @throws org.xml.sax.SAXException
     * @throws java.io.IOException
     */
    static Logger logger = LogManager.getLogger();
    private static final String PATH_XML = "src/main/resources/world.xml";
    private static final String PATH_XSD = "src/main/resources/world.xsd";

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, Exception {
        logger.debug("Загрузка файла XML");
        File fXml = new File(PATH_XML);
        XmlDOM xmld = new XmlDOM();
        boolean b = xmld.checkXMLforXSD(PATH_XML, PATH_XSD);
        if (b) {
            XmlXPath xpth = new XmlXPath();
            XmlSAX xsx = new XmlSAX();
            XmlJAXB xjb = new XmlJAXB();

            org.w3c.dom.Document document = xmld.init(fXml);
            xmld.run(document);
            xpth.run(document);
            xsx.run(fXml);
            xjb.run(fXml);
        }
    }
}

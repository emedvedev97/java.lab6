/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.medvedev.labxml;

import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import ru.medvedev.labxml.pojo.ContinentType;
import ru.medvedev.labxml.pojo.CountryType;
import ru.medvedev.labxml.pojo.Planet;

/**
 *
 * @author emedvedev
 */
public class XmlSAX {

    Logger logger = LogManager.getLogger();

    public void run(File fXml) {
        List<Planet> lp = parse(fXml);
        logger.debug("Вывод XML SAX");
        System.out.print(saxView(lp));
    }

    private StringBuilder saxView(List<Planet> lp) {
        StringBuilder sb = new StringBuilder();

        for (Planet p : lp) {
            sb.append("Planet name:").append(p.getName()).append("\n");
            sb.append("Planet area:").append(p.getArea()).append("\n");
            List<ContinentType> lct = p.getContinents();
            if (lct != null) {
                for (ContinentType ct : lct) {
                    sb.append(" ");
                    sb.append("Continent name:").append(ct.getName()).append("\n");
                    sb.append(" ");
                    sb.append("Continent area:").append(ct.getArea()).append("\n");
                    List<CountryType> lcy = ct.getCountries();
                    if (lcy != null) {;
                        for (CountryType cy : lcy) {
                            sb.append("     ");
                            sb.append("Country name:").append(cy.getName()).append("\n");
                            sb.append("     ");
                            sb.append("Country area:").append(cy.getArea()).append("\n");
                        }
                    }
                }
            }
        }

        return sb;
    }

    private List<Planet> parse(File fXml) {
        List<Planet> lp = null;

        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        try {
            logger.debug("Парсинг XML SAX");
            SAXParser saxParser = saxParserFactory.newSAXParser();
            SAXHandler handler = new SAXHandler();
            saxParser.parse(fXml, handler);
            lp = handler.getCustList();
        } catch (ParserConfigurationException | SAXException | IOException e) {
        }

        return lp;
    }
}

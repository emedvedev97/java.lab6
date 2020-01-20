/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.medvedev.labxml;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.NodeList;

/**
 *
 * @author emedvedev
 */
public class XmlXPath {

    Logger logger = LogManager.getLogger();

    public void run(org.w3c.dom.Document document) throws XPathExpressionException {
        NodeList nodes = read(document);
        logger.debug("Вывод XML XPATH: страны, чья площадь больше 2млн км^2");
        System.out.println(xpathView(nodes));
        logger.debug("XML XPATH завершние");
    }

    private NodeList read(org.w3c.dom.Document document) throws XPathExpressionException {
        logger.debug("Парсинг XML XPATH");
        XPathFactory xfactory = XPathFactory.newInstance();
        XPath xpath = xfactory.newXPath();
        XPathExpression expr = xpath.compile("/planet/continent/country[area>2.00]/name/text()");
        Object result = expr.evaluate(document, XPathConstants.NODESET);
        NodeList nodes = (NodeList) result;
        return nodes;
    }

    private StringBuilder xpathView(NodeList nodes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nodes.getLength(); i++) {
            sb.append(nodes.item(i).getNodeValue()).append("\n");
        }
        return sb;
    }
}

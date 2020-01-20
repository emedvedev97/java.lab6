/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.medvedev.labxml;

import https.rsatu_ru.continents.Planet;
import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author emedvedev
 */
public class XmlJAXB {

    Logger logger = LogManager.getLogger();

    private static final String NEW_PATH_XML = "src/main/resources/newWorld2.xml";

    public void run(File fXml) throws JAXBException {
        Planet planet = read(fXml);
        planet.setName("Луна");
        write(planet);
    }

    private Planet read(File fXml) throws JAXBException {
        logger.debug("Счтывание XML JAXB");
        JAXBContext jaxbContext = JAXBContext.newInstance(Planet.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        Planet planet = (Planet) jaxbUnmarshaller.unmarshal(fXml);
        return planet;
    }

    private void write(Object o) {
        try {
            logger.debug("Подготовка к записи XML JAXB");
            File file = new File(NEW_PATH_XML);
            JAXBContext jaxbContext = JAXBContext.newInstance(Planet.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            logger.debug("Запись XML JAXB");
            jaxbMarshaller.marshal(o, file);
            logger.debug("Вывод записанного XML JAXB");
            jaxbMarshaller.marshal(o, System.out);
        } catch (JAXBException e) {
        }
    }
}
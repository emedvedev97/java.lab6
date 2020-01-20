/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.medvedev.labxml;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import ru.medvedev.labxml.pojo.ContinentType;
import ru.medvedev.labxml.pojo.CountryType;
import ru.medvedev.labxml.pojo.Planet;

/**
 *
 * @author emedvedev
 */
public class SAXHandler extends DefaultHandler {

    private StringBuilder data;
    private List<Planet> planetList;
    private List<ContinentType> continenList;
    private List<CountryType> countryList;
    private CountryType country;
    private ContinentType continen;
    private Planet planet;
    private String name;
    private Float area;

    boolean bName = false;
    boolean bArea = false;

    public List<Planet> getCustList() {
        return planetList;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        if (qName.equalsIgnoreCase("planet")) {
            planet = new Planet();
            if (planetList == null) {
                planetList = new ArrayList<>();
            }
        } else if (qName.equalsIgnoreCase("continent")) {
            continen = new ContinentType();
            if (continenList == null) {
                continenList = new ArrayList<>();
            }
        } else if (qName.equalsIgnoreCase("country")) {
            country = new CountryType();
            if (countryList == null) {
                countryList = new ArrayList<>();
            }
        } else if (qName.equalsIgnoreCase("name")) {
            bName = true;
        } else if (qName.equalsIgnoreCase("area")) {
            bArea = true;
        }

        data = new StringBuilder();
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (bName) {
            name = data.toString();
            if (continen != null) {
                if (country != null) {
                    country.setName(name);
                } else {
                    continen.setName(name);
                }
            } else {
                planet.setName(name);
            }
            bName = false;
        } else if (bArea) {
            area = Float.parseFloat(data.toString());
            if (continen != null) {
                if (country != null) {
                    country.setArea(area);
                } else {
                    continen.setArea(area);
                }
            } else {
                planet.setArea(area);
            }
            bArea = false;
        }

        if (qName.equalsIgnoreCase("planet")) {
            planet.setContinents(continenList);
            planetList.add(planet);
            planet = null;
            countryList = null;
        }

        if (qName.equalsIgnoreCase("continent")) {
            continen.setCountries(countryList);
            continenList.add(continen);
            continen = null;
            countryList = null;
        }

        if (qName.equalsIgnoreCase("country")) {
            countryList.add(country);
            country = null;
        }
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        data.append(new String(ch, start, length));
    }
}

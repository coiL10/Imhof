package ch.epfl.imhof.osm;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import ch.epfl.imhof.PointGeo;
import ch.epfl.imhof.osm.OSMRelation.Member;
import ch.epfl.imhof.osm.OSMRelation.Member.Type;

/**
 * Classe permettant de construire une carte OpenStreetMap à partir de données
 * stockées dans un fichier au format OSM.
 * 
 * 
 * 
 * @author Loïc Nguyen (238243)
 * @author Karine Perrard (250605)
 */
public final class OSMMapReader {
    private OSMMapReader() {
    }

    /**
     * Lit la carte OSM entrée en argument, en le décompressant en gzip si le
     * boolean entré en argument est vrai.
     * 
     * @param fileName
     *            nom du fichier
     * @param unGZip
     *            boolean vérifiant si le fichier a besoin d'être dézippé
     * @return retourne une carte OSM avec les données correspondant au fichier
     *         entré en paramètre
     * @throws IOException
     *             en cas d'erreur d'entrée/sortie
     * @throws SAXException
     *             en cas d'erreur dans le format du fichier
     */
    public static OSMMap readOSMFile(String fileName, boolean unGZip)
            throws IOException, SAXException {
        try (InputStream s = (unGZip) ? new GZIPInputStream(
                new BufferedInputStream(new FileInputStream(fileName)))
                : new BufferedInputStream(new FileInputStream(fileName))) {

            OSMMap.Builder newOSMMap = new OSMMap.Builder(); // crée le
                                                             // batisseur pour
                                                             // la carte à
                                                             // retourner
            XMLReader r = XMLReaderFactory.createXMLReader();
            r.setContentHandler(new DefaultHandler() {

                OSMWay.Builder newOSMWay;
                OSMRelation.Builder newOSMRelation;
                boolean way = false; // boolean qui permet de déterminer
                                     // l'entitée parente du tag (attributes)
                boolean relation = false;

                /*
                 * (non-Javadoc)
                 * 
                 * @see
                 * org.xml.sax.helpers.DefaultHandler#startElement(java.lang
                 * .String, java.lang.String, java.lang.String,
                 * org.xml.sax.Attributes)
                 */
                @Override
                public void startElement(String uri, String lName,
                        String qName, Attributes atts) throws SAXException {
                    switch (qName) { // le switch va permettre de différentier
                                     // les opération à faire selon le type
                                     // d'élément.
                    case "node":
                        PointGeo position = new PointGeo(Math.toRadians(Double
                                .parseDouble(atts.getValue("lon"))), Math
                                .toRadians(Double.parseDouble(atts
                                        .getValue("lat"))));
                        OSMNode.Builder newNode = new OSMNode.Builder(Long
                                .parseLong(atts.getValue("id"), 10), position);
                        newOSMMap.addNode(newNode.build());
                        break;
                    case "way":
                        way = true;
                        newOSMWay = new OSMWay.Builder(Long.parseLong(
                                atts.getValue("id"), 10));
                        break;
                    case "nd":
                        OSMNode node = newOSMMap.nodeForId(Long.parseLong(
                                atts.getValue("ref"), 10));
                        if (node == null) {
                            newOSMWay.setIncomplete();
                            break;
                        } else {
                            newOSMWay.addNode(node);
                            break;

                        }

                    case "tag":
                        if (way) {
                            newOSMWay.setAttribute(atts.getValue("k"),
                                    atts.getValue("v"));
                        } else if (relation) {
                            newOSMRelation.setAttribute(atts.getValue("k"),
                                    atts.getValue("v"));
                        }
                        break;

                    case "relation":
                        relation = true;
                        newOSMRelation = new OSMRelation.Builder(Long
                                .parseLong(atts.getValue("id"), 10));
                        break;

                    case "member":
                        if (newOSMRelation.isIncomplete()) {
                            break;
                        }
                        Member.Type type = null;
                        OSMEntity newMember = null;
                        switch (atts.getValue("type")) {
                        case "way":
                            type = Type.WAY;
                            newMember = newOSMMap.wayForId(Long.parseLong(
                                    atts.getValue("ref"), 10));
                            break;
                        case "node":
                            type = Type.NODE;
                            newMember = newOSMMap.nodeForId(Long.parseLong(
                                    atts.getValue("ref"), 10));
                            break;
                        case "relation":
                            type = Type.RELATION;
                            newMember = newOSMMap.relationForId(Long.parseLong(
                                    atts.getValue("ref"), 10));
                            break;
                        }
                        if (newMember == null) {
                            newOSMRelation.setIncomplete();
                            break;
                        } else {
                            newOSMRelation.addMember(type,
                                    atts.getValue("role"), newMember);
                            break;
                        }

                    default:
                        break;
                    }
                }

                /*
                 * (non-Javadoc)
                 * 
                 * @see
                 * org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String
                 * , java.lang.String, java.lang.String)
                 */
                @Override
                public void endElement(String uri, String lName, String qName)
                        throws SAXException {
                    switch (qName) {
                    case "way":
                        way = false;
                        try {
                            newOSMMap.addWay(newOSMWay.build());
                        } catch (IllegalStateException
                                | IllegalArgumentException e) {

                        }
                        break;
                    case "relation":
                        relation = false;
                        try {
                            newOSMMap.addRelation(newOSMRelation.build());
                        } catch (IllegalStateException e) {

                        }
                        break;

                    default:
                        break;
                    }
                }
            });

            r.parse(new InputSource(s));
            return newOSMMap.build();
        }

    }
}

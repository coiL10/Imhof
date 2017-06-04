package ch.epfl.imhof.painting;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import ch.epfl.imhof.geometry.Polygon;
import ch.epfl.imhof.geometry.PolyLine;
import ch.epfl.imhof.geometry.ClosedPolyLine;
import ch.epfl.imhof.Attributed;
import ch.epfl.imhof.Map;
import ch.epfl.imhof.painting.LineStyle.LineCap;
import ch.epfl.imhof.painting.LineStyle.LineJoin;

/**
 * représente un peintre
 * 
 * @author Karine Perrard (250605)
 * @author Loic Nguyen (238243)
 *
 *
 */
public interface Painter {

    /**
     * dessine la map sur la toile donné
     * 
     * @param map
     *            la map a dessiner
     * @param cv
     *            la toile sur laquelle peindre
     */

    void drawMap(Map map, Canvas cv);

    /**
     * crée un peintre qui dessine à l'intérieur de tous les polygones de la
     * carte qu'il reçoit avec cette couleur
     * 
     * @param color
     *            la couleur avec laquelle remplir les polygones
     * @return le peintre
     */

    static Painter polygon(Color color) {
        return (x, y) -> {
            for (Attributed<Polygon> p : x.polygons()) {
                y.drawPolygon(p.value(), color);
            }
        };

    }

    /**
     * crée un peintre qui dessine toutes les lignes de la carte fournie avec le
     * style de ligne donné
     * 
     * @param linestyle
     *            le style de ligne en LineStyle
     * @return le peintre
     */
    static Painter line(LineStyle linestyle) {
        return (x, y) -> {
            for (Attributed<PolyLine> p : x.polyLines()) {
                y.drawPolyLine(p.value(), linestyle);
            }
        };
    }

    /**
     * crée un peintre qui dessine toutes les lignes de la carte fournie avec le
     * style de ligne donné
     * 
     * @param width
     *            la largeur des lignes
     * @param color
     *            la couleur des lignes
     * @param lc
     *            la terminaison des lignes
     * @param lj
     *            la jointure des segments
     * @param dash
     *            l'alternance des sections opaques et transparantes
     * @return le peintre
     */

    static Painter line(float width, Color color, LineCap lc, LineJoin lj,
            float... dash) {
        return line(new LineStyle(width, color, lc, lj, dash));
    }

    /**
     * crée un peintre qui dessine toutes les lignes de la carte fournie avec le
     * style de ligne donné ayant les valeurs linecap, linejoin et dash par
     * défaut
     * 
     * @param width
     *            la largeur des lignes
     * @param color
     *            la couleur des lignes
     * @return le peintre
     */

    static Painter line(float width, Color color) {
        return line(new LineStyle(width, color));
    }

    /**
     * crée un peintre qui dessine les pourtours de l'enveloppe et des trous de
     * tous les polygones de la carte qu'on lui fournit avec le style de ligne
     * entré en argument
     * 
     * @param linestyle
     *            le style de ligne
     * @return le peintre
     */
    static Painter outline(LineStyle linestyle) {
        return (x, y) -> {
            for (Attributed<Polygon> p : x.polygons()) {
                y.drawPolyLine(p.value().shell(), linestyle);
                for (ClosedPolyLine cp : p.value().holes()) {
                    y.drawPolyLine(cp, linestyle);
                }
            }
        };
    }

    /**
     * crée un peintre qui dessine les pourtours de l'enveloppe et des trous de
     * tous les polygones de la carte qu'on lui fournit avec le style de ligne
     * entré en argument
     * 
     * @param width
     *            la largeur de la ligne
     * @param color
     *            la couleur de la ligne
     * @param lc
     *            la terminaison des lignes
     * @param lj
     *            la jointure des lignes
     * @param dash
     *            l'alternance des sections opaques et transparentes
     * @return le peintre
     */

    static Painter outline(float width, Color color, LineCap lc, LineJoin lj,
            float[] dash) {
        return outline(new LineStyle(width, color, lc, lj, dash));
    }

    /**
     * crée un peintre qui dessine les pourtours de l'enveloppe et des trous de
     * tous les polygones de la carte qu'on lui fournit avec le style de ligne
     * entré en argument et ayant des valeurs par défaut
     * 
     * @param width
     *            la largeur de la ligne
     * @param color
     *            la couleur de la ligne
     * @return le peintre
     */

    static Painter outline(float width, Color color) {
        return outline(new LineStyle(width, color));
    }

    /**
     * crée un peintre qui ne dessine que les entités satisfaisant le prédicet
     * donné
     * 
     * @param pr
     *            le prédicat
     * @return le peintre
     */

    default Painter when(Predicate<Attributed<?>> pr) {
        return (x, y) -> {
            List<Attributed<Polygon>> polygonList = new ArrayList<Attributed<Polygon>>();
            List<Attributed<PolyLine>> polyLineList = new ArrayList<Attributed<PolyLine>>();
            for (Attributed<Polygon> pg : x.polygons()) {
                if (pr.test(pg)) {
                    polygonList.add(pg);
                }
            }
            for (Attributed<PolyLine> pl : x.polyLines()) {
                if (pr.test(pl)) {
                    polyLineList.add(pl);
                }
            }
            Map map = new Map(polyLineList, polygonList);
            drawMap(map, y);

        };

    }

    /**
     * crée un peintre qui dessine sur une même toile d'abord la certe du
     * peintre entré en argument, puis celle de peintre actuel
     * 
     * @param p
     *            l'autre peintre
     * @return le peintre
     */

    default Painter above(Painter p) {
        return (x, y) -> {
            p.drawMap(x, y);
            drawMap(x, y);
        };
    }

    /**
     * crée un peintre qui dessine tous les éléments de la carte couche par
     * couche
     * 
     * @return le peintre
     */

    default Painter layered() {
        Painter p = when(Filters.onLayer(-5)).above(this);
        for (int i = -4; i <= 5; ++i) {
            p = when(Filters.onLayer(i)).above(p);
        }
        return p;
    }

}

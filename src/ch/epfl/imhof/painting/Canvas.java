package ch.epfl.imhof.painting;

import ch.epfl.imhof.geometry.PolyLine;
import ch.epfl.imhof.geometry.Polygon;

/**
 * Interface représentant une toile
 * 
 * @author Loïc Nguyen (238243)
 * @author Karine Perrard (250605)
 */
public interface Canvas {
    /**
     * Permet de dessiner sur la toile une polyligne donnée avec un style de
     * ligne donné
     * 
     * @param polyline
     *            polyligne de type PolyLine donnée
     * @param linestyle
     *            style de ligne de type LineStyle donnée
     */
    void drawPolyLine(PolyLine polyline, LineStyle linestyle);

    /**
     * Permet de dessiner sur une toile un polygone donné avec une couleur
     * donnée
     * 
     * @param polygon
     *            polygone de type Polygon donné
     * @param color
     *            couleur de type Color donné
     */
    void drawPolygon(Polygon polygon, Color color);
}

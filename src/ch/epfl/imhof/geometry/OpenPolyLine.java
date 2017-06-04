package ch.epfl.imhof.geometry;

import java.util.List;

/**
 * représente one polyline ouverte
 * 
 * @author Karine Perrard (250605)
 * @author Loic Nguyen (238243)
 *
 *
 */

public final class OpenPolyLine extends PolyLine {
    /**
     * crée une nouvelle polyline
     * 
     * @param points
     *            liste de points de la polyligne
     */
    public OpenPolyLine(List<Point> points) {
        super(points);
    }

    /**
     * renvoie false car la ligne est ouverte
     */

    @Override
    public boolean isClosed() {
        return false;
    }
}

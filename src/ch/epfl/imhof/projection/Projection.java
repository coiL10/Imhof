package ch.epfl.imhof.projection;

import ch.epfl.imhof.PointGeo;
import ch.epfl.imhof.geometry.Point;

/**
 * Interface contenant les méthodes nécessaires pour une projection
 * 
 * @author Loïc Nguyen (238243)
 * @author Karine Perrard (250605)
 */
public interface Projection {
    /**
     * projette un point en coordonnées géographiques en un point en coordonnées
     * cartésiennes
     * 
     * @param point
     *            Point geographique que l'on veut projeter
     * @return Point le point en coordonnées cartésiennes
     */
    Point project(PointGeo point);

    /**
     * projette un point en coordonnées cartésiennes en un point en coordonnées
     * géographiques
     * 
     * @param point
     *            point en coordonnées cartésiennes que l'on veut projeter
     * @return Point le point en coordonnées géographique
     */
    PointGeo inverse(Point point);
}

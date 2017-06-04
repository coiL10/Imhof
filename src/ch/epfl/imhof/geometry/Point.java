package ch.epfl.imhof.geometry;

import java.util.function.Function;

/**
 * Un point sur le plan en coordonnées cartésiennes
 * 
 * 
 * 
 * @author Loïc Nguyen (238243)
 * @author Karine Perrard (250605)
 */
public final class Point {
    private final double x, y;

    /**
     * Construit un point avec les coordonnées x et y
     * 
     * @param x
     *            la coordonnée x du point
     * @param y
     *            la coordonnée y du point
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Retourne la valeur x du point
     * 
     * @return double qui représente la valeur x du point
     */
    public double x() {
        return x;
    }

    /**
     * Retourne la valeur y du point
     * 
     * @return double qui représente la valeur y du point
     */
    public double y() {
        return y;
    }

    /**
     * crée une fonction représentant un changement de repère
     * 
     * @param p1
     *            le premier point en coordonnées initiales
     * @param p2
     *            le premier point en coordonnées projetées
     * @param q1
     *            le deuxieme point en coordonnées initiales
     * @param q2
     *            le deuxieme point en coordonnées projetées
     * @return
     */

    public static Function<Point, Point> alignedCoordinateChange(Point p1,
            Point p2, Point q1, Point q2) {
        
        if (p1.x == q1.x || p1.y == q1.y || p2.x == q2.x || p2.y == q2.y){
            throw new IllegalArgumentException("Les points du changement de coordonnées sont alignés");
        }
        

        double ax = (p2.x - q2.x) / (p1.x - q1.x);
        double ay = (p2.y - q2.y) / (p1.y - q1.y);

        double bx = p2.x - ax * p1.x;
        double by = p2.y - ay * p1.y;
        
        return p -> new Point(p.x * ax + bx, p.y * ay + by);

    }
}

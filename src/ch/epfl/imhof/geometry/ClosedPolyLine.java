package ch.epfl.imhof.geometry;

import java.util.List;

/**
 * désigne une polyline fermée
 * 
 * @author Karine Perrard (250605)
 * @author Loic Nguyen (238243)
 *
 *
 */

public final class ClosedPolyLine extends PolyLine {
    /**
     * crée une nouvelle polyline
     * 
     * @param points
     */
    public ClosedPolyLine(List<Point> points) {
        super(points);

    }

    /**
     * renvoie true car la polyline est fermée
     */
    @Override
    public boolean isClosed() {

        return true;
    }

    /**
     * permet de calculer l'aire de la polyline fermée
     *
     * @return l'aire de la polyline
     */

    public double area() {
        double somme = 0;
        for (int i = 0; i < points().size(); ++i) {
            // formule donnée dans l'énoncé
            somme += points().get(generalized(i)).x()
                    * (points().get(generalized(i + 1)).y() - points().get(
                            generalized(i - 1)).y());
        }
        return (1.0 / 2.0) * Math.abs(somme);
    }

    /**
     * permet de déterminer si un point donné est a l'intérieur de la polyline
     * 
     * @param p
     *            désigne le point
     * 
     * @return true si le point est a l'interieur, sinon false
     */

    public boolean containsPoint(Point p) {
        int indice = 0;
        for (int i = 0; i < points().size(); ++i) {
            // formules données dans l'énoncé
            if (points().get(generalized(i)).y() <= p.y()) {
                if (((points().get(generalized(i + 1)).y()) > p.y())
                        && (isLeft(p, points().get(generalized(i)),
                                points().get(generalized(i + 1))))) {
                    ++indice;
                }
            } else {
                if (((points().get(generalized(i + 1)).y()) <= p.y())
                        && (isLeft(p, points().get(generalized(i + 1)), points()
                                .get(generalized(i))))) {
                    --indice;
                }
            }
        }

        return (indice != 0);
    }

    /*
     * premet de déterminer si un point donné est a gauche de la ligne reliant
     * deux autres points donnés
     */

    private boolean isLeft(Point p, Point p1, Point p2) {
        return ((p1.x() - p.x()) * (p2.y() - p.y()) > (p2.x() - p.x())
                * (p1.y() - p.y()));
    }

    /**
     * 
     * @param n
     * @return l'indice généralisé d'un nombre donné
     */

    private int generalized(int n) {
        return Math.floorMod(n, points().size());
    }
}

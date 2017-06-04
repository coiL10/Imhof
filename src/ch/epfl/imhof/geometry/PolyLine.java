package ch.epfl.imhof.geometry;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;

/**
 * La classe PolyLine représente une liste de points formant une PolyLine
 * 
 * @author Karine Perrard (250605)
 * @author Loic Nguyen (238243)
 *
 *
 */

public abstract class PolyLine {

    private final List<Point> points;

    /**
     * Construit la liste de points
     * 
     * @param points
     *            désigne la liste de points représentant la PolyLine
     */

    public PolyLine(List<Point> points) {
        if (points.isEmpty()) {
            throw new IllegalArgumentException("La liste entre en argument est vide");
        }

        this.points = Collections
                .unmodifiableList(new ArrayList<Point>(points));
    }

    /**
     * 
     * @return true si la polyligne est fermée et true si elle est ouverte
     */

    public abstract boolean isClosed();

    /**
     * 
     * @return la liste de points
     */

    public List<Point> points() {
        return points;
    }

    /**
     * 
     * @return le premier point de la liste
     */

    public Point firstPoint() {
        return points.get(0);
    }

    /**
     * le batisseur permet de créer la ligne en plusieurs étapes
     * 
     * @author Karine Perrard (250605)
     * @author Loic Nguyen (238243)
     *
     *
     */

    public final static class Builder {

        private List<Point> points;

        /**
         * construit une ligne ne contenant aucun point
         */

        public Builder() {
            this.points = new ArrayList<Point>();
        }

        /**
         * permet d'ajouter des points a la liste
         * 
         * @param newPoint
         */

        public void addPoint(Point newPoint) {
            points.add(newPoint);
        }

        /**
         * Construit une polyligne ouverte a partir des éléments ajoutés
         * 
         * @return une openpolyline construite à partir du batisseur
         */

        public OpenPolyLine buildOpen() {
            return new OpenPolyLine(points);
        }

        /**
         * Construit une polyligne fermée a partir des éléments ajoutés
         * 
         * @return une closedpolyline construite à partir du batisseur
         */

        public ClosedPolyLine buildClosed() {
            return new ClosedPolyLine(points);
        }
    }
}

package ch.epfl.imhof;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ch.epfl.imhof.geometry.PolyLine;
import ch.epfl.imhof.geometry.Polygon;

/**
 * la classe map représente une carte projetée, composée d'entités géométriques
 * attribuées
 * 
 * @author Karine Perrard (250605)
 * @author Loic Nguyen (238243)
 *
 *
 */

public final class Map {
    final private List<Attributed<PolyLine>> polylines;
    final private List<Attributed<Polygon>> polygons;

    /**
     * construit une carte à partir des listes de polylignes et polygones
     * entrées en argument
     * 
     * @param polyLines
     *            liste de polylignes, attaché a des attributs que l'on veut
     *            ajouter à la Map
     * @param polygons
     *            liste de polygones, attaché a des attributs que l'on veut
     *            ajouter à la Map
     */

    public Map(List<Attributed<PolyLine>> polyLines,
            List<Attributed<Polygon>> polygons) {
        this.polylines = Collections
                .unmodifiableList(new ArrayList<Attributed<PolyLine>>(polyLines));
        this.polygons = Collections
                .unmodifiableList(new ArrayList<Attributed<Polygon>>(polygons));

    }

    /**
     * Retourne la liste des polylignes attribuées a la carte
     * 
     * @return Liste de polylignes qui sont attribuées à la carte
     */

    public List<Attributed<PolyLine>> polyLines() {
        return polylines;
    }

    /**
     * Retourne la liste des polygones attribués de la carte
     * 
     * @return Liste de polygones qui sont attribuées à la carte
     */

    public List<Attributed<Polygon>> polygons() {
        return polygons;
    }

    /**
     * batisseur qui permet de construire une carte en plusieurs étapes
     * 
     * @author Karine Perrard (250605)
     * @author Loic Nguyen (238243)
     *
     *
     */

    public static final class Builder {
        private List<Attributed<PolyLine>> polylines;
        private List<Attributed<Polygon>> polygons;

        /**
         * initialise les listes de polylignes et de polygones
         */

        public Builder() {
            this.polylines = new ArrayList<Attributed<PolyLine>>();
            this.polygons = new ArrayList<Attributed<Polygon>>();
        }

        /**
         * permet d'ajouter la polyligne entrée en argument au batisseur
         * 
         * @param newPolyLine
         *            nouvelle polyligne à ajouter
         */

        public void addPolyLine(Attributed<PolyLine> newPolyLine) {
            polylines.add(newPolyLine);
        }

        /**
         * permet d'ajouter le polygone entré en argument au batisseur
         * 
         * @param newPolygon
         *            nouveau polygone à ajouter
         */

        public void addPolygon(Attributed<Polygon> newPolygon) {
            polygons.add(newPolygon);
        }

        /**
         * construit une carte ayant les mêmes polylignes et polygones que le
         * batisseur
         * 
         * @return retourne une Map à partir des entitées ajoutées jusqu'à présent
         * 
         */

        public Map build() {
            return new Map(polylines, polygons);
        }
    }

}

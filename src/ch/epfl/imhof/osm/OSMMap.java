package ch.epfl.imhof.osm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe représentant une carte OpenStreetMap
 * 
 * 
 * @author Loïc Nguyen (238243)
 * @author Karine Perrard (250605)
 */
public final class OSMMap {
    private final List<OSMWay> ways;
    private final List<OSMRelation> relations;

    /**
     * Construit une carte OSM en prenant les chemins et les relations donnés
     * 
     * @param ways
     *            liste de chemin donné
     * @param relations
     *            liste re relations donnés
     */
    public OSMMap(Collection<OSMWay> ways, Collection<OSMRelation> relations) {
        this.ways = Collections.unmodifiableList(new ArrayList<OSMWay>(ways));
        this.relations = Collections
                .unmodifiableList(new ArrayList<OSMRelation>(relations));
    }

    /**
     * retourne la liste des chemins de la carte
     * 
     * @return une liste de noeuds associés a la carte
     */
    public List<OSMWay> ways() {
        return ways;
    }

    /**
     * retourne la liste des relations de la carte
     * 
     * @return une liste des relations associées a la carte
     */
    public List<OSMRelation> relations() {
        return relations;
    }

    /**
     * Batisseur de la classe OSMMap qui permet de construire progressivement la
     * carte
     * 
     * @author Loïc Nguyen (238243)
     * @author Karine Perrard (250605)
     */
    public static final class Builder {
        private final Map<Long, OSMNode> nodes;
        private final Map<Long, OSMWay> ways;
        private final Map<Long, OSMRelation> relations;

        /**
         * Constructeur par défaut du batisseur, initialise les liste.
         * 
         */
        public Builder() {
            nodes = new HashMap<Long, OSMNode>();
            ways = new HashMap<Long, OSMWay>();
            relations = new HashMap<Long, OSMRelation>();
        }

        /**
         * Ajoute le noeud donné au batisseur
         * 
         * @param newNode
         *            le noeud donné
         */
        public void addNode(OSMNode newNode) {
            nodes.put(newNode.id(), newNode);
        }

        /**
         * Retourne le noeud dont l'identifiant unique est égal à celui donné,
         * ou null si le noeud n'a pas déjà été ajouté au batisseur
         * 
         * @param id
         *            l'identifiant unique
         * @return le noeud correspondant
         */
        public OSMNode nodeForId(long id) {
            return nodes.get(id);
        }

        /**
         * Ajoute le chemin donné à la carte en construction
         * 
         * @param newWay
         *            le nouveau chemin
         */
        public void addWay(OSMWay newWay) {
            ways.put(newWay.id(), newWay);
        }

        /**
         * Retourn le chemin dont l'identifiant unique est égal à celui donné,
         * ou null si le chemin n'a pas été ajouté
         * 
         * @param id
         *            l'identifiant unique
         * @return le chemin correspondant
         */
        public OSMWay wayForId(long id) {
            return ways.get(id);
        }

        /**
         * Ajoute la relation donnée à la carte en cours de construction
         * 
         * @param newRelation
         *            la nouvelle relation à ajouter
         * 
         */
        public void addRelation(OSMRelation newRelation) {
            relations.put(newRelation.id(), newRelation);
        }

        /**
         * Retourne la relation dont l'identifiant unique correspond à celui
         * donné, ou null si cette relation n'a pas été ajoutée
         * 
         * @param id
         *            l'identifiant unique de la relation voulue
         * @return retourne la relation correspondante à l'identifiant
         */
        public OSMRelation relationForId(long id) {
            return relations.get(id);
        }

        /**
         * Construit une carte OSM avec les chemins et les relations ajoutés
         * 
         * @return
         */
        public OSMMap build() {
            return new OSMMap(ways.values(), relations.values());
        }

    }

}

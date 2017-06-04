package ch.epfl.imhof.osm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ch.epfl.imhof.Attributes;

/**
 * Classe repreésentant un chemin OSM. Hérite de OSMEntity
 * 
 * 
 * 
 * @author Loïc Nguyen (238243)
 * @author Karine Perrard (250605)
 */
public final class OSMWay extends OSMEntity {
    private final List<OSMNode> nodes;

    /**
     * Construit un chemin étant donné son identifiant unique, ses noeuds et ses
     * attributs. Lève l'exception IllegalArgumentException si la liste de
     * noeuds a moins de deux elements
     * 
     * @param id
     *            l'identifiant unique
     * @param nodes
     *            ses noeuds
     * @param attributes
     *            ses attributs
     */
    public OSMWay(long id, List<OSMNode> nodes, Attributes attributes) {
        super(id, attributes);
        if (nodes.size() < 2) {
            throw new IllegalArgumentException("il faut au moins 2 noeuds");
        }
        this.nodes = Collections
                .unmodifiableList(new ArrayList<OSMNode>(nodes));
    }

    /**
     * retourne le nombre de noeuds du chemin
     * 
     * @return le nombre de noeuds du chemin
     */
    public int nodesCount() {
        return nodes.size();
    }

    /**
     * retourne la liste des noeuds du chemin
     * 
     * @return la liste des noeuds du chemin
     */

    public List<OSMNode> nodes() {
        return nodes;
    }

    /**
     * retourne la liste des noeuds du chemin sans le dernier si celui-ci est
     * identique au premier
     * 
     * @return la liste des points sans répétition
     */
    public List<OSMNode> nonRepeatingNodes() {

        List<OSMNode> n = new ArrayList<OSMNode>(nodes);
        if (n.get(0).equals(n.get(n.size() - 1))) {
            n.remove(n.size() - 1);
        }
        return n;
    }

    /**
     * retourne le premier noeud du chemin
     * 
     * @return le premier noeud du chemin
     */
    public OSMNode firstNode() {
        return nodes.get(0);
    }

    /**
     * retourne le dernier noeud du chemin
     * 
     * @return le dernier noeud du chemin
     */
    public OSMNode lastNode() {
        return nodes.get(nodes.size() - 1);
    }

    /**
     * retourne vrai si et seulement si le chemin est fermé
     * 
     * @return true si le chemin est fermé
     */
    public boolean isClosed() {
        return firstNode().equals(lastNode());
    }

    /**
     * Batisseur de la classe OSMWay, hérite du batisseur de OSMEntity
     * 
     * @author Loïc Nguyen (238243)
     * @author Karine Perrard (250605)
     */
    public final static class Builder extends OSMEntity.Builder {
        private List<OSMNode> nodes;

        /**
         * Construit un batisseur pour un chemin ayant l'identifiant donné
         * 
         * @param id
         *            l'identifiant unique
         */
        public Builder(long id) {
            super(id);
            nodes = new ArrayList<OSMNode>();
        }

        /**
         * Ajoute un noeud à (la fin) des noeuds du chemin en cours de
         * construction
         * 
         * @param newNode
         *            le noeud a ajouter
         */
        public void addNode(OSMNode newNode) {
            nodes.add(newNode);
        }

        /*
         * (non-Javadoc)
         * 
         * @see ch.epfl.imhof.osm.OSMEntity.Builder#isIncomplete()
         */
        @Override
        public boolean isIncomplete() {
            if (nodes.size() < 2) {
                return true;
            } else {
                return super.isIncomplete();
            }
        }

        /**
         * construit et retourne le chemin ayant les noeuds et les attributs
         * ajoutés jusqu'a présent. Lève l'exception IllegalStateException si le
         * chemin en cours de construction est imcomplet
         * 
         * @return le chemin construit
         */
        public OSMWay build() {
            if (isIncomplete()) {
                throw new IllegalStateException("Le chemin n'est pas fini");
            }
            return new OSMWay(getid(), nodes, getAttributes().build());
        }

    }

}

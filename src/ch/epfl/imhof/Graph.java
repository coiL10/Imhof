package ch.epfl.imhof;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * représente un graphe non orienté
 * 
 * 
 * @author Karine Perrard (250605)
 * @author Loic Nguyen (238243)
 *
 *
 */

public final class Graph<N> {

    /**
     * représente une liste associant un noeud a tous ses voisins
     */
    private final Map<N, Set<N>> neighbors;

    /**
     * construit un graphe
     * 
     * @param neighbors
     *            est la liste des noeuds et de leurs voisins
     */

    public Graph(Map<N, Set<N>> neighbors) {
        Map<N, Set<N>> copy = new HashMap<>();
        for (N n : neighbors.keySet()) {
            copy.put(n, Collections.unmodifiableSet(new HashSet<>(neighbors
                    .get(n))));
        }
        this.neighbors = Collections.unmodifiableMap(copy);

    }

    /**
     * Retourne l'ensemble des noeuds représentés dans la liste
     * 
     * @return un Set contenant les noeuds représentés
     */

    public Set<N> nodes() {
        return neighbors.keySet();
    }

    /**
     * Retourne l'ensemble des noeuds voisins au noeud entré en paramètre, lance
     * une exception si le noeud n'existe pas
     * 
     * @param node
     *            le noeud dont on veut savoir les voisins
     * @return un Set contenant l'ensemble des noeuds voisins
     */

    public Set<N> neighborsOf(N node) {
        if (!neighbors.containsKey(node)) {
            throw new IllegalArgumentException(
                    "le noeud donné ne fait pas partie du graphe");
        } else {
            return neighbors.get(node);
        }
    }

    /**
     * Batisseur qui permet de construire un graphe en plusieurs étapes
     * 
     * @author Karine Perrard (250605)
     * @author Loic Nguyen (238243)
     *
     *
     */

    public static final class Builder<N> {
        /**
         * représente la liste de points en contruction a entrer en arguments
         * lors de la construction d'un graphe
         */
        private Map<N, Set<N>> neighbors;

        /**
         * contruit une liste vide
         */

        public Builder() {
            neighbors = new HashMap<N, Set<N>>();
        }

        /**
         * permet d'ajouter un noeud a la liste des noeuds en contruction
         * 
         * @param n
         *            noeud a ajouter
         */

        public void addNode(N n) {
            neighbors.putIfAbsent(n, new HashSet<N>());
        }

        /**
         * permet de créer une liaison entre les deux noeuds entrés en argument
         * en entrant chaque noeud dans la liste des noeuds voisins de l'autre.
         * Lance une exception si l'un des deux noeuds n'est pas dans la liste
         *
         * 
         * @param n1
         *            le premier noeud
         * @param n2
         *            le deuxieme noeud
         */

        public void addEdge(N n1, N n2) {
            if (!neighbors.containsKey(n1)) {
                throw new IllegalArgumentException(
                        "Le premier noeud n'appartient pas au graphe en cours de construction");

            } else if (!neighbors.containsKey(n2)) {
                throw new IllegalArgumentException(
                        "Le deuxieme noeud n'appartient pas au graphe en cours de construction");
            } else {
                neighbors.get(n1).add(n2);
                neighbors.get(n2).add(n1);
            }
        }

        /**
         * Crée un graphe avec les noeuds et les aretes ajoutées jusqu'a présent
         * 
         * @return un Graph construit a partir des valeur données
         * 
         */

        public Graph<N> build() {
            return new Graph<N>(neighbors);
        }

    }

}

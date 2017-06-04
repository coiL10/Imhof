package ch.epfl.imhof.osm;

import ch.epfl.imhof.Attributes;
import ch.epfl.imhof.PointGeo;

/**
 * Représente un noeud OpenStreetMap qui est défini par une position, un
 * identifiant et des attributs
 * 
 * @author Karine Perrard (250605)
 * @author Loic Nguyen (238243)
 *
 *
 */
public final class OSMNode extends OSMEntity {
    /**
     * représente la position du noeud
     */
    private final PointGeo position;

    /**
     * permet de construire un noeud
     * 
     * @param id
     *            représente l'identifiant du noeud
     * @param position
     *            représente la position du noeud
     * @param attributes
     *            représente les attributs du noeud
     */

    public OSMNode(long id, PointGeo position, Attributes attributes) {
        super(id, attributes);
        this.position = position;
    }

    /**
     * retourne la position du point
     * 
     * @return la position du point
     */

    public PointGeo position() {
        return position;
    }

    /**
     * permet de construire le noeud en plusieurs étapes
     * 
     * @author Karine Perrard (250605)
     * @author Loic Nguyen (238243)
     *
     *
     */

    public static final class Builder extends OSMEntity.Builder {
        /**
         * position est une variable a entrer en argument lors de la
         * construction du noeud
         */
        private final PointGeo position;

        /**
         * constructeur du batisseur
         * 
         * @param id
         *            est passé en argument a la superclasse
         * @param position
         *            est la position du noeud a construire
         */

        public Builder(long id, PointGeo position) {
            super(id);
            this.position = position;
        }

        /**
         * permet de construire un noeud
         * 
         * @return le noeud construit
         */

        public OSMNode build() {
            if (isIncomplete()) {
                throw new IllegalStateException(
                        "Le noeud en cours de construction est incomplet");
            }

            return new OSMNode(super.getid(), position, super.getAttributes()
                    .build());
        }
    }

}

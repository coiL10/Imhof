package ch.epfl.imhof.osm;

import ch.epfl.imhof.Attributes;

/**
 * classe mere de toutes les entites OSM
 * 
 * 
 * @author Karine Perrard (250605)
 * @author Loic Nguyen (238243)
 *
 *
 */

public abstract class OSMEntity {

    private final long id;
    private final Attributes attributes;

    /**
     * construit une entité
     * 
     * @param id
     *            représente l'identifiant de l'entité
     * @param attributes
     *            représente les attributs de l'entité
     */

    public OSMEntity(long id, Attributes attributes) {
        this.id = id;
        this.attributes = attributes;
    }

    /**
     * retourne l'identifiant de l'entité
     * 
     * @return un long qui identifie l'entité
     */

    public long id() {
        return id;
    }

    /**
     * retourne les attributs de l'entité
     * 
     * @return l'attribut de l'entité
     */

    public Attributes attributes() {
        return attributes;
    }

    /**
     * retourne vrai si les attributs de l'entité contiennent la clé donnée en
     * argument
     * 
     * @param key
     *            la clé dont on veut déterminer l'existence
     * @return true si l'entité contient la clé donnée
     * 
     */

    public boolean hasAttribute(String key) {
        return attributes.contains(key);
    }

    /**
     * retourne la valeur associée a la clé entrée en argument
     * 
     * @param key
     *            la clé dont on veut la valeur
     * @return un string correspondant a la valeur attendue
     */

    public String attributeValue(String key) {
        return attributes.get(key);
    }

    /**
     * ce builder permet de construire une entité en plusieurs étapes
     * 
     * @author Karine Perrard (250605)
     * @author Loic Nguyen (238243)
     *
     *
     */

    public abstract static class Builder {
        /**
         * id et attributes sont les variables a passer en argument lors de la
         * construction de l'entité isIncomplete empêche de construire l'entité
         * si sa valeur est true: cela signifie que l'entité en cours de
         * construction est incomplete
         */
        private final long id;
        private boolean isIncomplete;
        private final Attributes.Builder attributes;

        /**
         * crée une liste d'attributs vide donne la valeur id a l'identifiant
         * 
         * @param id
         *            est l'identifiant a donner a l'entité
         */

        public Builder(long id) {
            this.id = id;
            attributes = new Attributes.Builder();
            isIncomplete = false;
        }

        /**
         * permet d'entrer un nouvel attribut dans la liste créée
         * 
         * @param key
         *            est la clé de l'attribut
         * @param value
         *            est la valeur associée a la clé
         */

        public void setAttribute(String key, String value) {
            attributes.put(key, value);
        }

        /**
         * permet de déclarer que l'entité en cours de construction est
         * incomplete
         */

        public void setIncomplete() {
            isIncomplete = true;
        }

        /**
         * retourne false si l'entité en construction est complete, et true
         * sinon
         * 
         * @return false si l'entité est incomplete
         */

        public boolean isIncomplete() {
            return isIncomplete;
        }

        /**
         * retourne l'identifiant de l'entité
         * 
         * @return un long qui identifie l'entité
         */

        protected long getid() {
            return id;
        }

        /**
         * retourne la liste d'attributs de l'entité en construction
         * 
         * @return les attributs de l'entité
         */

        protected Attributes.Builder getAttributes() {
            return attributes;
        }

    }
}

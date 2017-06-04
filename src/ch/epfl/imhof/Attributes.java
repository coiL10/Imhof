package ch.epfl.imhof;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Représente un ensemble d'attributs (un ensemble de paire clef/valeur)
 * 
 * 
 * @author Loïc Nguyen (238243)
 * @author Karine Perrard (250605)
 */
public final class Attributes {
    private final Map<String, String> attributes;

    /**
     * Construit l'ensemble d'attributs
     * 
     * @param attributes
     *            liste associative contenant les attributs de la carte
     */
    public Attributes(Map<String, String> attributes) {
        this.attributes = Collections
                .unmodifiableMap(new HashMap<String, String>(attributes));
    }

    /**
     * Retourne true si et seulement la liste d'attributs est vide
     * 
     * @return boolean qui indique si la liste est vide
     */
    public boolean isEmpty() {
        return attributes.isEmpty();
    }

    /**
     * Retourne vrai si l'ensemble d'attributs contient la clé donnée
     * 
     * @param key
     *            la clef donnée
     * @return boolean qui indique si l'ensemble d'attributs contient la clé
     *         donnée
     */
    public boolean contains(String key) {
        return attributes.containsKey(key);
    }

    /**
     * Retourne la valeur associée à la clé donnée, ou null si la clé n'existe
     * pas
     * 
     * @param key
     *            la clef donnée
     * @return String représentant la valeur associée à la clé donnée
     */
    public String get(String key) {
        return attributes.get(key);
    }

    /**
     * @param key
     *            la clé donnée
     * @param defaultValue
     *            la valeur par défaut
     * @return retourne la valeur associé à la clé donnée, ou la valeur par
     *         défaut donnée si aucune valeur ne lui est associée
     */
    public String get(String key, String defaultValue) {
        return attributes.getOrDefault(key, defaultValue);
    }

    /**
     * @param key
     *            la clé donnée
     * @param defaultValue
     *            la valeur par défaut
     * @return retourne l'entier associé à la clé donnée, ou la valeur par
     *         défaut donnée si aucune valeur ne lui est associé ou si cette
     *         valeur n'est pas un entier valide
     */
    public int get(String key, int defaultValue) {
        String s = attributes.get(key);
        if (s != null) {
            try {
                int value = Integer.parseInt(s);
                return value;
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        } else {
            return defaultValue;
        }
    }

    /**
     * Retourne une version filtrée des attributs ne contenant que ceux dont le
     * nom figure dans l'ensemble passé
     * 
     * @param keysToKeep
     *            ensemble de clé que l'on veut garder parmi les attributs
     * @return un objet Attributes qui est une version filtrée des attributs
     */
    public Attributes keepOnlyKeys(Set<String> keysToKeep) {
        Map<String, String> kept = new HashMap<String, String>(attributes);
        kept.keySet().retainAll(keysToKeep);
        return new Attributes(kept);
    }

    /**
     * Batisseur de la classe Attributes pour construire l'objet progressivement
     * 
     * @author Loïc Nguyen (238243)
     * @author Karine Perrard (250605)
     */
    public final static class Builder {
        private Map<String, String> attributes;

        /**
         * Construit une liste associative vide
         */
        public Builder() {
            this.attributes = new HashMap<String, String>();
        }

        /**
         * ajoute l'association clef/valeur donnée é l'ensemble d'attributs en
         * cours de construction. Si un attribut de même nom avait déjà été
         * ajouté précédemment à l'ensemble, sa valeur est remplacée par celle
         * donnée
         * 
         * @param key
         *            clée associée
         * @param value
         *            valeur associée
         * 
         */

        public void put(String key, String value) {
            attributes.put(key, value);
        }

        /**
         * Construit et retourne un ensemble d'attributs contenant les
         * associations clés/valeur ajoutées jusqu'à présent
         * 
         * @return objet de type Attributes construit à partir des valeurs
         *         ajoutées
         */
        public Attributes build() {
            return new Attributes(attributes);
        }
    }

}

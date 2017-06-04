package ch.epfl.imhof;

/**
 * Classe générique qui représente une entité de type T dotée d'attributs
 * 
 * @author Loïc Nguyen (238243)
 * @author Karine Perrard (250605)
 * @param <T>
 */
public final class Attributed<T> {
    private final T value;
    private final Attributes attributes;

    /**
     * Construit une valeur attribuée dont la valeur et les attributs dont ceux
     * donnés
     * 
     * @param value
     *            valeur
     * @param attributes
     *            liste associative d'attributs
     */
    public Attributed(T value, Attributes attributes) {
        this.value = value;
        this.attributes = attributes;
    }

    /**
     * Retourne la valeur à laquelle les attributs sont attachés
     * 
     * @return la valeur de l'attribut
     */
    public T value() {
        return value;
    }

    /**
     * Retourne les attributs attachés à la valeur
     * 
     * @return retourne les attributs
     */
    public Attributes attributes() {
        return attributes;
    }

    /**
     * Retourne vrai si et seulement si les attributs incluent celui dont le nom
     * est passé en argument
     * 
     * @param attributeName
     *            nom de l'attribut
     * @return boolean qui indique si les attributs contiennent celui passé en
     *         argument
     */
    public boolean hasAttribute(String attributeName) {
        return attributes.contains(attributeName);
    }

    /**
     * Retourne la valeur associée à l'attribut donné ou null si celui-ci
     * n'existe pas
     * 
     * @param attributeName
     *            nom de l'attribut
     * @return un String correspondant à la valeur de l'attribut
     */
    public String attributeValue(String attributeName) {
        return attributes.get(attributeName);
    }

    /**
     * Retourne la valeur associée à l'attribut donné ou la valeur par défaut
     * donnée si celui-ci n'existe pas
     * 
     * @param attributeName
     *            nom de l'attribut
     * @param defaultValue
     *            valeur par défaut
     * @return un String correspondant à la valeur de l'attribut ou la valeur
     *         par defaut
     */
    public String attributeValue(String attributeName, String defaultValue) {
        return attributes.get(attributeName, defaultValue);
    }

    /**
     * Retourne la valeur entière associée à l'attribut donné, ou la
     *         valeur par défaut si celui-ci n'existe pas ou si la valeur qui lui
     *         est donnée n'est pas un entier valide
     * @param attributeName
     *            nom de l'attribut
     * @param defaultValue
     *            valeur par défaut
     * @return un int associé à l'attribut donné ou null s'il n'existe pas
     */
    public int attributeValue(String attributeName, int defaultValue) {
        return attributes.get(attributeName, defaultValue);
    }
}

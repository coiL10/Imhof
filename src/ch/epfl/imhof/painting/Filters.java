package ch.epfl.imhof.painting;

import java.util.function.Predicate;

import ch.epfl.imhof.Attributed;

/**
 * Classe utilitaire servant à déterminer, étant donnée une entite attributée,
 * si elle doit être gardée ou non
 * 
 * @author Loïc Nguyen (238243)
 * @author Karine Perrard (250605)
 */
public final class Filters {
    private Filters() {
    }

    /**
     * Prend un nom d'attribut en argument et retourne un predicat qui n'est
     * vrai que si la valeur attribuée à laquelle on l'applique possède un
     * attribut portant ce nom
     * 
     * @param attributeName
     *            nom de l'attribut que l'on veut garder
     * @return predicat
     */
    public static Predicate<Attributed<?>> tagged(String attributeName) {
        return x -> x.hasAttribute(attributeName);
    }

    /**
     * crée un prédicat qui n'est vrai que si la valeur attribuée a laquelle il
     * s'applique possède un attribut portant ce nom, et si en plus la valeur
     * associée a cet attribut fait partie de celles données
     * 
     * @param attributeName
     *            le nom de l'attribut que l'on veut garder
     * @param attributeValues
     *            les valeurs que l'on veut garder
     * @return retourne le prédicat associé
     */

    public static Predicate<Attributed<?>> tagged(String attributeName,
            String... attributeValues) {
        return x -> {
            if (!x.hasAttribute(attributeName)) {
                return false;
            }
            for (String s : attributeValues) {
                if (x.attributeValue(attributeName).equals(s)) {
                    return true;
                }
            }
            return false;
        };
    }

    /**
     * crée un prédicat qui n'est vrai que si la valeur attribuée a laquelle il
     * s'applique appartient a une couche donnée
     * 
     * @param number
     *            le numéro de la couche voulue
     * @return retourne le prédicat demandé
     */

    public static Predicate<Attributed<?>> onLayer(int number) {
        return x -> {
            return x.attributeValue("layer", 0) == number;
        };
    }
}

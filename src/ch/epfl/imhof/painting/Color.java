package ch.epfl.imhof.painting;

/**
 * représente une couleur, décrite par 3 composantes rouge, vert, bleu comprises
 * entre 0 et 1
 * 
 * @author Karine Perrard (250605)
 * @author Loic Nguyen (238243)
 *
 *
 */

public final class Color {
    private final float r, g, b;

    /*
     * la couleur rouge (pure)
     */
    public final static Color RED = new Color(1, 0, 0);
    /*
     * la couleur verte (pure)
     */
    public final static Color GREEN = new Color(0, 1, 0);
    /*
     * la couleur bleue (pure)
     */
    public final static Color BLUE = new Color(0, 0, 1);
    /*
     * la couleur blanche
     */
    public final static Color WHITE = new Color(1, 1, 1);
    /*
     * la couleur noire
     */
    public final static Color BLACK = new Color(0, 0, 0);

    private Color(float r, float g, float b) {
        valide(r);
        valide(g);
        valide(b);
        this.r = r;
        this.g = g;
        this.b = b;
    }

    /**
     * crée une couleur grise ayant l'intensité voulue
     * 
     * @param g
     *            l'intensité voulue
     * @return retourne le gris correspondant
     */

    public static Color gray(float g) {
        valide(g);
        return new Color(g, g, g);
    }

    /**
     * méthode construisant une couleur voulue
     * 
     * @param r
     *            la composante rouge de la couleur
     * @param g
     *            la composante verte de la couleur
     * @param b
     *            la composante bleue de la couleur
     * @return retourne la couleur voulue
     */

    public static Color rgb(float r, float g, float b) {
        valide(r);
        valide(g);
        valide(b);
        return new Color(r, g, b);

    }

    /**
     * crée une nouvelle couleur à partir de trois composantes rouge, vert, bleu
     * empaquetées dans un entier
     * 
     * @param packed
     *            l'entier contenant les composantes
     * @return la couleur correspondante
     */

    public static Color rgb(int packed) {
        return new Color((float) (((packed >> 16) & 0xFF) / 255d),
                (float) (((packed >> 8) & 0xFF) / 255d),
                (float) (((packed >> 0) & 0xFF) / 255d));
    }

    /**
     * multiplie deux couleurs entre elles en multipliant chacune des
     * composantes r, g, b des deux couleurs entre elles
     * 
     * @param c
     *            la couleur a multiplier
     * @return retourne la multiplication des deux couleurs
     */

    public Color multiply(Color c) {
        return new Color(r * c.r, g * c.g, b * c.b);
    }

    /**
     * convertir une couleur en couleur de l'API Java
     * 
     * @param c
     *            la couleur a convertir
     * @return retourne la couleur convertie
     */

    public java.awt.Color convert() {
        return new java.awt.Color(r, g, b);
    }

    /**
     * 
     * @return la composante rouge de la couleur
     */

    public float getr() {
        return r;
    }

    /**
     * 
     * @return la composante verte de la couleur
     */

    public float getg() {
        return g;
    }

    /**
     * 
     * @return la composante bleue de la couleur
     */

    public float getb() {
        return b;
    }

    /**
     * ajout d'une méthode levant une exception si le nombre entré en argument
     * n'est pas compris entre 0 et 1
     * 
     * @param c
     *            le nombre a tester
     */

    private static void valide(float c) {
        if (!(c >= 0.0 && c <= 1.0)) {
            throw new IllegalArgumentException(
                    "La composante n'est pas comprise entre 0 et 1");
        }
    }

}

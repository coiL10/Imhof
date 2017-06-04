package ch.epfl.imhof;

/**
 * Classe représentant un vecteur tridimensionnel
 * 
 * 
 * @author Loïc Nguyen (238243)
 * @author Karine Perrard (250605)
 */
public final class Vector3 {
    private final double x;
    private final double y;
    private final double z;

    /**
     * Construit un vecteur tridimensionnel selon les composantes x,y et z
     * données
     * 
     * @param x
     *            composante x du vecteur
     * @param y
     *            composante y du vecteur
     * @param z
     *            composante z du vecteur
     */
    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Permet d'obtenir la norme du vecteur
     * 
     * @return la norme du vecteur en double
     */
    public double norm() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    /**
     * Permet d'obtenir la version normalisée du vecteur
     * 
     * @return un vecteur parallèle, de même direction et de longueur unitaire
     */
    public Vector3 normalized() {
        return new Vector3(x / norm(), y / norm(), z / norm());
    }

    /**
     * Retourne le produit scalaire entre le récepteur et un second vecteur
     * passé en argument
     * 
     * @param v2
     *            le second vecteur
     * @return retourne le produit scalaire en double
     */
    public double scalarProduct(Vector3 v2) {
        return x * v2.x + y * v2.y + z * v2.z;
    }

    /**
     * Retourne la composante x du vecteur
     * 
     * @return composante x en double
     */
    public double x() {
        return x;
    }

    /**
     * Retourne la composante y du vecteur
     * 
     * @return composante y en double
     */
    public double y() {
        return y;
    }

    /**
     * Retourne la composante z du vecteur
     * 
     * @return composante z en double
     */
    public double z() {
        return z;
    }

}

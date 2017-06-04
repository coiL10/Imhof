package ch.epfl.imhof;

/**
 * Un point à la surface de la Terre, en coordonnées
 * sphériques
 * 
 * @author Loïc Nguyen (238243)
 * @author Karine Perrard (250605)
 */
public final class PointGeo {
    private final double longitude, latitude;
    
    /**
     * Construit un point avec la latitude et la longitude données.
     *
     * @param longitude
     *            la longitude du point, en radians
     * @param latitude
     *            la latitude du point, en radians
     * @throws IllegalArgumentException
     *             si la longitude est invalide, c-à-d hors de l'intervalle [-π;
     *             π]
     * @throws IllegalArgumentException
     *             si la latitude est invalide, c-à-d hors de l'intervalle
     *             [-π/2; π/2]
     */
    public PointGeo(double longitude, double latitude){
        if ((longitude < -Math.PI) || (longitude > Math.PI)){
            throw new IllegalArgumentException("La longitude doit etre comprise dans [-π, π]");
        }
        if ((latitude < (-Math.PI/2)) || (latitude > Math.PI/2)){
            throw new IllegalArgumentException("La longitude doit etre comprise dans [-π/2, π/2]");
        }
        this.longitude = longitude;
        this.latitude = latitude;
    }
    
    /**
     * Retourne la longitude du PointGeo
     * 
     * @return double qui représente la longitude
     */
    public double longitude(){
        return longitude;
    }
    
    /**
     * Retourne la latitude
     * @return double qui représente la latitude
     */
    public double latitude(){
        return latitude;
    }

}

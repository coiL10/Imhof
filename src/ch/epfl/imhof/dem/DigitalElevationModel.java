package ch.epfl.imhof.dem;

import ch.epfl.imhof.PointGeo;
import ch.epfl.imhof.Vector3;

/**
 * Interface représentant un modèle numérique de terrain
 * 
 * @author Karine Perrard (250605)
 * @author Loic Nguyen (238243)
 *
 *
 */
public interface DigitalElevationModel extends AutoCloseable {

    /**
     * calcule le vecteur normal à la Terre en le point entré en argument
     * 
     * @param pt
     *            le point en coordonnées WGS 84
     * @return retourne le vecteur normal
     */
    Vector3 normalAt(PointGeo pt);
}

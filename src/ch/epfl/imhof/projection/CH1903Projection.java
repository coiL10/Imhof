package ch.epfl.imhof.projection;

import ch.epfl.imhof.PointGeo;
import ch.epfl.imhof.geometry.Point;

/**
 * Projection Suisse (CH1903)
 * 
 * 
 * @author Loïc Nguyen (238243)
 * @author Karine Perrard (250605)
 */
public final class CH1903Projection implements Projection {

    /*
     * (non-Javadoc)
     * 
     * @see ch.epfl.imhof.projection.Projection#project(ch.epfl.imhof.PointGeo)
     */
    @Override
    public Point project(PointGeo point) {
        double lambda = (1 / 10000.)
                * ((Math.toDegrees(point.longitude()) * 3600) - 26782.5);
        double phi = (1 / 10000.)
                * ((Math.toDegrees(point.latitude()) * 3600) - 169028.66);
        // formules données dans l'énoncé
        double x = (600072.37 + (211455.93 * lambda)
                - (10938.51 * lambda * phi)
                - (0.36 * lambda * Math.pow(phi, 2)) - 44.54 * Math.pow(lambda,
                3));
        double y = (200147.07 + (308807.95 * phi)
                + (3745.25 * Math.pow(lambda, 2)) + (76.63 * Math.pow(phi, 2))
                - (194.56 * Math.pow(lambda, 2) * phi) + (119.79 * Math.pow(
                phi, 3)));
        return new Point(x, y);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * ch.epfl.imhof.projection.Projection#inverse(ch.epfl.imhof.geometry.Point)
     */
    @Override
    public PointGeo inverse(Point point) {
        double x = ((point.x() - 600000) / 1000000.);
        double y = ((point.y() - 200000) / 1000000.);
        // formules données dans l'énoncé
        double lambda0 = (2.6779094 + (4.728982 * x) + (0.791484 * x * y)
                + (0.1306 * x * Math.pow(y, 2)) - (0.0436 * Math.pow(x, 3)));
        double phi0 = (16.9023892 + (3.238272 * y)
                - (0.270978 * Math.pow(x, 2)) - (0.002528 * Math.pow(y, 2))
                - (0.0447 * Math.pow(x, 2) * y) - (0.0140 * Math.pow(y, 3)));
        double lambda = lambda0 * (100. / 36.);
        double phi = phi0 * (100. / 36.);
        return new PointGeo(Math.toRadians(lambda), Math.toRadians(phi));
    }

}

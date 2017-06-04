package ch.epfl.imhof.dem;

import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.function.Function;

import ch.epfl.imhof.Vector3;
import ch.epfl.imhof.geometry.Point;
import ch.epfl.imhof.projection.Projection;

/**
 * Classe permettant de représenter un relief ombré
 * 
 * @author Karine Perrard (250605)
 * @author Loic Nguyen (238243)
 *
 *
 */
public final class ReliefShader {
    private final Projection project;
    private final DigitalElevationModel dem;
    private final Vector3 light;

    /**
     * construit un relief ombré
     * 
     * @param projection
     *            la projection utilisée
     * @param dem
     *            le modèle numérique du terrain à représenter
     * @param light
     *            le vecteur pointant vers la source lumineuse
     */
    public ReliefShader(Projection projection, DigitalElevationModel dem,
            Vector3 light) {
        this.project = projection;
        this.dem = dem;
        this.light = light;

    }

    /**
     * crée et dessine un relief ombré flouté
     * 
     * @param bl
     *            le point bas gauche du relief
     * @param tr
     *            le point haut droite du relief
     * @param width
     *            la largeur du relief
     * @param height
     *            la hauteur du relief
     * @param radius
     *            le rayon de floutage
     * @return retourne l'image représentant le relief
     */

    public BufferedImage shadedRelief(Point bl, Point tr, int width,
            int height, double radius) {
        int ceilRadius = (int) Math.ceil(radius);
        Function<Point, Point> function = Point.alignedCoordinateChange(
                new Point(ceilRadius, height + ceilRadius), bl, new Point(width
                        + ceilRadius, ceilRadius), tr);

        BufferedImage crudeRelief = crudeRelief(width + 2 * ceilRadius, height
                + 2 * ceilRadius, function);
        float[] ker = kernel(radius);

        return blurred(crudeRelief, ker).getSubimage(ceilRadius, ceilRadius,
                width, height);

    }

    /**
     * permet de créer un relief non flouté
     * 
     * @param width
     *            la largeur du relief
     * @param height
     *            la hauteur du relief
     * @param function
     *            la fonction permettant de projeter un point en coordonnées
     *            voulues
     * @return retourne l'image représentant le relief non flouté
     */
    private BufferedImage crudeRelief(int width, int height,
            Function<Point, Point> function) {
        BufferedImage im = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {

                Vector3 normal = dem.normalAt(project.inverse(function
                        .apply(new Point(i, j))));
                double cos = normal.scalarProduct(light)
                        / (normal.norm() * light.norm());
                int rg = (int) (255.9999 * (cos + 1) / 2d);
                int b = (int) (255.9999 * (0.7 * cos + 1) / 2d);
                int rgb = (rg << 16) | (rg << 8) | b;
                im.setRGB(i, j, rgb);
            }
        }
        return im;

    }

    /**
     * permet de calculer un noyau unidimensonnel du fou gaussien
     * 
     * @param r
     *            le rayon de floutage
     * @return retourne un vecteur représentant le noyau
     */
    private float[] kernel(double r) {
        double o = r / 3d;
        int n = (int) (2 * Math.ceil(r) + 1);
        float[] ker = new float[n];
        int nulPosition = (int) Math.floor(n / 2d);
        double sum = 0;
        for (int i = 0; i < n; ++i) {
            int x = i - nulPosition;
            ker[i] = (float) Math.exp(-((x * x) / (2 * o * o)));
            sum += ker[i];
        }
        for (int f = 0; f < ker.length; ++f) {
            ker[f] = (float) (ker[f] / sum);
        }
        return ker;

    }

    /**
     * permet de flouter une image donnée avec un noyau donné
     * 
     * @param image
     *            l'image a flouter
     * @param rad
     *            le noyau de floutage
     * @return retourne l'image floutée
     */
    private BufferedImage blurred(BufferedImage image, float[] rad) {
        Kernel kh = new Kernel(rad.length, 1, rad);
        Kernel kv = new Kernel(1, rad.length, rad);
        ConvolveOp cH = new ConvolveOp(kh, ConvolveOp.EDGE_NO_OP, null);
        ConvolveOp cV = new ConvolveOp(kv, ConvolveOp.EDGE_NO_OP, null);
        BufferedImage hz = cH.filter(image, null);
        BufferedImage vt = cV.filter(hz, null);
        return vt;

    }
}

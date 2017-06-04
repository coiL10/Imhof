package ch.epfl.imhof.dem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ShortBuffer;
import java.nio.channels.FileChannel.MapMode;

import ch.epfl.imhof.PointGeo;
import ch.epfl.imhof.Vector3;

/**
 * représente un modèle numérique du terrain en coordonnées HGT
 * 
 * @author Karine Perrard (250605)
 * @author Loic Nguyen (238243)
 *
 *
 */
public final class HGTDigitalElevationModel implements DigitalElevationModel {
    private ShortBuffer buffer;
    private final int sideSize;
    private final double delta;
    private final PointGeo bl;

    /**
     * crée un nouveau modèle numérique du terrain depuis le fichier d'altitudes
     * entré en argument
     * 
     * @param file
     *            le fichier contenant les altitudes
     * @throws FileNotFoundException
     * @throws IOException
     */

    public HGTDigitalElevationModel(File file) throws FileNotFoundException,
            IOException {

        String name = file.getName();
        if (valid(name)) {
            throw new IllegalArgumentException(
                    "le nom du fichier n'est pas valide");
        }
        long v = Math.round(Math.sqrt(file.length() / 2));
        if (!((v * v == file.length() / 2) && ((v - 1) % 2 == 0))) {
            throw new IllegalArgumentException(
                    "la taille du fichier n'est pas valide");
        }

        double x, y;
        String la = name.substring(1, 3);
        String lo = name.substring(4, 7);
        if (name.charAt(0) == 'N') {
            y = Integer.parseInt(la);
        } else {
            y = -Integer.parseInt(la);
        }
        if (name.charAt(3) == 'E') {
            x = Integer.parseInt(lo);
        } else {
            x = -Integer.parseInt(lo);
        }
        bl = new PointGeo(Math.toRadians(x), Math.toRadians(y));

        try (FileInputStream stream = new FileInputStream(file)) {
            buffer = stream.getChannel()
                    .map(MapMode.READ_ONLY, 0, file.length()).asShortBuffer();
        }
        sideSize = (int) (v) - 1;
        delta = 1d / sideSize;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.AutoCloseable#close()
     */
    @Override
    public void close() {
        buffer = null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * ch.epfl.imhof.dem.DigitalElevationModel#normalAt(ch.epfl.imhof.PointGeo)
     */
    @Override
    public Vector3 normalAt(PointGeo point) {

        if (point.latitude() <= bl.latitude()
                || point.latitude() >= bl.latitude() + Math.toRadians(1)
                || point.longitude() <= bl.longitude()
                || point.longitude() >= bl.longitude() + Math.toRadians(1)) {
            throw new IllegalArgumentException(
                    "le point n'est pas dans la zone couverte");
        }
        double s = Earth.RADIUS * Math.toRadians(delta);

        int j = sideSize
                - (int) (Math.toDegrees((point.latitude() - bl.latitude())
                        / delta));

        int i = (int) (Math.toDegrees((point.longitude() - bl.longitude())
                / delta));
        double v1 = 1
                / 2d
                * s
                * (buffer.get(i + j * (sideSize + 1))
                        - buffer.get(i + 1 + j * (sideSize + 1))
                        + buffer.get(i + (j - 1) * (sideSize + 1)) - buffer
                            .get(i + 1 + (j - 1) * (sideSize + 1)));
        double v2 = 1
                / 2d
                * s
                * (buffer.get(i + j * (sideSize + 1))
                        + buffer.get(i + 1 + j * (sideSize + 1))
                        - buffer.get(i + (j - 1) * (sideSize + 1)) - buffer
                            .get(i + 1 + (j - 1) * (sideSize + 1)));
        return new Vector3(v1, v2, s * s);
    }

    /**
     * permet de déterminer si un nom de fichier est valide
     * 
     * @param name
     *            le nom du fichier
     * @return retourne vrai si le fichier est valide, faux sinon
     */
    private boolean valid(String name) {
        if (!((name.charAt(0) == 'N' || name.charAt(0) == 'S') && (name
                .charAt(3) == 'E' || name.charAt(3) == 'O'))) {
            return false;
        }
        String nbr = name.substring(1, 3);
        nbr = nbr + name.substring(4, 7);
        for (int i = 0; i < nbr.length(); ++i) {
            if (!(name.charAt(i) >= '0' && (name.charAt(i) <= '9'))) {
                return false;
            }
        }
        if (!name.endsWith(".hgt")) {
            return false;
        }
        return true;
    }

}

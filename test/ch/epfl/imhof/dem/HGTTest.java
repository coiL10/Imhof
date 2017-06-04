package ch.epfl.imhof.dem;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;

import ch.epfl.imhof.PointGeo;

public class HGTTest {

    @Test
    public void test() throws IOException, Exception {
        File file = new File("data/N46E007.hgt");
        HGTDigitalElevationModel hgt = new HGTDigitalElevationModel(file);
        BufferedImage image = new BufferedImage(800, 800,
                BufferedImage.TYPE_INT_RGB);
        double ratio = 0.6 / 800;

        for (int i = 0; i < 799; ++i) {
            for (int j = 0; j < 799; ++j) {
                double y = hgt
                        .normalAt(
                                new PointGeo(Math.toRadians(7.2 + i * ratio),
                                        Math.toRadians(46.2 + (799-j) * ratio)))
                        .normalized().y() + 1;
                int gray = (int) (255.9999 * 1 / 2d * (y));
                int g = (gray << 16) | (gray << 8) | gray;
                image.setRGB(i, j, g);
            }
        }

        hgt.close();
        ImageIO.write(image, "png", new File("BWR.png"));
    }

}

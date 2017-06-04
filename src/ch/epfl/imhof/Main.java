package ch.epfl.imhof;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.xml.sax.SAXException;

import ch.epfl.imhof.dem.DigitalElevationModel;
import ch.epfl.imhof.dem.Earth;
import ch.epfl.imhof.dem.HGTDigitalElevationModel;
import ch.epfl.imhof.dem.ReliefShader;
import ch.epfl.imhof.geometry.Point;
import ch.epfl.imhof.osm.OSMMap;
import ch.epfl.imhof.osm.OSMMapReader;
import ch.epfl.imhof.osm.OSMToGeoTransformer;
import ch.epfl.imhof.painting.Color;
import ch.epfl.imhof.painting.Java2DCanvas;
import ch.epfl.imhof.painting.Painter;
import ch.epfl.imhof.painting.SwissPainter;
import ch.epfl.imhof.projection.CH1903Projection;
import ch.epfl.imhof.projection.Projection;

/**
 * Programme principal permettant de représenter une carte contenant les entités
 * et le relief
 * 
 * @author Karine Perrard (250605)
 * @author Loic Nguyen (238243)
 *
 *
 */
public final class Main {

    public static void main(String[] args) {
        File osmFile = new File(args[0]);
        File hgtFile = new File(args[1]);
        double blLong = Double.parseDouble(args[2]);
        double blLat = Double.parseDouble(args[3]);
        double trLong = Double.parseDouble(args[4]);
        double trLat = Double.parseDouble(args[5]);
        int dpi = Integer.parseInt(args[6]);
        String filePath = args[7];

        Projection projection = new CH1903Projection();
        OSMToGeoTransformer CH1903 = new OSMToGeoTransformer(projection);
        Painter painter = SwissPainter.painter();

        PointGeo blGeo = new PointGeo(Math.toRadians(blLong),
                Math.toRadians(blLat));
        PointGeo trGeo = new PointGeo(Math.toRadians(trLong),
                Math.toRadians(trLat));

        Point bl = projection.project(blGeo);
        Point tr = projection.project(trGeo);

        double r = dpi * 39.370079;

        int h = (int) Math.round((r * (1 / 25000d)
                * (trGeo.latitude() - blGeo.latitude()) * Earth.RADIUS));
        int w = (int) Math.round(((tr.x() - bl.x()) / (tr.y() - bl.y()) * h));

        try {
            DigitalElevationModel dem = new HGTDigitalElevationModel(hgtFile);
            ReliefShader shader = new ReliefShader(projection, dem,
                    new Vector3(-1, 1, 1));
            OSMMap OSMm = OSMMapReader.readOSMFile(osmFile.getName(), true);
            Map map = CH1903.transform(OSMm);

            Java2DCanvas canvas = new Java2DCanvas(bl, tr, w, h, dpi,
                    Color.WHITE);
            painter.drawMap(map, canvas);

            BufferedImage image = canvas.image();
            BufferedImage imageCanvas = new BufferedImage(w, h,
                    BufferedImage.TYPE_INT_RGB);

            BufferedImage relief = shader
                    .shadedRelief(bl, tr, w, h, 0.0017 * r);

            for (int i = 0; i < w; ++i) {
                for (int j = 0; j < h; ++j) {
                    Color color = Color.rgb(image.getRGB(i, j) & 0x00FFFFFF);
                    Color rgb = color
                            .multiply(Color.rgb(relief.getRGB(i, j) & 0x00FFFFFF));
                    imageCanvas.setRGB(i, j,
                            rgb.convert().getRGB() & 0x00FFFFFF);
                }
            }

            ImageIO.write(imageCanvas, "png", new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

    }

}

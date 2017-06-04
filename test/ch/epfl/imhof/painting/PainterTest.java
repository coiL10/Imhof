package ch.epfl.imhof.painting;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.function.Predicate;

import javax.imageio.ImageIO;

import org.junit.Test;
import org.xml.sax.SAXException;

import ch.epfl.imhof.Attributed;
import ch.epfl.imhof.Map;
import ch.epfl.imhof.geometry.Point;
import ch.epfl.imhof.osm.OSMMap;
import ch.epfl.imhof.osm.OSMMapReader;
import ch.epfl.imhof.osm.OSMToGeoTransformer;
import ch.epfl.imhof.projection.CH1903Projection;

public class PainterTest {
    // Le peintre et ses filtres
    @Test
    public void test() throws IOException, SAXException {

        Predicate<Attributed<?>> isLake = Filters.tagged("natural", "water");
        Painter lakesPainter = Painter.polygon(Color.BLUE).when(isLake);

        Predicate<Attributed<?>> isBuilding = Filters.tagged("building");
        Painter buildingsPainter = Painter.polygon(Color.BLACK)
                .when(isBuilding);

        Painter painter = buildingsPainter.above(lakesPainter);

        String fileName = getClass().getResource("/Lausanne.osm.gz").getFile();
        OSMMap map1 = OSMMapReader.readOSMFile(fileName, true);

        OSMToGeoTransformer CH1903 = new OSMToGeoTransformer(
                new CH1903Projection());

        Map map = CH1903.transform(map1);
        // Map map = …; // Lue depuis lausanne.osm.gz

        // La toile
        Point bl = new Point(532510, 150590);
        Point tr = new Point(539570, 155260);
        Java2DCanvas canvas = new Java2DCanvas(bl, tr, 800, 530, 72,
                Color.WHITE);

        // Dessin de la carte et stockage dans un fichier
        painter.drawMap(map, canvas);
        ImageIO.write(canvas.image(), "png", new File("loz.png"));
    }
}

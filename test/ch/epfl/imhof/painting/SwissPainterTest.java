package ch.epfl.imhof.painting;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;
import org.xml.sax.SAXException;

import ch.epfl.imhof.Map;
import ch.epfl.imhof.geometry.Point;
import ch.epfl.imhof.osm.OSMMap;
import ch.epfl.imhof.osm.OSMMapReader;
import ch.epfl.imhof.osm.OSMToGeoTransformer;
import ch.epfl.imhof.projection.CH1903Projection;

public class SwissPainterTest {
    @Test
    public void test() throws IOException, SAXException{
            Painter painter = SwissPainter.painter();

            String fileName = getClass().getResource("/Lausanne.osm.gz").getFile();
            OSMMap map1 = OSMMapReader.readOSMFile(fileName, true);
            
            OSMToGeoTransformer CH1903 = new OSMToGeoTransformer (new CH1903Projection());
            
            Map map = CH1903.transform(map1);
            //Map map = â€¦; // Lue depuis lausanne.osm.gz

            // La toile
            
            /*Point bl = new Point(515613, 221958);
            Point tr = new Point(518074, 223258);
            Java2DCanvas canvas =
                new Java2DCanvas(bl, tr, 752*2, 415*2, 72*2, Color.WHITE);*/
            
            Point bl = new Point(532510, 150590);
            Point tr = new Point(539570, 155260);
            Java2DCanvas canvas =
                new Java2DCanvas(bl, tr, 800*8, 530*8, 72*8, Color.WHITE);

            // Dessin de la carte et stockage dans un fichier
            painter.drawMap(map, canvas);
            ImageIO.write(canvas.image(), "png", new File("loz.png"));
    }

}

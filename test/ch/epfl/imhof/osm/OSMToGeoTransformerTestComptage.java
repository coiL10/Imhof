package ch.epfl.imhof.osm;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.xml.sax.SAXException;

import ch.epfl.imhof.Attributed;
import ch.epfl.imhof.Map;
import ch.epfl.imhof.geometry.Polygon;
import ch.epfl.imhof.projection.CH1903Projection;

public class OSMToGeoTransformerTestComptage {

    @Test
    public void test() throws IOException, SAXException {
        String fileName = getClass().getResource("/Lausanne.osm.gz").getFile();
        OSMMap map = OSMMapReader.readOSMFile(fileName, true);
        System.out.println("Lausanne");
        
        OSMToGeoTransformer CH1903 = new OSMToGeoTransformer (new CH1903Projection());
        
        Map map1 = CH1903.transform(map);

        
        System.out.println(map1.polyLines().size()+ "  polylignes");
        System.out.println(map1.polygons().size()+ "  polygones");
        
        
        List<Attributed<Polygon>> poly = map1.polygons();
    }

}

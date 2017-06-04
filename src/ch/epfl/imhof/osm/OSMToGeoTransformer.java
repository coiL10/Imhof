package ch.epfl.imhof.osm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import ch.epfl.imhof.Attributed;
import ch.epfl.imhof.Attributes;
import ch.epfl.imhof.Graph;
import ch.epfl.imhof.Map;
import ch.epfl.imhof.geometry.ClosedPolyLine;
import ch.epfl.imhof.geometry.OpenPolyLine;
import ch.epfl.imhof.geometry.Point;
import ch.epfl.imhof.geometry.PolyLine;
import ch.epfl.imhof.geometry.Polygon;
import ch.epfl.imhof.osm.OSMRelation.Member;
import ch.epfl.imhof.osm.OSMRelation.Member.Type;
import ch.epfl.imhof.projection.Projection;

/**
 * Convertit les données OSM en une carte
 * 
 * @author Karine Perrard (250605)
 * @author Loic Nguyen (238243)
 *
 *
 */

public final class OSMToGeoTransformer {
    private final Projection projection;

    /**
     * construit un convertisseur OSM en géométrie qui utilise la projection
     * donnée
     * 
     * @param projection
     *            la projection dans laquelle on désire projeter la map OSM
     */

    public OSMToGeoTransformer(Projection projection) {
        this.projection = projection;
    }

    /**
     * convertit la carte OSM en carte géométrique
     * 
     * @param map
     *            la map a transformer
     * @return la map construite
     */

    public Map transform(OSMMap map) {
        List<Attributed<Polygon>> polygons = new ArrayList<>();
        List<Attributed<PolyLine>> polylines = new ArrayList<>();

        String[] polylineAttributes = { "bridge", "highway", "layer",
                "man_made", "railway", "tunnel", "waterway" };
        String[] polygonsAttributes = { "building", "landuse", "layer",
                "leisure", "natural", "waterway" };
        Set<String> polylinesKeys = new HashSet<>(
                Arrays.asList(polylineAttributes));
        Set<String> polygonsKeys = new HashSet<>(
                Arrays.asList(polygonsAttributes));

        // Ces sets vont servir à filtrer les attributs

        List<OSMWay> way = map.ways();

        for (OSMWay w : way) {
            List<Point> points = new ArrayList<Point>();
            if (w.isClosed() && surface(w.attributes())) {
                for (OSMNode n : w.nonRepeatingNodes()) {
                    points.add(projection.project(n.position()));
                }

                Attributes keys = w.attributes().keepOnlyKeys(polygonsKeys);

                // crée un polygone fermé si le chemin représente une surface
                // et est valide
                if (!keys.isEmpty()) {
                    Attributed<Polygon> attributed = new Attributed<Polygon>(
                            new Polygon(new ClosedPolyLine(points)), keys);
                    polygons.add(attributed);
                }

            } else {

                Attributes keys = w.attributes().keepOnlyKeys(polylinesKeys);

                // crée une polyligne si celle ci est valide, c'est a dire si
                // elle contient au moins un des attributs donnés

                if (!keys.isEmpty()) {
                    PolyLine polyline;
                    if (w.isClosed()) {
                        for (OSMNode n : w.nonRepeatingNodes()) {
                            points.add(projection.project(n.position()));
                        }
                        polyline = new ClosedPolyLine(points);
                    } else {
                        for (OSMNode n : w.nodes()) {
                            points.add(projection.project(n.position()));
                        }
                        polyline = new OpenPolyLine(points);
                    }

                    Attributed<PolyLine> attributed = new Attributed<PolyLine>(
                            polyline, keys);

                    polylines.add(attributed);
                }

            }
        }

        List<OSMRelation> relations = map.relations();

        for (OSMRelation o : relations) {
            if ((o.hasAttribute("type"))) {
                if ((o.attributeValue("type").equals("multipolygon"))) {
                    Attributes keys = o.attributes().keepOnlyKeys(polygonsKeys);
                    if (!keys.isEmpty()) {
                        List<Attributed<Polygon>> multipolygon = assemblePolygon(
                                o, keys);
                        if (!multipolygon.isEmpty()) {
                            polygons.addAll(multipolygon);
                        }
                    }
                }
            }
        }

        return new Map(polylines, polygons);

    }

    /**
     * permet de déterminer si une liste d'attributs donnés représente une
     * surface
     * 
     * @param attributs
     *            les attributs a controler
     * @return true si les attributs correspondent a une surface
     */

    private boolean surface(Attributes attributs) {
        String[] keys = { "aeroway", "amenity", "building", "harbour",
                "historic", "landuse", "leisure", "man_made", "military",
                "natural", "office", "place", "power", "public_transport",
                "shop", "sport", "tourism", "water", "waterway", "wetland" };

        if (attributs.get("area", "no").equals("yes")
                || attributs.get("area", "0").equals("1")
                || attributs.get("area", "false").equals("true")) {
            return true;

        } else
            for (String k : keys) {
                if (attributs.contains(k)) {
                    return true;
                }
            }

        return false;
    }

    /**
     * construit et retourne l'ensemble des anneaux d'un type donné d'une
     * relation donnée
     * 
     * @param relation
     *            la relation dont on veut les anneaux
     * @param role
     *            le role des anneaux voulut (intérieurs ou extérieurs)
     * @return les anneaux de la relation
     */

    private List<ClosedPolyLine> ringsForRole(OSMRelation relation, String role) {
        List<OSMWay> ways = new ArrayList<>();
        for (Member m : relation.members()) { // extraction des chemins
            if (m.type() == Type.WAY) {
                if (m.role().equals(role)) {
                    ways.add((OSMWay) m.member());
                }
            }

        }

        Graph.Builder<OSMNode> newGraph = new Graph.Builder<OSMNode>();
        for (OSMWay w : ways) { // creation du graph
            List<OSMNode> nodes = w.nodes();
            OSMNode previousNode = null;
            for (OSMNode n : nodes) {
                newGraph.addNode(n);
                if (previousNode != null) {
                    newGraph.addEdge(previousNode, n);
                }
                previousNode = n;
            }
        }

        Graph<OSMNode> graph = newGraph.build();

        Set<OSMNode> checkList = new HashSet<OSMNode>(graph.nodes());
        List<ClosedPolyLine> rings = new ArrayList<>();

        Iterator<OSMNode> i = graph.nodes().iterator();

        while (i.hasNext()) {
            OSMNode n = i.next();
            if (graph.neighborsOf(n).size() != 2) {
                List<ClosedPolyLine> emptyList = Collections.emptyList();
                return emptyList;
            } else {
                if (checkList.contains(n)) {
                    checkList.remove(n);

                    ClosedPolyLine.Builder newRing = new ClosedPolyLine.Builder();
                    newRing.addPoint(projection.project(n.position()));

                    Iterator<OSMNode> it = new HashSet<>(graph.neighborsOf(n))
                            .iterator();

                    while (it.hasNext()) {
                        OSMNode o = it.next();
                        if (checkList.contains(o)) {
                            newRing.addPoint(projection.project(o.position()));
                            checkList.remove(o);
                            it = new HashSet<>(graph.neighborsOf(o)).iterator();
                        }
                    }
                    rings.add(newRing.buildClosed());
                }

            }
        }
        return rings;
    }

    /**
     * crée la liste des polygones de la relation en associant les anneaux
     * intérieurs et extérieurs correspondant
     * 
     * @param relation
     *            la relation dont on veut les polygones
     * @param attributes
     *            les attributs de la relation
     * @return la liste des polygones de la relation
     */

    private List<Attributed<Polygon>> assemblePolygon(OSMRelation relation,
            Attributes attributes) {

        List<Attributed<Polygon>> polygons = new ArrayList<>();
        List<ClosedPolyLine> outer = ringsForRole(relation, "outer");
        List<ClosedPolyLine> inner = ringsForRole(relation, "inner");

        if (outer.isEmpty()) {
            List<Attributed<Polygon>> emptyList = Collections.emptyList();
            return emptyList;
        }

        Collections.sort(outer, (s1, s2) -> Double.valueOf(s1.area())
                .compareTo(Double.valueOf(s2.area())));

        for (ClosedPolyLine c : outer) {
            List<ClosedPolyLine> holes = new ArrayList<>();
            Iterator<ClosedPolyLine> i = inner.iterator();
            while (i.hasNext()) {
                ClosedPolyLine d = i.next();
                if (c.containsPoint(d.firstPoint())) {
                    holes.add(d);
                    i.remove();
                }
            }
            polygons.add(new Attributed<Polygon>(new Polygon(c, holes),
                    attributes));
        }
        return polygons;
    }
}

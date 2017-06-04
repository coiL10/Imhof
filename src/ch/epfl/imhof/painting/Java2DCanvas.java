package ch.epfl.imhof.painting;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.function.Function;

import ch.epfl.imhof.geometry.ClosedPolyLine;
import ch.epfl.imhof.geometry.Point;
import ch.epfl.imhof.geometry.PolyLine;
import ch.epfl.imhof.geometry.Polygon;
import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;

/**
 * représente une toile permettant de représenter une image
 * 
 * @author Karine Perrard (250605)
 * @author Loic Nguyen (238243)
 *
 * 
 */
public final class Java2DCanvas implements Canvas {
    private final Function<Point, Point> coordinateChange;
    private final BufferedImage image;
    private final Graphics2D ctx;

    /**
     * crée une nouvelle toile sur laquelle on peut dessiner
     * 
     * @param bl
     *            le coin en bas a gauche en coordonnées projetées
     * @param tr
     *            le coin en haut a droite en coordonnées projetées
     * @param width
     *            la largeur de la toile en pixels
     * @param height
     *            la hauteur de la toile en pixels
     * @param dpi
     *            la résolution de l'image (en points par pouce)
     * @param bg
     *            la couleur de fons de la toile
     */

    public Java2DCanvas(Point bl, Point tr, int width, int height, int dpi,
            Color bg) {

        if (width < 0 || height <= 0 || dpi <= 0) {
            throw new IllegalArgumentException(
                    "la largeur, hauteur ou la résolution sont négatives");
        }
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        double scale = dpi / 72d;
        ctx = image.createGraphics();
        ctx.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
        ctx.setColor(bg.convert());
        ctx.fillRect(0, 0, width, height);
        ctx.translate(0, height);
        ctx.scale(scale, scale);

        coordinateChange = Point.alignedCoordinateChange(tr, new Point(width
                * (1 / scale), -height * (1 / scale)), bl, new Point(0, 0));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * ch.epfl.imhof.painting.Canvas#drawPolyLine(ch.epfl.imhof.geometry.PolyLine
     * , ch.epfl.imhof.painting.LineStyle)
     */
    @Override
    public void drawPolyLine(PolyLine polyline, LineStyle linestyle) {
        
        ctx.setStroke(new BasicStroke(linestyle.width(), linestyle.linecap().ordinal(), linestyle.linejoin().ordinal(), 10,
                linestyle.dash(), 0));
        ctx.setColor(linestyle.color().convert());
        Path2D line = new Path2D.Double();
        List<Point> points = polyline.points();
        line.moveTo(coordinateChange.apply(points.get(0)).x(), coordinateChange
                .apply(points.get(0)).y());
        for (int i = 1; i < points.size(); ++i) {
            line.lineTo(coordinateChange.apply(points.get(i)).x(),
                    coordinateChange.apply(points.get(i)).y());
        }
        if (polyline.isClosed()) {
            line.closePath();
        }
        ctx.draw(line);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * ch.epfl.imhof.painting.Canvas#drawPolygon(ch.epfl.imhof.geometry.Polygon,
     * ch.epfl.imhof.painting.Color)
     */
    @Override
    public void drawPolygon(Polygon polygon, Color color) {
        ctx.setColor(color.convert());
        Path2D line = new Path2D.Double();
        List<Point> shell = polygon.shell().points();

        line.moveTo(coordinateChange.apply(shell.get(0)).x(), coordinateChange
                .apply(shell.get(0)).y());

        for (int i = 1; i < shell.size(); ++i) {
            line.lineTo(coordinateChange.apply(shell.get(i)).x(),
                    coordinateChange.apply(shell.get(i)).y());
        }

        line.closePath();

        Area poly = new Area(line);

        List<ClosedPolyLine> holes = polygon.holes();
        for (ClosedPolyLine c : holes) {
            Path2D holeLine = new Path2D.Double();
            List<Point> points = c.points();
            holeLine.moveTo(coordinateChange.apply(points.get(0)).x(),
                    coordinateChange.apply(points.get(0)).y());
            for (int i = 1; i < points.size(); ++i) {
                holeLine.lineTo(coordinateChange.apply(points.get(i)).x(),
                        coordinateChange.apply(points.get(i)).y());
            }
            holeLine.closePath();
            Area hole = new Area(holeLine);
            poly.subtract(hole);
        }
        ctx.setColor(color.convert());
        ctx.fill(poly);
    }

    /**
     * 
     * @return retourne la toile comportant les éléments déja dessinés dessus
     */
    public BufferedImage image() {
        return image;
    }

}

package ch.epfl.imhof.painting;

import java.util.function.Predicate;

import ch.epfl.imhof.Attributed;
import ch.epfl.imhof.painting.LineStyle.LineCap;
import ch.epfl.imhof.painting.LineStyle.LineJoin;

/**
 * représente un générateur de réseau routier
 * 
 * @author Karine Perrard (250605)
 * @author Loic Nguyen (238243)
 *
 *
 */
public final class RoadPainterGenerator {

    private RoadPainterGenerator() {
    }

    /**
     * crée un peintre dessinant un reseau routier dont les routes ont des
     * spécifications données
     * 
     * @param roadspecs
     *            les spécifications des routes
     * @return le peintre
     */

    public static Painter painterForRoads(RoadSpec... roadspecs) {
        Painter bridgeInner = (x, y) -> {
        };
        Painter bridgeOuter = (x, y) -> {
        };
        Painter roadInner = (x, y) -> {
        };
        Painter roadOuter = (x, y) -> {
        };
        Painter tunnels = (x, y) -> {
        };

        Predicate<Attributed<?>> isBridge = Filters.tagged("bridge");
        Predicate<Attributed<?>> isTunnel = Filters.tagged("tunnel");

        for (RoadSpec r : roadspecs) {
            bridgeInner = bridgeInner.above(Painter.line(r.width1, r.color1,
                    LineCap.ROUND, LineJoin.ROUND, null).when(
                    r.pr.and(isBridge)));
            bridgeOuter = bridgeOuter.above(Painter.line(
                    r.width1 + 2 * r.width2, r.color2, LineCap.BUTT,
                    LineJoin.ROUND, null).when(r.pr.and(isBridge)));
            roadInner = roadInner.above(Painter.line(r.width1, r.color1,
                    LineCap.ROUND, LineJoin.ROUND, null).when(
                    r.pr.and(isBridge.negate()).and(isTunnel.negate())));
            roadOuter = roadOuter.above(Painter.line(r.width1 + 2 * r.width2,
                    r.color2, LineCap.ROUND, LineJoin.ROUND, null).when(
                    r.pr.and(isBridge.negate()).and(isTunnel.negate())));
            float[] dash = { r.width1 * 2, r.width1 * 2 };
            tunnels = tunnels.above(Painter.line(r.width1 / 2, r.color2,
                    LineCap.BUTT, LineJoin.ROUND, dash)
                    .when(r.pr.and(isTunnel)));
        }

        return bridgeInner.above(bridgeOuter.above(roadInner.above(roadOuter
                .above(tunnels))));

    }

    /**
     * représente le dessin d'un type de route donné
     * 
     * @author Karine Perrard (250605)
     * @author Loic Nguyen (238243)
     *
     *
     */

    public static final class RoadSpec {
        private final Predicate<Attributed<?>> pr;
        private final float width1;
        private final Color color1;
        private final float width2;
        private final Color color2;

        /**
         * crée un type de route ayant des paramètres de style donnés
         * 
         * @param pr
         *            le filtre permettant de séléctionner le type de route
         *            voulu
         * @param width1
         *            la largeur du trait intérieur
         * @param color1
         *            la couleur du trait intérieur
         * @param width2
         *            la largeur du trait extérieur
         * @param color2
         *            la couleur du trait extérieur
         */

        public RoadSpec(Predicate<Attributed<?>> pr, float width1,
                Color color1, float width2, Color color2) {
            super();
            this.pr = pr;
            this.width1 = width1;
            this.color1 = color1;
            this.width2 = width2;
            this.color2 = color2;
        }
    }
}

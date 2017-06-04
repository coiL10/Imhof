package ch.epfl.imhof.painting;

/**
 * représente un style de ligne
 * 
 * @author Karine Perrard (250605)
 * @author Loic Nguyen (238243)
 *
 *
 */
public final class LineStyle {
    private final Color color;
    private final float width;
    private final float[] dash;
    private final LineCap linecap;
    private final LineJoin linejoin;

    public enum LineCap {
        BUTT, ROUND, SQUARE
    }

    public enum LineJoin {
        MITER, BEVEL, ROUND
    }

    /**
     * crée un nouveau style de ligne ayant une largeur, couleur, terminaison de
     * ligne, jointure de segments et l'alternance des sections opaques et
     * transparentes
     * 
     * @param width
     *            la largeur de la ligne
     * @param color
     *            la couleur de la ligne
     * @param lc
     *            la terminaison des lignes
     * @param lj
     *            la jointure des segments
     * @param dash
     *            l'alternance des sections opaques et transparentes
     */

    public LineStyle(float width, Color color, LineCap lc, LineJoin lj,
            float[] dash) {
        if (width < 0) {
            throw new IllegalArgumentException(
                    "la largeur du trait est négative");
        }

        if (dash != null) {
            for (int i = 0; i < dash.length; ++i) {
                if (dash[i] <= 0) {
                    throw new IllegalArgumentException(
                            "il y a des segments négatifs");
                }
            }
        }

        this.color = color;
        this.width = width;
        this.dash = dash;
        this.linecap = lc;
        this.linejoin = lj;

    }

    /**
     * crée un nouveau style de ligne avec la largeur et la couleur donnée, et
     * ayant terminaison de ligne abrupte, une jointure de segments par
     * prolongation des cotés, et étant un trait continu
     * 
     * @param width
     *            la largeur voulue
     * @param c
     *            la couleur voulue
     */

    public LineStyle(float width, Color c) {
        this(width, c, LineCap.BUTT, LineJoin.MITER, new float[0]);
    }

    /**
     * 
     * @return la couleur du style de ligne
     */

    public Color color() {
        return color;
    }

    /**
     * 
     * @return la largeur du style de ligne
     */

    public float width() {
        return width;
    }

    /**
     * 
     * @return l'alternance du style de ligne
     */

    public float[] dash() {
        if (dash != null) {
            if (dash.length == 0) {
                return null;
            }
            float[] d = new float[dash.length];
            for (int i = 0; i < dash.length; ++i) {
                d[i] = dash[i];
            }
            return d;
        }
        return dash;
    }

    /**
     * 
     * @return la terminaison des lignes
     */

    public LineCap linecap() {
        return linecap;
    }

    /**
     * 
     * @return la jointure des lignes
     */

    public LineJoin linejoin() {
        return linejoin;
    }

    /**
     * crée un nouveau style de ligne correspondant à celui en cours, mais en
     * changeant la largeur
     * 
     * @param w
     *            la largeur voulue
     * @return le style de ligne voulu
     */

    public LineStyle withWidth(float w) {
        return new LineStyle(w, color, linecap, linejoin, dash);
    }

    /**
     * crée un nouveau style de ligne correspondant à celui en cours, mais en
     * changeant la couleur
     * 
     * @param c
     *            la couleur voulue
     * @return le style de ligne voulu
     */

    public LineStyle withColor(Color c) {
        return new LineStyle(width, c, linecap, linejoin, dash);
    }

    /**
     * crée un nouveau style de ligne correspondant à celui en cours, mais en
     * changeant la terminaison des lignes
     * 
     * @param lc
     *            la terminaison voulue
     * 
     * 
     * @return le style de ligne voulu
     */

    public LineStyle withLineCap(LineCap lc) {
        return new LineStyle(width, color, lc, linejoin, dash);
    }

    /**
     * crée un nouveau style de ligne correspondant à celui en cours, mais en
     * changeant la jointure des segments
     * 
     * @param lj
     *            la jointure voulue
     * @return le style de ligne voulu
     */

    public LineStyle withLineJoin(LineJoin lj) {
        return new LineStyle(width, color, linecap, lj, dash);
    }

    /**
     * crée un nouveau style de ligne correspondant à celui en cours, mais en
     * changeant l'alternance des sections opaques et transparentes
     * 
     * @param d
     *            l'alternance voulue
     * @return le style de ligne voulu
     */

    public LineStyle withDash(float[] d) {
        return new LineStyle(width, color, linecap, linejoin, d);
    }

}

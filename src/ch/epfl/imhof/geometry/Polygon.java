package ch.epfl.imhof.geometry;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;

/**
 * représente un polygone a trous
 * 
 * @author Karine Perrard (250605)
 * @author Loic Nguyen (238243)
 *
 *
 */

public final class Polygon {
    private final ClosedPolyLine shell;
    private final List<ClosedPolyLine> holes;

    /**
     * permet de construire un nouveau polygone avec des trous
     * 
     * @param shell
     *            représente la forme extérieure du polygone
     * @param holes
     *            représente les trous a l'intérieur du polygone
     */

    public Polygon(ClosedPolyLine shell, List<ClosedPolyLine> holes) {
        this.shell = shell;
        this.holes = Collections
                .unmodifiableList(new ArrayList<ClosedPolyLine>(holes));
    }

    /**
     * permet de créer un nouveau polygone sans trous
     * 
     * @param shell
     *            désigne la forme extérieure
     */

    public Polygon(ClosedPolyLine shell) {
        this(shell,Collections.emptyList());
    }

    /**
     * Retourne l'enveloppe
     * 
     * @return une closedpolyline qui représente la forme extérieure
     */

    public ClosedPolyLine shell() {
        return shell;
    }

    /**
     * Retourne les trous dans le polygone
     * 
     * @return une liste des trous du polygone
     */

    public List<ClosedPolyLine> holes() {
        return holes;
    }

}

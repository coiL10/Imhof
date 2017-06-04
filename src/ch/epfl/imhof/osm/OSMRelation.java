package ch.epfl.imhof.osm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ch.epfl.imhof.Attributes;

/**
 * Classe représentant une relation OSM. Hérite de OSMEntity
 * 
 * @author Loïc Nguyen (238243)
 * @author Karine Perrard (250605)
 */
public final class OSMRelation extends OSMEntity {
    private final List<Member> members;

    /**
     * Construit une relation étant donné son identifiant unique, ses membres et
     * ses attributs
     * 
     * @param id
     *            l'identifiant unique
     * @param members
     *            ses membres
     * @param attributes
     *            ses attributs
     */
    public OSMRelation(long id, List<Member> members, Attributes attributes) {
        super(id, attributes);
        this.members = Collections.unmodifiableList(new ArrayList<Member>(
                members));
    }

    /**
     * retourne la liste des membres de la relation
     * 
     * @return une liste contenant les membres de la relation
     */
    public List<Member> members() {
        return members;
    }

    /**
     * Classe imbriquée dans OSMRelation représentant un membre d'une relation
     * OSM.
     * 
     * @author Loïc Nguyen (238243)
     * @author Karine Perrard (250605)
     */
    public static final class Member {
        private final Type type;
        private final String role;
        private final OSMEntity member;

        /**
         * Construit un membre ayant le type, le role, et la valeur donnés
         * 
         * @param type
         *            le type
         * @param role
         *            le role
         * @param member
         *            la valeur donnée
         */
        public Member(Type type, String role, OSMEntity member) {
            this.type = type;
            this.role = role;
            this.member = member;
        }

        /**
         * @return retourne le type du membre
         */
        public Type type() {
            return type;
        }

        /**
         * @return retourne le role du membre
         */
        public String role() {
            return role;
        }

        /**
         * @return retourne le membre lui-même
         */
        public OSMEntity member() {
            return member;
        }

        /**
         * Enumération qui énumère les 3 types de membres qu'une relation peut
         * comporter: NODE, WAY et RELATION
         * 
         * @author Loïc Nguyen (238243)
         * @author Karine Perrard (250605)
         */
        public enum Type {
            NODE, WAY, RELATION
        }

    }

    /**
     * Batisseur de la classe OSMRelation, hérite du batisseur de OSMEntity
     * 
     * @author Loïc Nguyen (238243)
     * @author Karine Perrard (250605)
     */
    public static final class Builder extends OSMEntity.Builder {

        private final List<Member> members;

        /**
         * Construit un bâtisseur pour une relation ayant l'identifiant donné
         * 
         * @param id
         *            l'identifiant
         */
        public Builder(long id) {
            super(id);
            members = new ArrayList<Member>();
        }

        /**
         * Ajoute un nouveau membre de type et de role donné à la relation
         * 
         * @param type
         *            le type du membre donné
         * @param role
         *            le role du membre donné
         * @param newMember
         *            le membre en question à ajouter
         */
        public void addMember(Member.Type type, String role, OSMEntity newMember) {
            members.add(new Member(type, role, newMember));
        }

        /**
         * construit et retourne la relation l'identifiant passé au constructeur
         * avec les membres et les attributs ajoutés au batisseur. Lève
         * IllegalStateException si la relation en cours et incomplète
         * 
         * @return la relation construite
         */
        public OSMRelation build() {
            if (isIncomplete()) {
                throw new IllegalStateException("La relation n'est pas finie");
            }
            return new OSMRelation(super.getid(), members, super
                    .getAttributes().build());
        }
    }

}

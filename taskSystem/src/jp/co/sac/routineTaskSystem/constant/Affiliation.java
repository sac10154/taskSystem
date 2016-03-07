package jp.co.sac.routineTaskSystem.constant;

/**
 * 所属クラス
 *
 * @author shogo_saito
 */
public class Affiliation {

    private Section section;
    private Group group;
    private Team team;

    public Affiliation() {
        this(Section.none, Group.none, Team.none);
    }

    public Affiliation(Section section, Group group) {
        this(section, group, Team.none);
    }

    public Affiliation(Section section, Group group, Team team) {
        this.section = section;
        this.group = group;
        this.team = team;
    }

    public enum Section {

        none,
        manage,
        first,
        second,
        third,
        fourth,
        other;

        public static String ToString(Section section) {
            if(section == null) {
                 return null;
             }
             return section.toString();
        }
    }

    public enum Group {

        none,
        first,
        second,
        third,
        fourth;

        public static String ToString(Group group) {
            if(group == null) {
                 return null;
             }
             return group.toString();
        }
    }

    public enum Team {

        none,
        first,
        second,
        third,
        fourth;
        
         public static String ToString(Team team) {
             if(team == null) {
                 return null;
             }
             return team.toString();
        }
    }

    public Section getSection() {
        return section;
    }

    public Group getGroup() {
        return group;
    }

    public Team getTeam() {
        return team;
    }
}

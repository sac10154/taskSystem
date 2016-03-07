package jp.co.sac.routineTaskSystem.common;

import jp.co.sac.routineTaskSystem.entity.staff.Staff;

/**
 *
 * @author shogo_saito
 */
public class Normalizer {

    private Normalizer() {
    }

    public static String getNameNoSpace(Staff staff) {
        return getName(staff, "");
    }

    public static String getName(Staff staff) {
        return getName(staff, " ");
    }

    public static String getName(Staff staff, String div) {
        return staff.getLastName() + div + staff.getFirstName();
    }

    public static String[] convDest(String value, String[] divArr) {
        if (value == null || divArr == null) {
            return new String[]{value};
        }
        for (String div : divArr) {
            String[] convValue = value.split(div, -1);
            if (!value.equals(convValue[0])) {
                return convValue;
            }
        }
        return new String[]{value};
    }
}

package jp.co.sac.routineTaskSystem.entity.findings;

import java.io.Serializable;

/**
 * 勤務表指摘事項
 *
 * @author shogo_saito
 */
public class RosterFindings extends Findings implements Serializable {

    public RosterFindings() {
        super();
    }

    public RosterFindings(String message) {
        super(message);
    }

    public RosterFindings(String message, int idx) {
        this(String.format(message, idx + 1));
    }

    public RosterFindings(String message, String value) {
        this(String.format(message, value));
    }

    public RosterFindings(String message, int idx, String value) {
        this(String.format(message, idx + 1, value));
    }

    @Deprecated
    public RosterFindings(String message, Object... values) {
        this(String.format(message, values));
    }
}

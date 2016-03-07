package jp.co.sac.routineTaskSystem.check;

import java.util.List;
import jp.co.sac.routineTaskSystem.check.checkLogic.RosterCheck;
import jp.co.sac.routineTaskSystem.entity.document.Document;
import jp.co.sac.routineTaskSystem.entity.document.RosterDocument;
import jp.co.sac.routineTaskSystem.entity.findings.Findings;
import jp.co.sac.routineTaskSystem.entity.staff.Staff;


/**
 *
 * @author shogo_saito
 */
public class CheckerProtoType<T extends Document> extends Checker<T> {

    public CheckerProtoType() {
    }

    @Override
    public List<Findings> doCheck(T document, Staff inspector) {
        if (document instanceof RosterDocument) {
            return new RosterCheck<>().doCheck((RosterDocument) document, inspector);
        } else {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}

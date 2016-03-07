package jp.co.sac.routineTaskSystem.check;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jp.co.sac.routineTaskSystem.entity.document.Document;
import jp.co.sac.routineTaskSystem.entity.findings.Findings;
import jp.co.sac.routineTaskSystem.entity.findings.RosterFindings;
import jp.co.sac.routineTaskSystem.entity.staff.Staff;

/**
 * 書類チェック
 *
 * @author shogo_saito
 */
public abstract class Checker<T extends Document> {

    public Map<T, List<Findings>> check(List<T> documents) {
        return check(documents, null);
    }

    public Map<T, List<Findings>> check(List<T> documents, Staff inspector) {
        Map<T, List<Findings>> map = new HashMap<>();
        for(T document : documents) {
            try {
                map.put(document, doCheck(document, inspector));
            } catch(UnsupportedOperationException ex) {
                List<Findings> findList = new ArrayList<>();
                findList.add(new RosterFindings(ex.getMessage()));
                map.put(document, findList);
            }
        }
        return map;
    }

    protected abstract List<Findings> doCheck(T document, Staff inspector);

}

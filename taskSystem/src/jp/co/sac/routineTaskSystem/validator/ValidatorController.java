package jp.co.sac.routineTaskSystem.validator;

import jp.co.sac.routineTaskSystem.validator.impl.RosterValidator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jp.co.sac.routineTaskSystem.entity.document.Document;
import jp.co.sac.routineTaskSystem.entity.document.RosterDocument;
import jp.co.sac.routineTaskSystem.entity.findings.Findings;
import jp.co.sac.routineTaskSystem.entity.staff.Staff;

/**
 *
 * @author shogo_saito
 */
public class ValidatorController {

    private static RosterValidator rosterValidator = new RosterValidator();

    public static Map<Document, List<Findings>> doValidate(List<Document> documents) {
        return doValidate(documents, null);
    }

    public static Map<Document, List<Findings>> doValidate(List<Document> documents, Staff inspector) {
        Map<Document, List<Findings>> docsMap = new HashMap<>();
        if (documents != null && !documents.isEmpty()) {
            for (Document document : documents) {
                docsMap.put(document, doValidate(document, inspector));
            }
        }
        return docsMap;
    }

    public static List<Findings> doValidate(Document document) {
        return doValidate(document, null);
    }

    public static List<Findings> doValidate(Document document, Staff inspector) {
        if (document == null) {
            List<Findings> finds = new ArrayList<>(1);
            finds.add(new Findings("チェックするファイルがありません"));
            return finds;
        }
        if (document instanceof RosterDocument) {
            return rosterValidator.doValidate((RosterDocument)document, inspector);
        } else {
            List<Findings> finds = new ArrayList<>(1);
            finds.add(new Findings("not supported yet."));
            return finds;
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.co.sac.routineTaskSystem.check.checkLogic;

import java.util.ArrayList;
import java.util.List;
import jp.co.sac.routineTaskSystem.entity.document.Document;
import jp.co.sac.routineTaskSystem.entity.findings.Findings;
import jp.co.sac.routineTaskSystem.entity.staff.Staff;

/**
 *
 * @author shogo_saito
 */
public abstract class CheckLogic<T extends Document> {

    private Findings noFile;

    protected CheckLogic(Findings noFile) {
        this.noFile = noFile;
    }

    /**
     * 書類チェック
     * 
     * @param document 点検対象書類
     * @return 指摘事項リスト
     */
    public final List<Findings> doCheck(T document) {
        return doCheck(document, null);
    }

    /**
     * 書類チェック
     * 
     * @param document 点検対象書類
     * @param inspector 点検者 ×書類の記入者
     * @return 指摘事項リスト
     */
    public final List<Findings> doCheck(T document, Staff inspector) {
        List<Findings> finds = new ArrayList<>();
        finds.addAll(existDocument(document));
        if (finds.isEmpty()) {
            finds.addAll(validateDocumentName(document, inspector));
            finds.addAll(validateRegistrant(document, inspector));
            finds.addAll(validateContents(document, inspector));
        }
        return finds;
    }

    /**
     * 書類存在チェック
     * 
     * @param document
     * @return 指摘事項
     */
    protected List<Findings> existDocument(T document) {
        List<Findings> finds = new ArrayList<>();
        if (document == null) {
            finds.add(noFile);
        } else {
            if (document.getFileError() != null) {
                finds.addAll(getFindingsOfFileError(document));
            }
        }
        return finds;
    }

    /**
     * 書類ファイル名チェック
     * 
     * @param document
     * @param staff
     * @return 指摘事項
     */
    protected abstract List<Findings> validateDocumentName(T document, Staff staff);

    /**
     * 共通不備事項チェック
     * 
     * @param document
     * @param staff
     * @return 指摘事項
     */
    protected abstract List<Findings> validateRegistrant(T document, Staff staff);

    /**
     * 記入内容不備チェック
     * 
     * @param document
     * @param staff
     * @return 指摘事項
     */
    protected abstract List<Findings> validateContents(T document, Staff staff);

    protected abstract List<Findings> getFindingsOfFileError(T document);
}

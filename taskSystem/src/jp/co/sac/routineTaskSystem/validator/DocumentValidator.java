/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.co.sac.routineTaskSystem.validator;

import java.util.List;
import jp.co.sac.routineTaskSystem.entity.document.Document;
import jp.co.sac.routineTaskSystem.entity.findings.Findings;
import jp.co.sac.routineTaskSystem.entity.staff.Staff;

/**
 *
 * @author shogo_saito
 */
public interface DocumentValidator<T extends Document> {

    /**
     * 書類チェック
     * 
     * @param document 点検対象書類
     * @return 指摘事項リスト
     */
    public List<Findings> doValidate(T document);

    /**
     * 書類チェック
     * 
     * @param document 点検対象書類
     * @param inspector 点検者 ×書類の記入者
     * @return 指摘事項リスト
     */
    public List<Findings> doValidate(T document, Staff inspector);

    /**
     * 書類存在チェック
     * 
     * @param document
     * @return 指摘事項
     */
    public List<Findings> existDocument(T document);

    /**
     * 書類ファイル名チェック
     * 
     * @param document
     * @param staff 点検者
     * @return 指摘事項
     */
    public List<Findings> validateDocumentName(T document, Staff inspector);

    /**
     * 共通不備事項チェック
     * 
     * @param document
     * @param staff 点検者
     * @return 指摘事項
     */
    public List<Findings> validateRegistrant(T document, Staff inspector);

    /**
     * 記入内容不備チェック
     * 
     * @param document
     * @param staff 点検者
     * @return 指摘事項
     */
    public List<Findings> validateContents(T document, Staff inspector);

    /**
     * ファイルエラーの取得
     * 
     * @param document
     * @return 指摘事項
     */
    public List<Findings> getFindingsOfFileError(T document);
}

package jp.co.sac.routineTaskSystem.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jp.co.sac.routineTaskSystem.entity.document.Document;
import jp.co.sac.routineTaskSystem.entity.findings.Findings;
import jp.co.sac.routineTaskSystem.entity.staff.Staff;
import jp.co.sac.routineTaskSystem.manage.document.DocumentManager;
import jp.co.sac.routineTaskSystem.manage.document.RosterManager;
import jp.co.sac.routineTaskSystem.validator.DocumentValidator;
import jp.co.sac.routineTaskSystem.validator.impl.RosterValidator;

/**
 * 書類操作の窓口クラス
 * 書類のインスタンスと対応する操作を呼び出す
 *
 * @author shogo_saito
 */
public class DocumentController {

    //勤務表マネージャ
    private DocumentManager rosterManager = new RosterManager();
    //勤務表バリデータ
    private DocumentValidator rosterValidator = new RosterValidator();

    /**
     * 書類の読み込み
     * 
     * @param filePath 書類のファイルパス
     * @return 書類
     * @throws Exception 想定なし
     */
    public Document load(String filePath) throws Exception {
        return rosterManager.load(filePath);
    }

    /**
     * 書類の内容チェック
     * 
     * @param documents リスト(書類)
     * @param inspector 点検者
     * @return マップ(書類, リスト(指摘事項))
     */
    public Map<Document, List<Findings>> validateDocuments(List<Document> documents, Staff inspector) {
        Map<Document, List<Findings>> map = new HashMap<>();
        if (documents != null) {
            for (Document doc : documents) {
                map.put(doc, rosterValidator.doValidate(doc, inspector));
            }
        }
        return map;
    }
}

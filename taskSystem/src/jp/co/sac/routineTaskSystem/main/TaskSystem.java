package jp.co.sac.routineTaskSystem.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import jp.co.sac.routineTaskSystem.common.DataUtil;
import jp.co.sac.routineTaskSystem.log.Output;
import jp.co.sac.routineTaskSystem.constant.Const;
import jp.co.sac.routineTaskSystem.entity.document.Document;
import jp.co.sac.routineTaskSystem.manage.document.DocumentManager;
import jp.co.sac.routineTaskSystem.manage.document.RosterManager;
import jp.co.sac.routineTaskSystem.validator.ValidatorController;
import org.apache.log4j.Logger;

/**
 *
 * @author shogo_saito
 */
public class TaskSystem {

    private static Logger log = Logger.getLogger("root");
    private static int EXIT_STATUS = 0;
    private static int EXIT_STATUS_EXCEPTION = 1;
    private static int EXIT_STATUS_CHECKERR = -1;

    public static void main(String[] args) throws Exception {
        try {
            try {

                Output.getInstance().println(" *** 勤務表チェックシステム *** ");
                List<Document> docs = new ArrayList<>();
                DocumentManager docMgr = new RosterManager();
                if (Const.isDev()) {
                    //テスト
                    docs.add(docMgr.load("C:\\Users\\shogo_saito\\Documents\\勤務表\\201601sac10323.xls"));
                } else {
                    if (args != null && args.length != 0) {
                        for (String arg : args) {
                            if (DataUtil.isFilePath(arg)) {
                                docs.add(docMgr.load(arg));
                            } else {
                                Output.getInstance().println(" *** [" + arg + "]ファイルを確認してください *** ");
                            }
                        }
                    } else {
                        Output.getInstance().println("ファイルが選択されていません");
                    }
                }

                Output.getInstance().printForDebug(docs);
                Map docsMap = ValidatorController.doValidate(docs);
                Output.getInstance().print(docsMap);

                //チェックエラーがある場合は終了コードを-1にする。
                if (DataUtil.hasNotValidDocument(docsMap)) {
                    EXIT_STATUS = EXIT_STATUS_CHECKERR;
                }

            } catch (Exception e) {
                //終了コードを設定
                EXIT_STATUS = EXIT_STATUS_EXCEPTION;
                Output.getInstance().println(" *** エラーが発生しました。詳細はエラーログを参照してください。 *** ");
                log.warn("エラー詳細 -> \n", e);
            } finally {
                Output.getInstance().println(" *** 終了 *** ");
            }
        } finally {
            //システムを指定コードで終了
            System.exit(EXIT_STATUS);
        }
    }
}

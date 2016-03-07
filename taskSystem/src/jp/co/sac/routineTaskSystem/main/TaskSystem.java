package jp.co.sac.routineTaskSystem.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import jp.co.sac.routineTaskSystem.check.Checker;
import jp.co.sac.routineTaskSystem.check.CheckerProtoType;
import jp.co.sac.routineTaskSystem.check.checkLogic.CheckLogic;
import jp.co.sac.routineTaskSystem.check.checkLogic.RosterCheck;
import jp.co.sac.routineTaskSystem.common.DataUtil;
import jp.co.sac.routineTaskSystem.config.GeneralConfig;
import jp.co.sac.routineTaskSystem.console.Output;
import jp.co.sac.routineTaskSystem.entity.document.Document;
import jp.co.sac.routineTaskSystem.manage.document.DocumentManager;
import jp.co.sac.routineTaskSystem.manage.document.RosterManager;

/**
 *
 * @author shogo_saito
 */
public class TaskSystem {

    public static void main(String[] args) {

        try {
            Output.getInstance().println(" *** 勤務表チェックシステム *** ");
            if (GeneralConfig.isDebug()) {
                Output.getInstance().println(" - DEBUG_MODE = " + GeneralConfig.isDebug() + " - ");
            }
            List<Document> docs = new ArrayList<>();
            DocumentManager docMgr = new RosterManager();
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
            Checker checker = new CheckerProtoType();
            Output.getInstance().printForDebug(docs);
            Map docsMap = checker.check(docs);
            Output.getInstance().print(docsMap);
        } catch (Exception e) {
            Output.getInstance().println(" *** エラーが発生しました *** ");
            if (GeneralConfig.isDebug()) {
                Output.getInstance().println(e.getMessage());
            }
        }
        Output.getInstance().println(" *** 終了 *** ");
    }
}

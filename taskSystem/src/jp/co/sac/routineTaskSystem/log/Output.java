package jp.co.sac.routineTaskSystem.log;

import java.util.List;
import java.util.Map;
import jp.co.sac.routineTaskSystem.config.GeneralConfig;
import jp.co.sac.routineTaskSystem.entity.document.Document;
import jp.co.sac.routineTaskSystem.entity.findings.Findings;
import org.apache.log4j.Logger;

/**
 * 画面表示機能を集約
 * log4jを利用して標準エラー出力として表示する。
 * 表示先などの設定は設定ファイルを使う。
 *
 * @author shogo_saito
 */
public class Output {

    private static Output instance = new Output();
    protected static final Logger log = Logger.getLogger("Output");
    protected static final Logger debugLog = Logger.getLogger("root");

    private Output() {
    }

    public static Output getInstance() {
        return instance;
    }

    public void print() {
    }

    public void print(Object o) {
//        System.out.print(o);
        log.info(String.valueOf(o));
    }

    public void println() {
//        System.out.println();
        print(String.valueOf(getLineSeparator()));
    }

    public void println(Object o) {
//        System.out.println(o);
        print(String.valueOf(o) + getLineSeparator());
    }

    public void print(Map<Document, List<Findings>> map) {
        print(map, GeneralConfig.getNumberOfShowFinds() == 0);
    }

    public void print(Map<Document, List<Findings>> map, boolean showAll) {
        String result = createShowCheckResult(map, showAll);
        if (GeneralConfig.isOutputMsgBoxMode()) {
            showMessage(result);
        }
        println(result);
    }

    private String createShowCheckResult(Map<Document, List<Findings>> map, boolean showAll) {
        StringBuilder sb = new StringBuilder();
        int maxPrintCount = GeneralConfig.getNumberOfShowFinds();
        sb.append("[チェック結果]").append(getLineSeparator());
        if (map.isEmpty()) {
            sb.append(" - no file... - ").append(getLineSeparator());
        } else {
            for (Document document : map.keySet()) {
                sb.append(getLineSeparator());
                sb.append(" - ").append(document.getPrintTitle()).append(" - ").append(getLineSeparator());
                int prtCount = 0;
                for (Findings find : map.get(document)) {
                    if (!showAll && maxPrintCount > 0 && prtCount >= maxPrintCount) {
                        sb.append("and more... ").append(getLineSeparator());
                        break;
                    }
                    sb.append(find.getMessage()).append(getLineSeparator());
                    prtCount++;
                }
                if (prtCount > 0) {
                    sb.append(" - エラー箇所 : ").append(map.get(document).size()).append(" - ").append(getLineSeparator());
                }
            }
        }
        return sb.toString();
    }

    public void showMessage(String message) {
        try {
            ShowMessage showMessage = new ShowMessage();
            showMessage.setMessage(message);
            showMessage.start();
        } catch (Exception ex) {
            log.warn(message);
        }
    }

    public void showDocumentForDebug(List<Document> docs) {
        for (Document document : docs) {
            showMessage(document.getPrintAllForDebug());
        }
    }

    public void printForDebug(List<Document> docs) {
        for (Document document : docs) {
            debugLog.debug(document.getPrintAllForDebug());
        }
    }

    private String getLineSeparator() {
        return System.lineSeparator();
    }
}

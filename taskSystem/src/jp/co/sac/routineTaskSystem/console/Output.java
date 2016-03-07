package jp.co.sac.routineTaskSystem.console;

import java.util.List;
import java.util.Map;
import jp.co.sac.routineTaskSystem.config.GeneralConfig;
import jp.co.sac.routineTaskSystem.entity.document.Document;
import jp.co.sac.routineTaskSystem.entity.findings.Findings;

/**
 * 画面表示機能を集約
 *
 * @author shogo_saito
 */
public class Output {

    private static Output instance = new Output();

    private Output() {
        ready();
    }

    public static Output getInstance() {
        return instance;
    }

    private void ready() {
    }

    public void print() {
    }

    public void print(Object o) {
        System.out.print(o);
    }

    public void println() {
        System.out.println();
    }

    public void println(Object o) {
        System.out.println(o);
    }

    public void print(Map<Document, List<Findings>> map) {
        print(map, GeneralConfig.getShowAllFindings());
    }

    public void print(Map<Document, List<Findings>> map, boolean showAll) {
        StringBuilder sb = new StringBuilder();
        if (GeneralConfig.isOutputMsgBoxMode()) {
            sb.append("[チェック結果]").append(getLineSeparator());
            if (map.isEmpty()) {
                sb.append(" - no file... - ").append(getLineSeparator());
            } else {
                for (Document document : map.keySet()) {
                    sb.append(getLineSeparator());
                    sb.append(" - ").append(document.getPrintTitle()).append(" - ").append(getLineSeparator());
                    int prtCount = 0;
                    for (Findings find : map.get(document)) {
                        if (!showAll && prtCount >= 3) {
                            sb.append("and more... ").append(getLineSeparator());
                            break;
                        }
                        sb.append(find.getMessage()).append(getLineSeparator());
                        prtCount++;
                    }
                    if (prtCount > 0) {
                        sb.append(" - total : ").append(map.get(document).size()).append(" - ").append(getLineSeparator());
                    }
                }
            }
            showMessage(sb.toString());
        } else {
            for (Document document : map.keySet()) {
                sb.append(getLineSeparator());
                sb.append(" * チェック開始！ * ").append(getLineSeparator());
                sb.append(document.getPrintTitle()).append(getLineSeparator());
                int prtCount = 0;
                for (Findings find : map.get(document)) {
                    if (!showAll && prtCount >= 10) {
                        sb.append("and more... ").append(getLineSeparator());
                        break;
                    }
                    sb.append(find.getMessage()).append(getLineSeparator());
                    prtCount++;
                }
                if (prtCount > 0) {
                    sb.append(" *** total : ").append(map.get(document).size()).append(" *** ").append(getLineSeparator());
                }
                sb.append(" * チェック完了！ * ").append(getLineSeparator());
            }
            println(sb.toString());
        }
    }

    public void showMessage(String message) {
        try {
            ShowMessage showMessage = new ShowMessage();
            showMessage.setMessage(message);
            showMessage.start();
        } catch (Exception ex) {
        }
    }

    public void showDocumentForDebug(List<Document> docs) {
        if (!GeneralConfig.isDebug()) {
            return;
        }
        for (Document document : docs) {
            showMessage(document.getPrintAllForDebug());
        }
    }

    public void printForDebug(List<Document> docs) {
        if (!GeneralConfig.isDebug()) {
            return;
        }
        for (Document document : docs) {
            println(document.getPrintAllForDebug());
        }
    }

    private String getLineSeparator() {
        return System.lineSeparator();
    }
}

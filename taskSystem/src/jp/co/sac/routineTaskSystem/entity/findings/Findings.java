package jp.co.sac.routineTaskSystem.entity.findings;

/**
 * 指摘事項クラス
 * 書類のバリデート情報を保持
 *
 * @author shogo_saito
 */
public class Findings {

    //指摘事項
    private String message;

    public Findings() {
        this.message = null;
    }

    public Findings(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

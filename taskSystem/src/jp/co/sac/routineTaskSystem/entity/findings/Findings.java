package jp.co.sac.routineTaskSystem.entity.findings;

/**
 * 指摘事項クラス
 *
 * @author shogo_saito
 */
public class Findings {

    //指摘事項
    private String message;

    protected Findings() {
        this.message = null;
    }

    protected Findings(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

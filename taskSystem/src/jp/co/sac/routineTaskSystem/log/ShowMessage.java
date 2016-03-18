package jp.co.sac.routineTaskSystem.log;

import javax.swing.JOptionPane;

/**
 *
 * @author shogo_saito
 */
public class ShowMessage extends Thread {

    private String message = null;

    @Override
    public void run() {
        JOptionPane.showMessageDialog(null, message);
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

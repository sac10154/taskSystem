package jp.co.sac.routineTaskSystem.config;

import java.io.File;
import jp.co.sac.routineTaskSystem.constant.Const;

/**
 * 個人設定読み込みクラス
 *
 * @author shogo_saito
 */
public class GeneralConfig {

    private static String CONFIG_PATH;
    private static String trueString = "T";
    private static String numberOfShowFindsKey = "numberOfShowFinds";
    private static int numberOfShowFinds = 0;
    private static boolean OutputMsgBox = false;
    private static String OutputMsgBoxKey = "OutputMsgBox";
    private static boolean checkSuperSign = false;
    private static String checkSuperSignKey = "Check-SuperSign";
    private static String userId = "";
    private static String userIdKey = "sUserId";
    private static String outputPath = Const.getRootPath();
    private static String outputPathKey = "outputPathKey";
    private PropertyManager manager;
    private GeneralConfig instance = new GeneralConfig();

    private GeneralConfig() {
        CONFIG_PATH = Const.getRootPath() + File.separator + "settings.properties";
        manager = new PropertyManager(CONFIG_PATH);
        prepare();
    }

    /**
     * 初期読み込み
     */
    private void prepare() {
        OutputMsgBox = manager.getBoolean(OutputMsgBoxKey, OutputMsgBox, trueString);
        checkSuperSign = manager.getBoolean(checkSuperSignKey, checkSuperSign, trueString);
        userId = manager.getString(userIdKey, userId);
        outputPath = manager.getString(outputPath, outputPathKey);
        numberOfShowFinds = manager.getInt(numberOfShowFindsKey, numberOfShowFinds);
    }

    public static boolean isOutputMsgBoxMode() {
        return OutputMsgBox;
    }

    public static boolean needCheckSuperSign() {
        return checkSuperSign;
    }

    public static String getUserId() {
        return userId;
    }

    public static String getOutputPath() {
        return outputPath;
    }

    public static int getNumberOfShowFinds() {
        return numberOfShowFinds;
    }
}

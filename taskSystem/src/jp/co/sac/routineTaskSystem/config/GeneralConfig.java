/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.co.sac.routineTaskSystem.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import jp.co.sac.routineTaskSystem.console.Output;

/**
 *
 * @author shogo_saito
 */
public class GeneralConfig {

    private static String CONFIG_PATH = "settings.properties";
    private static String trueString = "T";
    private static boolean thisOK = false;
    private static boolean showAllFindings = false;
    private static String showAllFindingsKey = "Finding-ALL";
    private static boolean debugMode = false;
    private static String debugModeKey = "Debug-MODE";
    private static boolean OutputMsgBox = false;
    private static String OutputMsgBoxKey = "OutputMsgBox";
    private static boolean checkSuperSign = false;
    private static String checkSuperSignKey = "Check-SuperSign";
    private Properties resource;

    private static GeneralConfig instance = new GeneralConfig();

    private GeneralConfig() {
        load();
        if (thisOK) {
            prepare();
        }
    }

    private void load() {
        try {
            String jarPath = System.getProperty("java.class.path");
            String dirPath = jarPath.substring(0, jarPath.lastIndexOf(File.separator) + 1);
            //IDEで実行時にファイルパスがずれるので、ファイルパスを変更する
            if (dirPath.indexOf(";") > 0) {
                dirPath = System.getProperty("user.dir") + "\\";
            }
            resource = new Properties();
            InputStream inputStream = new FileInputStream(new File(dirPath + CONFIG_PATH));
            resource.load(inputStream);
            thisOK = true;
        } catch (Exception ex) {
            thisOK = false;
            Output.getInstance().println(" * プロパティファイルを読み込めませんでした。");
            Output.getInstance().println(" * デフォルト設定で起動します。");
        }
    }

    private void prepare() {
        showAllFindings = getBoolean(showAllFindingsKey, showAllFindings);
        debugMode = getBoolean(debugModeKey, debugMode);
        OutputMsgBox = getBoolean(OutputMsgBoxKey, OutputMsgBox);
        checkSuperSign = getBoolean(checkSuperSignKey, checkSuperSign);
    }

    private boolean getBoolean(String key, boolean defBool) {
        try {
            return trueString.equals(resource.get(key));
        } catch (Exception ex) {
            return defBool;
        }
    }

    public static boolean getShowAllFindings() {
        return showAllFindings;
    }

    public static boolean isDebug() {
        return debugMode;
    }

    public static boolean isOutputMsgBoxMode() {
        return OutputMsgBox;
    }

    public static boolean needCheckSuperSign() {
        return checkSuperSign;
    }
}

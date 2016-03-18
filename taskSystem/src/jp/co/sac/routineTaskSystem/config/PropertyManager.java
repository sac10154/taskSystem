package jp.co.sac.routineTaskSystem.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import jp.co.sac.routineTaskSystem.log.Output;

/**
 * プロパティファイル操作クラス
 *
 * @author shogo_saito
 */
public class PropertyManager {
    
    private boolean thisOK = false;
    private Properties resource;

    public PropertyManager(String fileName) {
        load(fileName);
    }

    private void load(String filePath) {
        try {
            resource = new Properties();
            try (InputStream inputStream = new FileInputStream(new File(filePath))) {
                resource.load(inputStream);
                thisOK = true;
            }
        } catch (Exception ex) {
            Output.getInstance().println(" * [" + filePath + "]を読み込めませんでした。");
        }
    }

    public boolean getBoolean(String key, boolean defBool, String trueString) {
        try {
            return trueString.equals(resource.get(key));
        } catch (Exception ex) {
            return defBool;
        }
    }

    public String getString(String key, String defString) {
        String ret = getString(key);
        if (ret == null || ret.isEmpty()) {
            return defString;
        } else {
            return ret;
        }
    }

    public String getString(String key) {
        try {
            Object value = resource.get(key);
            if (value == null) {
                return null;
            }
            return resource.get(key).toString();
        } catch (Exception ex) {
            return null;
        }
    }

    public int getInt(String key, int defInt) {
        if (getString(key) != null) {
            try {
                return Integer.valueOf(getString(key));
            } catch (NumberFormatException ex) {
            }
        }
        return defInt;
    }
}

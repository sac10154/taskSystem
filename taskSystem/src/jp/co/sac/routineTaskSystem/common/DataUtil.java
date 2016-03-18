package jp.co.sac.routineTaskSystem.common;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jp.co.sac.routineTaskSystem.config.GeneralConfig;
import jp.co.sac.routineTaskSystem.log.Output;
import jp.co.sac.routineTaskSystem.constant.Const;
import jp.co.sac.routineTaskSystem.constant.RosterConst;
import jp.co.sac.routineTaskSystem.entity.document.Document;
import jp.co.sac.routineTaskSystem.entity.findings.Findings;
import jp.co.sac.routineTaskSystem.manage.excel.SheetMap;

/**
 *
 * @author shogo_saito
 */
public final class DataUtil {
    private static Pattern ROSTER_NAME_PATTERN = Pattern.compile(RosterConst.FILE_NAME_REGEX);
    public static Pattern ROSTER_NAME_PATTERN_PRE = Pattern.compile(RosterConst.FILE_NAME_REGEX_PRE);
    private static SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat(Const.DATE_PATTERN);

    public static boolean isNullOrEmpty(Object[] object) {
        return (object == null || object.length == 0);
    }

    public static boolean isNullOrEmpty(List object) {
        return (object == null || object.isEmpty());
    }

    public static boolean isNullOrEmpty(String object) {
        return (object == null || object.isEmpty());
    }

    public static boolean isNullOrZero(Integer object) {
        return (object == null || object.equals(0));
    }

    public static boolean isNullOrZero(Long object) {
        return (object == null || object.equals(0L));
    }

    public static boolean isFilePath(String object) {
        return object == null ? false : object.lastIndexOf(".") > 0;
    }

    public static boolean isNumeric(String object) {
        Matcher matcher = Const.getNumericPattern().matcher(object);
        return matcher.matches();
    }

    public static boolean isDocument(Object object) {
        if (object != null && object instanceof Document) {
            return true;
        }
        return false;
    }

    public static boolean isRosterDocumentName(String fileName) {
        if (fileName == null) {
            return false;
        }
        String convName = convertToFileNameFromFilePath(fileName);
        Matcher matcher = ROSTER_NAME_PATTERN.matcher(convName);
        Matcher matcherPre = ROSTER_NAME_PATTERN_PRE.matcher(convName);
        return matcher.matches() || matcherPre.matches();
    }

    public static String convertToFileNameFromFilePath(String filePath) {
        if (filePath == null) {
            return filePath;
        }
        String fileNamePlus = convertToFileNameWithExtensionFromFilePath(filePath);
        int lastIndex = fileNamePlus.indexOf(".");
        if (lastIndex > 0) {
            return fileNamePlus.substring(0, lastIndex);
        }
        return filePath;
    }

    public static String convertToFileNameWithExtensionFromFilePath(String filePath) {
        if (filePath == null) {
            return filePath;
        }
        int headIndex = filePath.lastIndexOf("\\");
        if (headIndex > 0) {
            return filePath.substring(filePath.lastIndexOf("\\") + 1);
        }
        return filePath;
    }

    public static String convertToIntStringFromDoubleString(String value) {
        if (value != null && value.lastIndexOf(".") > 0) {
            return value.substring(0, value.lastIndexOf("."));
        } else {
            return value;
        }
    }

    public static String convertToTimeStringFromDateForRoster(Date value) {
        if (value == null) {
            return null;
        }
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(value);
            return String.format(RosterConst.FORMAT_TIME, calendar);
        } catch (Exception ex) {
            return null;
        }
    }

    public static Date convertToDateFromString(String value) {
        if (value == null) {
            return null;
        }
        try {
            return DEFAULT_DATE_FORMAT.parse(value);
        } catch (ParseException ex) {
            Output.getInstance().println(ex.getMessage());
            return null;
        }
    }

    public static String getExtensionFromFilePath(String filePath) {
        if (filePath == null) {
            return filePath;
        }
        int periodIndex = filePath.lastIndexOf(".");
        if (periodIndex > 0 && periodIndex < filePath.length()) {
            return filePath.substring(periodIndex + 1);
        }
        return filePath;
    }

    public static String getFilePath(Document document) {
        if (document == null) {
            return null;
        }
        String ret;
        if (isNullOrEmpty(document.getDirPath())) {
            ret = GeneralConfig.getOutputPath();
        } else {
            ret = document.getDirPath();
        }
        return ret + File.separator + document.getFileNameAndExtension();
    }

    public static int getMaxDayOfMonth(int month) {
        return getMaxDayOfMonth(null, month);
    }

    public static int getMaxDayOfMonth(Integer year, int month) {
        try {
            Calendar cal = Calendar.getInstance();
            if (year == null) {
                year = cal.get(Calendar.YEAR);
            }
            cal.set(year, month, 1);
            return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        } catch (Exception ex) {
            return Const.MAX_DAY;
        }
    }

    public static int getDayOfWeekInt(Integer year, Integer month, int day) {
        Calendar cal = Calendar.getInstance();
        if (year == null) {
            year = cal.get(Calendar.YEAR);
        }
        if (month == null) {
            month = cal.get(Calendar.MONTH);
        }
        cal.set(year, month, day);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    public static String getDayOfWeek(Integer year, Integer month, int day) {
        return getDayOfWeek(year, month, day, true);
    }
    
    public static String getDayOfWeek(Integer year, Integer month, int day, boolean isLongDay) {
        Calendar cal = Calendar.getInstance();
        if (year == null) {
            year = cal.get(Calendar.YEAR);
        }
        if (month == null) {
            month = cal.get(Calendar.MONTH);
        }
        cal.set(year, month, day);
        return convertStringDayOfWeek(Calendar.DAY_OF_WEEK, isLongDay);
    }

    public static String convertStringDayOfWeek(int dayOfWeek, boolean isLongDay) {
        if (isLongDay) {
            return Const.getLongDay(dayOfWeek);
        } else {
            return Const.getShortDay(dayOfWeek);
        }
    }

    public static boolean isWeekEnd(int dayOfWeek) {
        return Const.isWeekEnd(dayOfWeek);
    }

    /**
     * 営業日判定
     * 
     * @param dayOfWeek
     * @return 
     */
    public static boolean isBusinessDay(int dayOfWeek) {
        return !isWeekEnd(dayOfWeek);
    }

    public static String toString(Object object) {
        return object == null ? null : object.toString();
    }

    public static boolean isDefault(SheetMap sheetMap) {
        return sheetMap == null
                || sheetMap.getType() == null
                || Const.CellDataType.DEFAULT.equals(sheetMap.getType());
    }

    public static boolean isDate(SheetMap sheetMap) {
        return sheetMap != null && Const.CellDataType.DATE.equals(sheetMap.getType());
    }

    public static boolean isTimeRoster(SheetMap sheetMap) {
        return sheetMap != null && Const.CellDataType.TIME_ROSTER.equals(sheetMap.getType());
    }

    public static boolean hasNotValidDocument(Map<Document, List<Findings>> docsMap) {
        if (docsMap != null && docsMap.values() != null) {
            for (List<Findings> finds : docsMap.values()) {
                if (finds != null && !finds.isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }
}
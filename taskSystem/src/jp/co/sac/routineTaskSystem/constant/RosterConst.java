package jp.co.sac.routineTaskSystem.constant;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import jp.co.sac.routineTaskSystem.console.Output;
import jp.co.sac.routineTaskSystem.manage.excel.SheetMap;
import org.apache.poi.ss.usermodel.ExcelStyleDateFormatter;

/**
 * 勤務表の定数
 *
 * @author shogo_saito
 */
public class RosterConst {

    public static final int MAX_DAY = 31;
    public static final String TITLE = "勤務表";
    public static final String[] DIV_DESTINATION = {",", "/", "、", "／", "・"};
    public static final String[] PARENTHESIS_FROM = {"(", "（"};
    public static final String[] PARENTHESIS_TO = {")", "）"};
    public static String HEAD_OFFICE = "本社";
    public static String FILE_NAME_REGEX_PRE = "\\d{6}sacXXXXX";
    public static String FILE_NAME_REGEX = "\\d{6}sac\\d{5}";
    public static String PRINT_STRING = "%s %4d年 %2d月";
    public static String FORMAT_TIME = "%1$Tk:%1$TM";
    public static String[] File_TYPES = {"xls"};
    public static String[] EXCEPT_CHECK_SUPER_SIGN = {"10154"};

    public static class Cause {

        public static String PAID_HOLIDAY = "有休";
        public static String HALF_HOLIDAY = "半休";
        public static String COMPENSATORY_DAY = "代休";
        public static String SPECIAL_HOLIDAY = "特休";
        public static String ABSENCE = "欠勤";
        public static String DELAY = "遅刻";
        public static String LEAVE_EARLY = "早退";
        public static String WORK_HORIDAY = "休出";
        public static String UNEXCUSED_HOLIDAY = "無欠";
        public static String SUBSTITUTE_HOLIDAY = "振休";
        public static String SUBSTITUTE_WORK = "振出";
    }

    public static class FindMessage {

        public static String NO_FILE = "ファイルがありません。";
        public static String ERR_TITLE = "ファイル名が不正[%s]";
        public static String NO_NAME = "名前が未入力";
        public static String NO_LOCATION = "勤務地が未入力";
        public static String NO_STAFF_ID = "社員コードが未入力";
        public static String NO_TIME_TO = "%d日の退社が未入力";
        public static String NO_TIME_FROM = "%d日の出社が未入力";
        public static String NO_DESTINATION = "%d日の行き先（常駐先）欄が未入力";
        public static String ERR_DESTINATION = "%d日の行き先（常駐先）欄の記述形式に問題がある可能性があります。";
        public static String NO_REASON_HORIDAY = "%d日の休暇理由が未入力";
        public static String ERR_REASON_HORIDAY = "%d日の行き先（常駐先）欄の休暇理由が未入力の可能性があります。";
        public static String ERR_TIME_HORIDAY = "%d日の%sで勤務時間が入力されています。";
        public static String NO_TIME_WORK_HORIDAY = "%d日の" + Cause.WORK_HORIDAY + "で勤務時間が未入力";
        //TODO:A変・B変の出社時間
        public static String NO_REASON_PARTIALWORK = "%d日の%s理由が未入力";
        public static String ERR_REASON_PARTIALWORK = "%d日の行き先（常駐先）欄の%s理由が未入力の可能性があります。";
        public static String ERR_TIME_PARTIALWORK = "%d日の%sで勤務時間が入力されていません。";
        public static String NO_SIGN_SUPER = "所属長サインが未入力";
        public static String NO_SIGN_AUTHOR = "記入者サインが未入力";
    }

    public enum Category {

        AuthorName,
        AuthorSign,
        SuperSign,
        WorkLocation,
        StaffId,
        FROM,
        TO,
        Cause,
        Destination;

        /*
         * エクセルシート内の座標情報
         */
        private static class SheetMaps {

            public static final SheetMap AuthorName = new SheetMap(2, 5, 2, 1, Const.Direction.APOINT);
            public static final SheetMap AuthorSign = new SheetMap(14, 60, 3, 3, Const.Direction.APOINT);
            public static final SheetMap SuperSign = new SheetMap(11, 60, 3, 3, Const.Direction.APOINT);
            public static final SheetMap WorkLocation = new SheetMap(2, 2, 2, 1, Const.Direction.APOINT);
            public static final SheetMap StaffId = new SheetMap(2, 4, 2, 1, Const.Direction.APOINT);
            public static final SheetMap FROM = new SheetMap(2, 7, 1, 1, Const.Direction.DOWN, Const.CellDataType.TIME_ROSTER);
            public static final SheetMap TO = new SheetMap(3, 7, 1, 1, Const.Direction.DOWN, Const.CellDataType.TIME_ROSTER);
            public static final SheetMap Cause = new SheetMap(12, 7, 3, 1, Const.Direction.DOWN);
            public static final SheetMap Destination = new SheetMap(13, 7, 3, 1, Const.Direction.DOWN);
        }

        /**
         * 日付項目チェック
         *
         * @param category 項目
         * @return 日付を持つか
         */
        public static boolean haveDay(Category category) {
            if (category == null) {
                return false;
            }
            switch (category) {
                // 日付関連項目のみ以下に追加
                case FROM:
                case TO:
                case Cause:
                case Destination:
                    return true;
                default:
                    return false;
            }
        }

        /**
         * 日付関連の項目を一括取得
         *
         * @return
         */
        public static Category[] valuesHaveDay() {
            List<Category> catList = new ArrayList<>();
            for (Category cat : Category.values()) {
                if (Category.haveDay(cat)) {
                    catList.add(cat);
                }
            }
            return toCategories(catList.toArray());
        }

        /**
         * 日付非関連の項目を一括取得
         *
         * @return
         */
        public static Category[] valuesNoHaveDay() {
            List<Category> catList = new ArrayList<>();
            for (Category cat : Category.values()) {
                if (!Category.haveDay(cat)) {
                    catList.add(cat);
                }
            }
            return toCategories(catList.toArray());
        }

        private static Category[] toCategories(Object[] values) {
            if(values == null || values.length == 0) {
                return new Category[0];
            }
            Category[] categories = new Category[values.length];
            for (int i = 0; i < values.length; i++) {
                categories[i] = (Category)values[i];
            }
            return categories;
        }

        /**
         * エクセルシート位置情報の取得
         *
         * @return
         */
        public SheetMap getSheetMap() {
            try {
                return (SheetMap)SheetMaps.class.getField(this.toString()).get(null);
            } catch (IllegalAccessException| IllegalArgumentException | NoSuchFieldException | SecurityException ex) {
                Output.getInstance().print(ex);
                return null;
            }
        }
    }
}

package jp.co.sac.routineTaskSystem.check.checkLogic;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jp.co.sac.routineTaskSystem.common.DataUtil;
import jp.co.sac.routineTaskSystem.common.Normalizer;
import jp.co.sac.routineTaskSystem.config.GeneralConfig;
import jp.co.sac.routineTaskSystem.constant.RosterConst;
import jp.co.sac.routineTaskSystem.entity.document.RosterDocument;
import jp.co.sac.routineTaskSystem.entity.findings.Findings;
import jp.co.sac.routineTaskSystem.entity.findings.RosterFindings;
import jp.co.sac.routineTaskSystem.entity.staff.Staff;

/**
 * 勤務表チェック実装クラス
 *
 * @author shogo_saito
 */
public class RosterCheck<T extends RosterDocument> extends CheckLogic<T> {

    public RosterCheck() {
        super(new RosterFindings(RosterConst.FindMessage.NO_FILE));
    }

    @Override
    protected List<Findings> validateDocumentName(T document, Staff staff) {
        List<Findings> finds = new ArrayList<>(0);
        if (!isValidDocumentName(document)) {
            finds.add(new RosterFindings(RosterConst.FindMessage.ERR_TITLE, document.getTitle()));
        }
        return finds;
    }

    @Override
    protected List<Findings> validateRegistrant(T document, Staff staff) {
        List<Findings> finds = new ArrayList<>(0);
        return finds;
    }

    @Override
    protected List<Findings> validateContents(T document, Staff staff) {
        List<Findings> finds = new ArrayList<>(0);
        // 全体のチェック
        finds.addAll(findOfOverAll(document));
        // 日付ごとのチェック
        for (int idx = 0; idx < document.getMaxDay(); idx++) {
            finds.addAll(findOfOneDay(document, idx));
        }
        // 最後に必要なチェック
        finds.addAll(findOfLast(document));
        return finds;
    }

    /**
     * ファイル名のチェック
     * 
     * @param document 勤務表
     * @return ファイル名が正しいか
     */
    private boolean isValidDocumentName(T document) {
        try {
            Pattern pattern = Pattern.compile(RosterConst.FILE_NAME_REGEX);
            Matcher matcher = pattern.matcher(document.getTitle());
            return matcher.matches();
        } catch (Exception ex) {
            return false;
        }
    }

    private List<Findings> findOfOverAll(T document) {
        List<Findings> finds = new ArrayList<>();
        //勤務地の入力チェック
        String strLocation = document.get(RosterConst.Category.WorkLocation, 0);
        if (DataUtil.isNullOrEmpty(strLocation)) {
            finds.add(new RosterFindings(RosterConst.FindMessage.NO_LOCATION));
        }
        //社員コードの入力チェック
        String strStaffId = document.get(RosterConst.Category.StaffId, 0);
        if (DataUtil.isNullOrEmpty(strStaffId)) {
            finds.add(new RosterFindings(RosterConst.FindMessage.NO_STAFF_ID));
        }
        return finds;
    }

    private List<Findings> findOfLast(T document) {
        List<Findings> finds = new ArrayList<>();
        //記入者サインの入力チェック
        String strAuthorSign = document.get(RosterConst.Category.AuthorSign, 0);
        if (DataUtil.isNullOrEmpty(strAuthorSign)) {
            finds.add(new RosterFindings(RosterConst.FindMessage.NO_SIGN_AUTHOR));
        }
        //所属長サインの入力チェック
        String strStaffId = document.get(RosterConst.Category.StaffId, 0);
        if (isNeedAuthorSign(strStaffId)) {
            String strSuperSign = document.get(RosterConst.Category.SuperSign, 0);
            if (DataUtil.isNullOrEmpty(strSuperSign)) {
                finds.add(new RosterFindings(RosterConst.FindMessage.NO_SIGN_SUPER));
            }
        }
        return finds;
    }

    /**
     * 内容チェック（1日単位）
     *
     * @param document
     * @param idx
     * @return
     */
    private List<Findings> findOfOneDay(T document, int idx) {
        List<Findings> finds = new ArrayList<>();
        //休暇等にあわせてチェック
        finds.addAll(findOneDayByCause(document, idx));
        return finds;
    }

    /**
     * 休暇等にあわせて1日のチェックを切り替える
     *
     * @param document 勤務表
     * @param idx
     * @return
     */
    private List<Findings> findOneDayByCause(T document, int idx) {
        String strCause = document.get(RosterConst.Category.Cause, idx);
        List<Findings> finds = new ArrayList<>();
        if (DataUtil.isNullOrEmpty(strCause)) {
            finds.addAll(findOneDayOfNormal(document, idx));

        } else if (strCause.equals(RosterConst.Cause.PAID_HOLIDAY)) {
            finds.addAll(findOneDayOfHoliday(document, idx));

        } else if (strCause.equals(RosterConst.Cause.COMPENSATORY_DAY)) {
            finds.addAll(findOneDayOfHoliday(document, idx));

        } else if (strCause.equals(RosterConst.Cause.SPECIAL_HOLIDAY)) {
            finds.addAll(findOneDayOfHoliday(document, idx));

        } else if (strCause.equals(RosterConst.Cause.SUBSTITUTE_HOLIDAY)) {
            finds.addAll(findOneDayOfHoliday(document, idx));

        } else if (strCause.equals(RosterConst.Cause.ABSENCE)) {
            finds.addAll(findOneDayOfHoliday(document, idx));

        } else if (strCause.equals(RosterConst.Cause.HALF_HOLIDAY)) {
            finds.addAll(findOneDayOfPartialWork(document, idx));

        } else if (strCause.equals(RosterConst.Cause.LEAVE_EARLY)) {
            finds.addAll(findOneDayOfPartialWork(document, idx));

        } else if (strCause.equals(RosterConst.Cause.DELAY)) {
            finds.addAll(findOneDayOfPartialWork(document, idx));

        } else if (strCause.equals(RosterConst.Cause.SUBSTITUTE_WORK)) {
            finds.addAll(findOneDayOfSubstituteWork(document, idx));

        }
        return finds;
    }

    /**
     * 休日の内容チェック
     * 
     * @param document
     * @param idx
     * @return 
     */
    private List<Findings> findOneDayOfHoliday(T document, int idx) {
        List<Findings> finds = new ArrayList<>();
        String strTo = document.get(RosterConst.Category.TO, idx);
        String strFrom = document.get(RosterConst.Category.FROM, idx);
        String strDst = document.get(RosterConst.Category.Destination, idx);
        String strCause = document.get(RosterConst.Category.Cause, idx);
        // 出社・退社時間は入力なしが前提
        if (!DataUtil.isNullOrEmpty(strTo) || !DataUtil.isNullOrEmpty(strFrom)) {
            finds.add(new RosterFindings(RosterConst.FindMessage.ERR_TIME_HORIDAY, idx, strCause));
        }
        // 行き先欄には休暇理由が必要
        if (DataUtil.isNullOrEmpty(strDst)) {
            finds.add(new RosterFindings(RosterConst.FindMessage.NO_REASON_HORIDAY, idx));
        } else {
            finds.addAll(validateDestination(document, idx));
        }
        return finds;
    }

    /**
     * 休出の内容チェック
     * 
     * @param document
     * @param idx
     * @return 
     */
    private List<Findings> findOneDayOfSubstituteWork(T document, int idx) {
        List<Findings> finds = new ArrayList<>();
        String strTo = document.get(RosterConst.Category.TO, idx);
        String strFrom = document.get(RosterConst.Category.FROM, idx);
        String strDst = document.get(RosterConst.Category.Destination, idx);
        String strCause = document.get(RosterConst.Category.Cause, idx);
        // 出社・退社時間は必須
        if (DataUtil.isNullOrEmpty(strTo) || DataUtil.isNullOrEmpty(strFrom)) {
            finds.add(new RosterFindings(RosterConst.FindMessage.NO_TIME_WORK_HORIDAY, idx));
        }
        // 行き先欄も必須、内容のチェック
        if (DataUtil.isNullOrEmpty(strDst)) {
            finds.add(new RosterFindings(RosterConst.FindMessage.NO_DESTINATION, idx));
        } else {
            finds.addAll(validateDestination(document, idx));
        }
        return finds;
    }

    /**
     * 一部出勤（遅刻・早退など）の内容チェック
     * 
     * @param document
     * @param idx
     * @return 
     */
    private List<Findings> findOneDayOfPartialWork(T document, int idx) {
        List<Findings> finds = new ArrayList<>();
        String strTo = document.get(RosterConst.Category.TO, idx);
        String strFrom = document.get(RosterConst.Category.FROM, idx);
        String strDst = document.get(RosterConst.Category.Destination, idx);
        String strCause = document.get(RosterConst.Category.Cause, idx);
        // 出社・退社時間は必須
        if (DataUtil.isNullOrEmpty(strFrom)) {
            finds.add(new RosterFindings(RosterConst.FindMessage.NO_TIME_FROM, idx));
        }
        if (DataUtil.isNullOrEmpty(strTo)) {
            finds.add(new RosterFindings(RosterConst.FindMessage.NO_TIME_TO, idx));
        }
        // 行き先欄も必須、内容のチェック
        if (DataUtil.isNullOrEmpty(strDst)) {
            finds.add(new RosterFindings(RosterConst.FindMessage.NO_REASON_PARTIALWORK, idx, strCause));
        } else {
            finds.addAll(validateDestination(document, idx));
        }
        return finds;
    }

    /**
     * 通常の日の内容チェック
     * 
     * @param document
     * @param idx
     * @return 
     */
    private List<Findings> findOneDayOfNormal(T document, int idx) {
        List<Findings> finds = new ArrayList<>();
        String strTo = document.get(RosterConst.Category.TO, idx);
        String strFrom = document.get(RosterConst.Category.FROM, idx);
        String strDst = document.get(RosterConst.Category.Destination, idx);
//        String strCause = document.get(RosterConst.Category.Cause, idx);

        if (DataUtil.isNullOrEmpty(strFrom) || DataUtil.isNullOrEmpty(strTo)) {
            //出社・退社の時間が片方だけ入力されている場合は、指摘する。
            //祝休日もこちらになるので、両方入力必須にはしない。
            if (DataUtil.isNullOrEmpty(strFrom) && !DataUtil.isNullOrEmpty(strTo)) {
                finds.add(new RosterFindings(RosterConst.FindMessage.NO_TIME_FROM, idx));
            }
            if (!DataUtil.isNullOrEmpty(strFrom) && DataUtil.isNullOrEmpty(strTo)) {
                finds.add(new RosterFindings(RosterConst.FindMessage.NO_TIME_TO, idx));
            }
            // TODO：営業日判定して、営業日の場合は勤務時間の必須チェック
        } else {
            if (DataUtil.isNullOrEmpty(strDst)) {
                finds.add(new RosterFindings(RosterConst.FindMessage.NO_DESTINATION, idx));
            } else {
                finds.addAll(validateDestination(document, idx));
            }
        }
        return finds;
    }

    /**
     * 行き先欄の内容チェック
     *
     * @param destination
     * @return
     */
    private List<Findings> validateDestination(T document, int idx) {
        List<Findings> finds = new ArrayList<>();
        String strDst = document.get(RosterConst.Category.Destination, idx);
        boolean isDestination = isDestination(Normalizer.convDest(strDst, RosterConst.DIV_DESTINATION));
        if (!isDestination) {
            finds.add(new RosterFindings(RosterConst.FindMessage.ERR_DESTINATION, idx));
        }
        return finds;
    }

    private boolean isDestination(String[] destination) {
        if (destination == null) {
            return false;
        }
        for (String dst : destination) {
            if (isDestination(dst)) {
                return true;
            }
        }
        return false;
    }

    private boolean isDestination(String destination) {
        if (DataUtil.isNullOrEmpty(destination)) {
            return false;
        }
        if (destination.equals(RosterConst.HEAD_OFFICE)) {
            return true;
        }
        int ist = 0;
        for (String str : RosterConst.PARENTHESIS_FROM) {
            int index = destination.indexOf(str);
            ist += index > 0 ? index : 0;
        }
        int isd = 0;
        for (String str : RosterConst.PARENTHESIS_TO) {
            int index = destination.indexOf(str);
            isd += index > 0 ? index : 0;
        }
        
        return ist > 0 && isd > 0;
    }

    @Override
    protected List<Findings> getFindingsOfFileError(T document) {
        List<Findings> finds = new ArrayList<>();
        if (document.getFileError() != null) {
            finds.add(new RosterFindings(document.getFileError()));
        }
        return finds;
    }

    private boolean isNeedAuthorSign(String staffId) {
        if (DataUtil.isNullOrEmpty(staffId)) {
            //社員IDなしの場合は、判定不可
            return false;
        }
        for (String exceptId : RosterConst.EXCEPT_CHECK_SUPER_SIGN) {
            if (staffId.equals(exceptId)) {
                return false;
            }
        }
        return GeneralConfig.needCheckSuperSign();
    }
}

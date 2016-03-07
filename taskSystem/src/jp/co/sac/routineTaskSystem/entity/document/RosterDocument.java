package jp.co.sac.routineTaskSystem.entity.document;

import java.util.Calendar;
import jp.co.sac.routineTaskSystem.common.DataUtil;
import jp.co.sac.routineTaskSystem.constant.RosterConst;

/**
 * 勤務表クラス
 * 
 * ・出社/退社時間など、日付の関係する項目はデータ内に配列で保存。
 * 　配列の容量は定数で固定。
 * 　アクセスの際に、年月に応じて制限をかける。
 *
 * @author shogo_saito
 */
public class RosterDocument extends Document<RosterConst.Category, String> {

    private int maxDay;
    private String rosterYM;

    public RosterDocument() {
        super();
        initializeRosterYM();
        title = rosterYM + "sac00000";
        for (RosterConst.Category cat : RosterConst.Category.values()) {
            if(RosterConst.Category.haveDay(cat)) {
                data.put(cat, new String[RosterConst.MAX_DAY]);
            } else {
                data.put(cat, new String[1]);
            }
        }
    }

    public RosterDocument(String title) {
        this();
        if (title != null && title.length() == 14) {
            setRosterYM(title.substring(0, 6));
        }
    }

    private void initializeRosterYM() {
        Calendar calendar = Calendar.getInstance();
        StringBuilder sb = new StringBuilder();
        sb.append(calendar.get(Calendar.YEAR));
        sb.append(String.format("%02d", calendar.get(Calendar.MONTH) + 1));
        setRosterYM(sb.toString());
    }

    @Override
    public void put(RosterConst.Category key, String[] value) {
        super.put(key, value);
    }

    @Override
    public void put(RosterConst.Category key, int idx, String str) {
        if (key == null || idx >= maxDay) {
            return;
        }
        String[] value = get(key);
        if (value != null) {
            if (RosterConst.Category.haveDay(key)) {
                value[idx] = str;
            } else {
                value[0] = str;
            }
        }
        super.put(key, value);
    }

    @Override
    public String[] get(RosterConst.Category key) {
        return super.get(key);
    }

    @Override
    public String get(RosterConst.Category key, int idx) {
        if (key == null || idx >= maxDay) {
            return null;
        }
        String[] value = super.get(key);
        if (value != null) {
            if (RosterConst.Category.haveDay(key)) {
                return value[idx];
            } else {
                return value[0];
            }
        }
        return null;
    }

    @Override
    public String getPrintTitle() {
        return String.format(title, RosterConst.TITLE, getYear(), getMonth());
    }

    public final void setRosterYM(String rosterYM) {
        this.rosterYM = rosterYM;
        setMaxDay(DataUtil.getMaxDayOfMonth(getYear(), getMonth() - 1));
    }

    public int getYear() {
        return Integer.parseInt(rosterYM.substring(0, 4));
    }

    public int getMonth() {
        return Integer.parseInt(rosterYM.substring(4));
    }

    private void setMaxDay(int maxDay) {
        this.maxDay = maxDay;
    }

    public int getMaxDay() {
        return maxDay;
    }

    @Override
    public String getPrintAllForDebug() {
        StringBuilder sb = new StringBuilder();
        sb.append(" *** デバッグ用 内容表示 *** ").append("\n");
        sb.append("#title -> ").append(title).append("\n");
        sb.append("#maxday -> ").append(maxDay).append("\n");
        sb.append("#rosterYM -> ").append(rosterYM).append("\n");
        for (RosterConst.Category cat : RosterConst.Category.valuesNoHaveDay()) {
            sb.append("#").append(cat.toString()).append(" -> ").append(get(cat, 0)).append("\n");
        }
        for (int index = 0; index < RosterConst.MAX_DAY; index++) {
            sb.append("#[").append(index).append("] ");
            for (RosterConst.Category cat : RosterConst.Category.valuesHaveDay()) {
                sb.append("#").append(cat.toString()).append(" -> ").append(get(cat, index)).append(" ");
            }
            sb.append("\n");
        }
        sb.append(" ***        ↑↑↑      *** ").append("\n");
        return sb.toString();
    }
}

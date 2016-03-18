package jp.co.sac.routineTaskSystem.entity.document;

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

    /**
     * 内部データの初期化
     */
    public RosterDocument() {
        super();
        for (RosterConst.Category cat : RosterConst.Category.values()) {
            if(RosterConst.Category.haveDay(cat)) {
                data.put(cat, new String[RosterConst.MAX_DAY]);
            } else {
                data.put(cat, new String[1]);
            }
        }
    }

    @Override
    public void put(RosterConst.Category key, int idx, String str) {
        if (key == null || idx >= getMaxDay()) {
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
    public String get(RosterConst.Category key, int idx) {
        if (key == null || idx >= getMaxDay()) {
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

    /**
     * 表示用タイトルを取得
     * 
     * @return 表示用タイトル
     */
    @Override
    public String getPrintTitle() {
        return String.format(title == null ? "null" : title, RosterConst.TITLE, getYear(), getMonth());
    }

    @Override
    public String getPrintAllForDebug() {
        StringBuilder sb = new StringBuilder();
        sb.append("# title -> ").append(getTitle()).append(System.lineSeparator());
        sb.append("# extension -> ").append(getExtension()).append(System.lineSeparator());
        sb.append("# year -> ").append(getYear()).append(System.lineSeparator());
        sb.append("# month -> ").append(getMonth()).append(System.lineSeparator());
        sb.append("# maxday -> ").append(getMaxDay()).append(System.lineSeparator());
        sb.append("# fileError -> ").append(getFileError()).append(System.lineSeparator());
        sb.append("# data ").append(System.lineSeparator());
        for (RosterConst.Category key : RosterConst.Category.valuesNoHaveDay()) {
            sb.append("# ").append(key).append(" -> ").append(get(key, 0)).append(System.lineSeparator());
        }
        for (int idx = 0; idx < getMaxDay(); idx++) {
            sb.append("# [").append(String.format("%2d", idx)).append("] -> ");
            for (RosterConst.Category key : RosterConst.Category.valuesHaveDay()) {
                sb.append(key).append(" : ").append(get(key, idx)).append(" ; ");
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
}

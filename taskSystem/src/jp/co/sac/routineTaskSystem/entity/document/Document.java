package jp.co.sac.routineTaskSystem.entity.document;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import jp.co.sac.routineTaskSystem.constant.Const;
import jp.co.sac.routineTaskSystem.constant.RosterConst;

/**
 * 書類ベースクラス<br/>
 * 勤務表や交通費請求書の基本クラス。<br/>
 *
 * @author shogo_saito
 */
public class Document<K extends RosterConst.Category, V> {

    //タイトル
    protected String title;
    //拡張子
    private String extension;
    //ディレクトリ
    private String dirPath;
    //年
    private int year;
    //月
    private int month;
    //最大日数
    private int maxDay;
    //内容
    protected final Map<K, V[]> data;
    //ファイルエラー
    protected String fileError;

    /**
     * 全データの初期化<br>
     * ※必ず呼び出すように実装してください
     */
    protected Document() {
        this.title = null;
        this.extension = "xls";
        this.year = 0;
        this.month = 0;
        this.maxDay = Const.MAX_DAY;
        this.data = new ConcurrentHashMap<>();
        this.fileError = null;
    }

    /**
     * 年月を初期化
     */
    protected void initializeDate() {
        this.year = 0;
        this.month = 0;
        this.maxDay = Const.MAX_DAY;
    }

    public String getPrintTitle() {
        return getTitle() + (".") + extension;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public int getMaxDay() {
        return maxDay;
    }

    public void setMaxDay(int maxDay) {
        this.maxDay = maxDay;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getFileError() {
        return fileError;
    }

    public void setFileError(String fileError) {
        this.fileError = fileError;
    }

    /**
     * データの配置
     * NULLの配列データの場合、何もしない。
     * 
     * @param key カテゴリ
     * @param value 配置データ配列 @{not null}
     */
    public void put(K key, V[] value) {
        if (key != null && value != null) {
            data.put(key, value);
        }
    }

    /**
     * データの配置
     * 
     * @param key カテゴリ
     * @param idx データのインデックス
     * @param inputVal 配置データ
     */
    public void put(K key, int idx, V inputVal) {
        if (key == null || idx >= getMaxDay()) {
            return;
        }
        V[] value = get(key);
        if (value != null) {
            if (key.needIndex()) {
                value[idx] = inputVal;
            } else {
                value[0] = inputVal;
            }
        }
        put(key, value);
    }

    /**
     * データの取得(配列)
     * 該当カテゴリのデータがない場合はNULL
     * 
     * @param <V> 返却データ型
     * @param key カテゴリ
     * @return データ配列
     */
    public <V> V[] get(K key) {
        if (key != null && data.containsKey(key)) {
            return (V[]) data.get(key);
        }
        return null;
    }

    /**
     * データの取得(単一)
     * 
     * @param <V> 返却データ型
     * @param key カテゴリ
     * @param idx データのインデックス
     * @return 単一データ
     */
    public <V> V get(K key, int idx) {
        if (key == null || idx >= getMaxDay()) {
            return null;
        }
        if (get(key) != null) {
            return (V)get(key)[idx];
        }
        return null;
    }

    /**
     * 内部データの削除(マップデータのみ)
     */
    public void clearData() {
        data.clear();
    }

    /**
     * 書類の年月を取得<br>
     * 形式：YYYYMM
     * 
     * @return 年月
     */
    public String getYearMonthString() {
        return String.format("%04d%02d", year, month);
    }

    /**
     * ファイル名を生成<br>
     * 形式：{タイトル}.{拡張子}
     * 
     * @return ファイル名
     */
    public String getFileNameAndExtension() {
        return title + "." + extension;
    }

    /**
     * 内部データを一覧で取得
     * 
     * @return 内部データ(デバッグ用)
     */
    public String getPrintAllForDebug() {
        StringBuilder sb = new StringBuilder();
        sb.append("# title -> ").append(getTitle()).append(System.lineSeparator());
        sb.append("# extension -> ").append(getExtension()).append(System.lineSeparator());
        sb.append("# year -> ").append(getYear()).append(System.lineSeparator());
        sb.append("# month -> ").append(getMonth()).append(System.lineSeparator());
        sb.append("# maxday -> ").append(getMaxDay()).append(System.lineSeparator());
        sb.append("# fileError -> ").append(getFileError()).append(System.lineSeparator());
        sb.append("# data ").append(System.lineSeparator());
        for (K key : data.keySet()) {
            if (key.needIndex() && data.get(key) != null) {
                int maxCount = data.get(key).length;
                for (int index = 0; index < maxCount; index++) {
                    sb.append("# ").append(key).append(" [").append(String.format("%2d", index)).append("] -> ")
                            .append(data.get(key)[index]).append(System.lineSeparator());
                }
            } else {
                sb.append("# ").append(key).append(" -> ")
                        .append(data.get(key) == null ? "have no data..." : data.get(key)[0]).append(System.lineSeparator());
            }
        }
        sb.append("#  -> ").append(sb).append(System.lineSeparator());
        return sb.toString();
    }

    public String getDirPath() {
        return dirPath;
    }

    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
    }
}

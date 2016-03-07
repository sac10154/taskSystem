package jp.co.sac.routineTaskSystem.entity.document;

import java.util.HashMap;
import java.util.Map;
import jp.co.sac.routineTaskSystem.constant.Affiliation;
import jp.co.sac.routineTaskSystem.entity.staff.Staff;

/**
 * 書類ベースクラス<br/>
 * 勤務表や交通費請求書の基本クラス。<br/>
 * インスタンスは継承したクラスのみ生成可能。<br/>
 * 内部に記入者とその所属を持つが、nullの場合の考慮が必要（未登録ユーザなど）のため<br/>
 * 削除の可能性あり。<br/>
 *
 * @author shogo_saito
 */
public class Document<K,V> {

    //タイトル
    protected String title;
    //記入者
    private Staff staff;
    //所属
    private Affiliation affiliation;
    //内容
    protected final Map<K, V[]> data;
    //ファイルエラー
    protected String fileError;

    protected Document() {
        this.title = null;
        this.staff = new Staff();
        this.affiliation = new Affiliation();
        this.data = new HashMap();
        this.fileError = null;
    }

    public String getPrintTitle() {
        return getTitle();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Affiliation getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(Affiliation affiliation) {
        this.affiliation = affiliation;
    }

    public String getFileError() {
        return fileError;
    }

    public void setFileError(String fileError) {
        this.fileError = fileError;
    }

    public V[] get(K key) {
        if(key != null && data.containsKey(key)) {
            return (V[])data.get(key);
        }
        return null;
    }

    public V get(K key, int idx) {
        return null;
    }
    
    public void put(K key, V[] value) {
        if(key != null && value!= null) {
            data.put(key, value);
        }
    }
    
    public void put(K key, int idx, V value) {
    }
    
    public void clearData() {
        data.clear();
    }

    public String getPrintAllForDebug() {
        return "instanceof Document";
    }
}

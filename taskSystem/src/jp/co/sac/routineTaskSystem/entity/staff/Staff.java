package jp.co.sac.routineTaskSystem.entity.staff;

import java.io.Serializable;
import java.util.Date;
import jp.co.sac.routineTaskSystem.constant.Affiliation;

/**
 *
 * @author shogo_saito
 */
public class Staff {

    //データ番号
    private Long userNo;
    //社員番号
    private Integer staffId;
    //名前
    private String firstName;
    //苗字
    private String LastName;
    //親データ番号
    private Long parentuserNo;
    //所属
    private Affiliation affiliation;
    //役職
    private Position position;
    //作成日
    private Date createDate;
    //更新日
    private Date updateDate;
    //削除フラグ
    private Integer deleteFlg;

    /**
     * データベースの項目名
     */
    public static final class DB_NAME implements Serializable {
        public static final String USER_NO = "USER_NO";
        public static final String STAFF_ID = "STAFF_ID";
        public static final String FIRST_NAME = "FIRST_NAME";
        public static final String LAST_NAME = "LAST_NAME";
        public static final String DELETE_FLG = "DELETE_FLG";
    }
    
    public enum Position {
        
        none,
        CEO,
        EMD,    //Executive Managing Director
        Manager,
        GL,
        TL,
        Other;
        
        public static String ToString(Position position) {
            if (position == null) {
                return null;
            }
            return position.toString();
        }
    }

    public Staff() {
    }

    public Long getUserNo() {
        return userNo;
    }

    public void setUserNo(Long userNo) {
        this.userNo = userNo;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public Long getParentuserNo() {
        return parentuserNo;
    }

    public void setParentuserNo(Long parentuserNo) {
        this.parentuserNo = parentuserNo;
    }

    public Affiliation getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(Affiliation affiliation) {
        this.affiliation = affiliation;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getDeleteFlg() {
        return deleteFlg;
    }

    public void setDeleteFlg(Integer deleteFlg) {
        this.deleteFlg = deleteFlg;
    }
}

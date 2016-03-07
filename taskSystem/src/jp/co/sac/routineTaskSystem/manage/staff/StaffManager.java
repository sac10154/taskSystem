package jp.co.sac.routineTaskSystem.manage.staff;

import java.util.ArrayList;
import java.util.List;
import jp.co.sac.routineTaskSystem.entity.staff.Staff;

/**
 *
 * @author shogo_saito
 */
public class StaffManager {

    /**
     * 社員の取得
     * 
     * @param staffId 社員ID
     * @return 社員
     */
    public Staff getStaff(Integer staffId) {
        return new Staff();
    }

    /**
     * 社員一覧の取得
     * 
     * @return 社員リスト
     */
    public List<Staff> getStaffList() {
        return new ArrayList<>();
    }
    
    /**
     * 社員の登録
     * 
     * @param staff 社員
     */
    public void registStaff(Staff staff) {
    }
    
    /**
     * 社員の削除（論理）
     * 
     * @param staff 社員
     */
    public void deleteStaff(Staff staff) {
        deleteStaff(staff.getStaffId());
    }
    
    /**
     * 社員の削除（論理）
     * 
     * @param staff 社員
     */
    public void deleteStaff(Integer staffId) {
    }
}

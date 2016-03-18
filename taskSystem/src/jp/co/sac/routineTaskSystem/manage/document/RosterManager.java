package jp.co.sac.routineTaskSystem.manage.document;

import jp.co.sac.routineTaskSystem.common.DataUtil;
import jp.co.sac.routineTaskSystem.constant.RosterConst;
import jp.co.sac.routineTaskSystem.entity.document.RosterDocument;
import jp.co.sac.routineTaskSystem.manage.excel.SheetMap;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author shogo_saito
 */
public class RosterManager extends DocumentManager<RosterDocument> {

    private static Logger log = Logger.getLogger("root");

    @Override
    public RosterDocument load(String filePath) throws Exception {
        if (filePath != null) {
            String extension = DataUtil.getExtensionFromFilePath(filePath);
            for (String fileType : RosterConst.File_TYPES) {
                if (extension.equals(fileType)) {
                    return super.load(filePath);
                }
            }
        }
        RosterDocument document = new RosterDocument();
        setTitleOfDocument(document, filePath);
        document.setFileError(document.getPrintTitle() + " : 形式外のファイル");
        return document;
    }

    @Override
    public RosterDocument load(Workbook wb, String filePath) {
        RosterDocument document = new RosterDocument();
        setTitleOfDocument(document, filePath);
        for (RosterConst.Category category : RosterConst.Category.valuesNoHaveDay()) {
                if (category.equals(RosterConst.Category.AuthorName)) {
                    // 社員名は後で取得
                    continue;
                }
                String[] data = getDataSingle(wb.getSheetAt(0), category.getSheetMap());
                if (category.equals(RosterConst.Category.StaffId)) {
                    data[0] = DataUtil.convertToIntStringFromDoubleString(data[0]);
                }
                document.put(category, data);
        }
        
        //社員名はここで設定
        document.put(RosterConst.Category.AuthorName, getAuthorName(wb.getSheetAt(0), document.get(RosterConst.Category.StaffId, 0)));
        
        for (RosterConst.Category category : RosterConst.Category.valuesHaveDay()) {
                String[] data = getData(wb.getSheetAt(0), category.getSheetMap(), document.getMaxDay());
                document.put(category, data);
        }
        return document;
    }

    @Override
    public void save(RosterDocument document, Workbook wb) {
        
    }

    @Override
    protected void setTitleOfDocument(RosterDocument document, String filePath) {
        String fileName = DataUtil.convertToFileNameFromFilePath(filePath);
        String extension = DataUtil.getExtensionFromFilePath(filePath);
        document.setTitle(fileName);
        document.setExtension(extension);
        if (DataUtil.isRosterDocumentName(fileName)) {
            if (fileName.length() > 4) {
                String strYear = fileName.substring(0, 4);
                if (DataUtil.isNumeric(strYear)) {
                    document.setYear(Integer.valueOf(strYear));
                }
            }
            if (fileName.length() > 6) {
                String strMonth = fileName.substring(4, 6);
                if (DataUtil.isNumeric(strMonth)) {
                    document.setMonth(Integer.valueOf(strMonth));
                }
            }
        }
    }

    @Override
    protected String getCellData(Cell cell, SheetMap sheetMap) {
        if (DataUtil.isTimeRoster(sheetMap)) {
            if (cell == null) {
                return null;
            }
            if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                return DataUtil.convertToTimeStringFromDateForRoster(cell.getDateCellValue());
            } else {
                return null;
            }
        }
        return super.getCellData(cell, sheetMap);
    }

    private String[] getAuthorName(Sheet sheet, String staffId) {
        String[] ret = new String[1];
        if (sheet == null || DataUtil.isNullOrEmpty(staffId)) {
            return ret;
        }
        String[] staffIds = getColumnDataAsc(sheet, RosterConst.lookUpStaffIdMap, RosterConst.lookUpStaffIdMap.getRngRow());
        String[] staffNames = getColumnDataAsc(sheet, RosterConst.lookUpAuthorNameMap, staffIds.length);
        for (int idx = 0; idx < staffIds.length && idx < staffNames.length; idx++) {
            if (staffId.equals(staffIds[idx])) {
                ret[0] = staffNames[idx];
                break;
            }
        }
        return ret;
    }

    @Override
    protected String[] getColumnDataAsc(Sheet sheet, SheetMap sheetMap, int amount) {
        if (RosterConst.lookUpStaffIdMap.equals(sheetMap)) {
            String[] data = new String[amount];
            for (int index = 0; index < amount; index++) {
                if (sheet.getRow(index + sheetMap.getPstRow()) == null) {
                    continue;
                }
                data[index] = getCellData(sheet.getRow(index + sheetMap.getPstRow()).getCell(sheetMap.getPstCol()), sheetMap);
                data[index] = DataUtil.convertToIntStringFromDoubleString(data[index]);
                if (RosterConst.LAST_STAFF_ID.equals(data[index])) {
                    break;
                }
            }
            return data;
        }
        if (RosterConst.lookUpAuthorNameMap.equals(sheetMap)) {
            String[] data = new String[amount];
            for (int index = 0; index < amount - 1; index++) {
                try {
                    if (sheet.getRow(index + sheetMap.getPstRow()) == null) {
                        continue;
                    }
                    data[index] = getCellData(sheet.getRow(index + sheetMap.getPstRow()).getCell(sheetMap.getPstCol()), sheetMap);
                } catch (Exception ex) {
                    log.warn("getColumnDataAsc", ex);
                }
            }
            return data;
        }
        return super.getColumnDataAsc(sheet, sheetMap, amount);
    }
}

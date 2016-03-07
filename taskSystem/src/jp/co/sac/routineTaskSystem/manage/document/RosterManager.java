package jp.co.sac.routineTaskSystem.manage.document;

import jp.co.sac.routineTaskSystem.common.DataUtil;
import jp.co.sac.routineTaskSystem.constant.RosterConst;
import jp.co.sac.routineTaskSystem.entity.document.RosterDocument;
import jp.co.sac.routineTaskSystem.manage.excel.SheetMap;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author shogo_saito
 */
public class RosterManager extends DocumentManager<RosterDocument> {

    @Override
    public RosterDocument load(String filePath) throws Exception {
        if (filePath != null) {
            String extension = DataUtil.getExtensionFromFilePath(filePath);
            for (String fileType : RosterConst.File_TYPES) {
                if (extension.equals(fileType)) {
                    return super.load(filePath); //To change body of generated methods, choose Tools | Templates.
                }
            }
        }
        RosterDocument document = new RosterDocument();
        document.setTitle(DataUtil.convertToFileNameWithExtensionFromFilePath(filePath));
        document.setFileError(document.getTitle() + " : 形式外のファイル");
        return document;
    }

    @Override
    public RosterDocument load(Workbook wb, String filePath) {
        RosterDocument document = new RosterDocument();
        setTitleOfDocument(document, filePath);
        for (RosterConst.Category category : RosterConst.Category.valuesNoHaveDay()) {
                String[] data = getDataSingle(wb.getSheetAt(0), category.getSheetMap());
                if (category.equals(RosterConst.Category.StaffId)) {
                    data[0] = DataUtil.convertToIntStringFromDoubleString(data[0]);
                }
                document.put(category, data);
        }
        for (RosterConst.Category category : RosterConst.Category.valuesHaveDay()) {
                String[] data = getData(wb.getSheetAt(0), category.getSheetMap(), document.getMaxDay());
                document.put(category, data);
        }
        return document;
    }

    @Override
    protected void setTitleOfDocument(RosterDocument document, String filePath) {
        String fileName = DataUtil.convertToFileNameFromFilePath(filePath);
        if (DataUtil.isRosterDocumentName(fileName)) {
            document.setTitle(fileName);
            if (fileName.length() > 6) {
                String rosterYM = fileName.substring(0, 6);
                if (DataUtil.isNumeric(rosterYM)) {
                    document.setRosterYM(rosterYM);
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
}

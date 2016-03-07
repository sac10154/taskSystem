package jp.co.sac.routineTaskSystem.manage.document;

import java.io.File;
import java.io.FileInputStream;
import jp.co.sac.routineTaskSystem.common.DataUtil;
import jp.co.sac.routineTaskSystem.constant.Const;
import jp.co.sac.routineTaskSystem.entity.document.Document;
import jp.co.sac.routineTaskSystem.manage.excel.SheetMap;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 *
 * @author shogo_saito
 */
public abstract class DocumentManager<T extends Document> {
    
    /**
     * 書類を取得
     * 
     * @return 書類
     */
    public T load (String filePath) throws Exception {
        T document = load(WorkbookFactory.create(new FileInputStream(filePath)), filePath);
        return document;
    }

    public T load (File file) throws Exception {
        T document = load(WorkbookFactory.create(file), file.getPath());
        return document;
    }

    public abstract T load(Workbook wb, String filePath);

    protected String[] getData(Sheet sheet, SheetMap sheetMap, int amount) {
        if (Const.Direction.APOINT.equals(sheetMap.getDirect())) {
            return getDataSingle(sheet, sheetMap);
        } else if (Const.Direction.DOWN.equals(sheetMap.getDirect())) {
            return getColumnDataAsc(sheet, sheetMap, amount);
        } else if (Const.Direction.RIGHT.equals(sheetMap.getDirect())) {
            return getRowDataAsc(sheet, sheetMap, amount);
        } else {
            return new String[1];
        }
    }

    protected String[] getRowDataAsc(Sheet sheet, SheetMap sheetMap, int amount) {
        String[] data = new String[amount];
        Row row = sheet.getRow(sheetMap.getPstRow());
        for (int index = 0; index < amount; index++) {
            data[index] = getCellData(row.getCell(index + sheetMap.getPstCol()), sheetMap);
        }
        return data;
    }

    protected String[] getColumnDataAsc(Sheet sheet, SheetMap sheetMap, int amount) {
        String[] data = new String[amount];
        for (int index = 0; index < amount; index++) {
            data[index] = getCellData(sheet.getRow(index + sheetMap.getPstRow()).getCell(sheetMap.getPstCol()), sheetMap);
        }
        return data;
    }

    protected String[] getDataSingle(Sheet sheet, SheetMap sheetMap) {
        String[] data = new String[1];
        data[0] = getCellData(sheet.getRow(sheetMap.getPstRow()).getCell(sheetMap.getPstCol()), sheetMap);
        return data;
    }

    protected String getCellData(Cell cell, SheetMap sheetMap) {
        if (cell == null) {
            return null;
        }
        if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            return cell.getBooleanCellValue() ? Boolean.TRUE.toString() : Boolean.FALSE.toString();
        } else if (cell.getCellType() == Cell.CELL_TYPE_ERROR) {
            return "error";
        } else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
            return cell.getCellFormula();
        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            if (DataUtil.isDate(sheetMap)) {
                return cell.getDateCellValue().toString();
            } else {
                return String.valueOf(cell.getNumericCellValue());
            }
        } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
            return cell.getStringCellValue();
        } else if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
            return null;
        } else {
            return null;
        }
    }

    protected void setTitleOfDocument(T document, String filePath) {
        document.setTitle(DataUtil.convertToFileNameFromFilePath(filePath));
    }
}

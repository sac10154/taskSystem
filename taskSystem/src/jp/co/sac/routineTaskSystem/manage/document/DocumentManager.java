package jp.co.sac.routineTaskSystem.manage.document;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;
import jp.co.sac.routineTaskSystem.common.DataUtil;
import jp.co.sac.routineTaskSystem.constant.Const;
import jp.co.sac.routineTaskSystem.entity.document.Document;
import jp.co.sac.routineTaskSystem.manage.excel.SheetMap;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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

    private static Logger log = Logger.getLogger("root");

    /**
     * 書類を取得
     * 
     * @return 書類
     */
    public T load (String filePath) throws Exception {
        T document;
        try (FileInputStream in = new FileInputStream(filePath)) {
            document = load(WorkbookFactory.create(in), filePath);
        }
        return document;
    }

    public T load (File file) throws Exception {
        T document = load(WorkbookFactory.create(file), file.getPath());
        return document;
    }

    public abstract T load(Workbook wb, String filePath);

    public void save (T document) throws Exception {
        setTitleOfDocument(document);
        String filePath = DataUtil.getFilePath(document);
        Workbook wb;
        wb = getOrCreateWorkbook(filePath);
        save(document, wb);
        try (FileOutputStream out = new FileOutputStream(filePath)) {
            wb.write(out);
        }
    }

    public abstract void save(T document, Workbook wb);

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

    protected void setTitleOfDocument(T document) {
        if (DataUtil.isNullOrEmpty(document.getTitle())) {
            document.setTitle("TMP" + String.format("[yyMMddHHmmssSSS]", Calendar.getInstance()));
        }
        if (DataUtil.isNullOrEmpty(document.getExtension())) {
            document.setExtension("xls");
        }
    }

    protected void setTitleOfDocument(T document, String filePath) {
        String fileName = DataUtil.convertToFileNameFromFilePath(filePath);
        String extension = DataUtil.getExtensionFromFilePath(filePath);
        document.setTitle(fileName);
        document.setExtension(extension);
    }

    protected void setData(Sheet sheet, SheetMap sheetMap, String[] data) {
        if (data == null) {
            return;
        }
        if (Const.Direction.APOINT.equals(sheetMap.getDirect())) {
            setDataSingle(sheet, sheetMap, data);
        } else if (Const.Direction.DOWN.equals(sheetMap.getDirect())) {
            setColumnDataAsc(sheet, sheetMap, data);
        } else if (Const.Direction.RIGHT.equals(sheetMap.getDirect())) {
            setRowDataAsc(sheet, sheetMap, data);
        }
    }

    protected void setRowDataAsc(Sheet sheet, SheetMap sheetMap, String[] data) {
        Row row = sheet.getRow(sheetMap.getPstRow());
        for (int index = 0; index < data.length; index++) {
            setCellData(row.getCell(index + sheetMap.getPstCol()), sheetMap, data[index]);
        }
    }

    protected void setColumnDataAsc(Sheet sheet, SheetMap sheetMap, String[] data) {
        for (int index = 0; index < data.length; index++) {
            setCellData(sheet.getRow(index + sheetMap.getPstRow()).getCell(sheetMap.getPstCol()), sheetMap, data[index]);
        }
    }

    protected void setDataSingle(Sheet sheet, SheetMap sheetMap, String[] data) {
        setCellData(sheet.getRow(sheetMap.getPstRow()).getCell(sheetMap.getPstCol()), sheetMap, data[1]);
    }

    protected void setCellData(Cell cell, SheetMap sheetMap, String value) {
        if (cell == null) {
            return;
        }
        if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            cell.setCellValue(value == null ? null : Boolean.valueOf(value));
        } else if (cell.getCellType() == Cell.CELL_TYPE_ERROR) {
            cell.setCellErrorValue(value == null || value.isEmpty() ? null : value.getBytes()[1]);
        } else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
            cell.setCellFormula(value);
        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            if (DataUtil.isDate(sheetMap)) {
                cell.setCellValue(DataUtil.convertToDateFromString(value));
            } else {
                cell.setCellValue(DataUtil.isNumeric(value) ? Double.valueOf(value) : null);
            }
        } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
            cell.setCellValue(value);
        } else if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
            cell.setCellValue(value);
        }
    }

    protected Workbook getOrCreateWorkbook(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            try {
                return WorkbookFactory.create(file);
            } catch(Exception e) {}
        }
        //ここはテンプレートファイルに置き換えたい。
        return new HSSFWorkbook();
    }
}

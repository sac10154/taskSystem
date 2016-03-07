/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.co.sac.routineTaskSystem.manage.excel;

import jp.co.sac.routineTaskSystem.constant.Const.CellDataType;
import jp.co.sac.routineTaskSystem.constant.Const.Direction;

/**
 *
 * @author shogo_saito
 */
public class SheetMap {
    private int pstCol;
    private int pstRow;
    private int rngCol;
    private int rngRow;
    private Direction direct;
    private CellDataType type;

    public SheetMap() {
        pstCol = -1;
        pstRow = -1;
        rngCol = -1;
        rngRow = -1;
        direct = null;
        type = null;
    }

    public SheetMap(int pstCol, int pstRow, int rngCol, int rngRow, Direction direct) {
        this.pstCol = pstCol;
        this.pstRow = pstRow;
        this.rngCol = rngCol;
        this.rngRow = rngRow;
        this.direct = direct;
        this.type = CellDataType.DEFAULT;
    }

    public SheetMap(int pstCol, int pstRow, int rngCol, int rngRow, Direction direct,CellDataType type) {
        this.pstCol = pstCol;
        this.pstRow = pstRow;
        this.rngCol = rngCol;
        this.rngRow = rngRow;
        this.direct = direct;
        this.type = type;
    }

    public int getPstCol() {
        return pstCol;
    }

    public void setPstCol(int pstCol) {
        this.pstCol = pstCol;
    }

    public int getPstRow() {
        return pstRow;
    }

    public void setPstRow(int pstRow) {
        this.pstRow = pstRow;
    }

    public int getRngCol() {
        return rngCol;
    }

    public void setRngCol(int rngCol) {
        this.rngCol = rngCol;
    }

    public int getRngRow() {
        return rngRow;
    }

    public void setRngRow(int rngRow) {
        this.rngRow = rngRow;
    }

    public Direction getDirect() {
        return direct;
    }

    public void setDirect(Direction direct) {
        this.direct = direct;
    }

    public CellDataType getType() {
        return type;
    }

    public void setType(CellDataType type) {
        this.type = type;
    }
    
}

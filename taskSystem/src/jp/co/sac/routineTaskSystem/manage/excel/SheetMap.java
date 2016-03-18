/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.co.sac.routineTaskSystem.manage.excel;

import jp.co.sac.routineTaskSystem.constant.Const.CellDataType;
import jp.co.sac.routineTaskSystem.constant.Const.Direction;

/**
 * Excelシートの位置情報クラス
 * 定数として宣言して読み書きに利用する想定
 *
 * @author shogo_saito
 */
public class SheetMap {
    //横位置
    private int pstCol;
    //縦位置
    private int pstRow;
    //範囲・横
    private int rngCol;
    //範囲・縦
    private int rngRow;
    //方向
    private Direction direct;
    //セルのデータタイプ
    private CellDataType type;

    public SheetMap() {
        pstCol = -1;
        pstRow = -1;
        rngCol = -1;
        rngRow = -1;
        direct = null;
        type = null;
    }

    /**
     * 位置・範囲・方向を指定して初期化
     * 
     * @param pstCol 横位置
     * @param pstRow 縦位置
     * @param rngCol 範囲・横
     * @param rngRow 範囲・縦
     * @param direct 方向
     */
    public SheetMap(int pstCol, int pstRow, int rngCol, int rngRow, Direction direct) {
        this.pstCol = pstCol;
        this.pstRow = pstRow;
        this.rngCol = rngCol;
        this.rngRow = rngRow;
        this.direct = direct;
        this.type = CellDataType.DEFAULT;
    }

    /**
     * 位置・範囲・方向・データタイプを指定して初期化
     * 
     * @param pstCol 横位置
     * @param pstRow 縦位置
     * @param rngCol 範囲・横
     * @param rngRow 範囲・縦
     * @param direct 方向
     * @param type セルのデータタイプ
     */
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

    protected void setPstCol(int pstCol) {
        this.pstCol = pstCol;
    }

    public int getPstRow() {
        return pstRow;
    }

    protected void setPstRow(int pstRow) {
        this.pstRow = pstRow;
    }

    public int getRngCol() {
        return rngCol;
    }

    protected void setRngCol(int rngCol) {
        this.rngCol = rngCol;
    }

    public int getRngRow() {
        return rngRow;
    }

    protected void setRngRow(int rngRow) {
        this.rngRow = rngRow;
    }

    public Direction getDirect() {
        return direct;
    }

    protected void setDirect(Direction direct) {
        this.direct = direct;
    }

    public CellDataType getType() {
        return type;
    }

    protected void setType(CellDataType type) {
        this.type = type;
    }
    
}

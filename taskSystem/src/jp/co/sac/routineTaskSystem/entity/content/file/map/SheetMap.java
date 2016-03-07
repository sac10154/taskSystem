package jp.co.sac.routineTaskSystem.entity.content.file.map;

/**
 *
 * @author shogo_saito
 */
public abstract class SheetMap {

    //列
    Integer column;
    //行
    Integer row;
    //名称
    String name;

    public static enum ITEM_NAME {

        name,
        Affiliation
    }

    protected SheetMap() {
        this.column = new Integer(0);
        this.row = new Integer(0);
        this.name = new String();
    }

    protected SheetMap(Integer column, Integer row) {
        this.column = column;
        this.row = row;
    }

    public Integer getColumn() {
        return column;
    }

    public Integer getRow() {
        return row;
    }
}

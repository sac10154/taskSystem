package jp.co.sac.routineTaskSystem.entity.content;

/**
 *
 * @author shogo_saito
 */
public class Position {
    //列
    private int column;
    //行
    private int row;
    //名称
    private String Name;

    public Position(int column, int row) {
        this.column = column;
        this.row = row;
        this.Name = new String();
    }

    public Position(int column, int row, String Name) {
        this.column = column;
        this.row = row;
        this.Name = Name;
    }

    protected int getColumn() {
        return column;
    }

    protected int getRow() {
        return row;
    }

    protected String getName() {
        return Name;
    }
}

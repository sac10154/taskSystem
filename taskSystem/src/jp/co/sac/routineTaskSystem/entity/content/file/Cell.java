package jp.co.sac.routineTaskSystem.entity.content.file;

/**
 *
 * @author shogo_saito
 */
public abstract class Cell {

    //位置
    private Position position;

    //内容
    private Content content;

    /**
     * 位置クラス
     */
    public class Position {

        public int getColumn() {return 0;}
        public int getRow() {return 0;}
        public void setColumn(int column) {}
        public void setRow(int row) {}
        
    }

    /**
     * 内容クラス
     */
    public class Content {
        //内容未定
    }

    public Cell() {
        position = new Position();
        content = null;
    }

    public Cell(Position position, Object content) {
        this.position = position;
        this.content = content instanceof Content ? (Content) content : new Content();
    }

    public Cell(Integer column, Integer row, Object content) {
        this.position = new Position();
        
        this.content = content instanceof Content ? (Content) content : new Content();
    }

    protected Position getPosition() {
        return position;
    }

    protected void setPosition(Position position) {
        this.position = position;
    }

    protected void setContent(Content content) {
        this.content = content;
    }

    protected Content getContent() {
        return content;
    }

    protected int getColumn() {
        return getPosition().getColumn();
    }

    protected void setColumn(Integer column) {
        getPosition().setColumn(column);
    }

    protected Integer getRow() {
        return getPosition().getRow();
    }

    protected void setRow(Integer row) {
        getPosition().setRow(row);
    }

    protected Object getObjectOfContent() {
        return getContent();
    }

    protected void setObjectOfContent(Object content) {
        this.setContent(content instanceof Content ? (Content) content : new Content());
    }
}
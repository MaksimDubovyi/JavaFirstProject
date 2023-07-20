package step.learning.file;


/**
 * Для дослідження можливостей Gson
 */
public class DataObject {
    private String field1 ;
    private String field2 ;

    public String getField1() {
        return field1;
    }

    public DataObject setField1(String field1) {
        this.field1 = field1;
        return this;
    }

    public String getField2() {
        return field2;
    }

    public DataObject setField2(String field2) {
        this.field2 = field2;
        return this;
    }

    @Override
    public String toString() {
        return String.format("filed1 = %s, file2 =%s", field1,field2);
    }
}
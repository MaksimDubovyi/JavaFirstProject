package step.learning.db.dto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * DTO for jpu121_randoms table (ORM)
**/
public class Record
{
 private UUID id;
 private int valInt;
 private String valStr;

    public Record() {
        setId(null);
    }

    public Record(int valInt, String valStr, double valFloat) {
        this.valInt = valInt;
        this.valStr = valStr;
        this.valFloat = valFloat;
    }

    public Record(ResultSet resultSet) throws SQLException {

      setId(UUID.fromString( resultSet.getString("id")));
      setValInt(resultSet.getInt("val_int"));
      setValStr(resultSet.getString("val_str"));
      setValFloat(resultSet.getDouble("val_float"));
    }

    @Override
    public String toString() {
        return String.format(
                "{id: '%s', valInt: %d , valStr '%s', valFloat: %f}",
                getId(),
                getValInt(),
                getValStr(),
                getValFloat()
        );
    }

    private  double valFloat;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getValInt() {
        return valInt;
    }

    public void setValInt(int valInt) {
        this.valInt = valInt;
    }

    public String getValStr() {
        return valStr;
    }

    public void setValStr(String valStr) {
        this.valStr = valStr;
    }

    public double getValFloat() {
        return valFloat;
    }

    public void setValFloat(double valFloat) {
        this.valFloat = valFloat;
    }
}

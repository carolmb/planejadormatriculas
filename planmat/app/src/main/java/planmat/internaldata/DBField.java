package planmat.internaldata;

/**
 * Created by Luisa on 12/12/2016.
 */

public class DBField {
    //[LUÍS] Tudo público pra não dar trabalho.

    public String fieldName;
    public String fieldType;
    public boolean pk;
    public boolean fk;
    public String refTable;
    public String refField;

    public DBField(String fieldName, String fieldType, boolean PK,
                   boolean FK, String refTable, String refField) {
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.pk = PK;
        this.fk = FK;
        this.refTable = refTable;
        this.refField = refField;
    }

    public DBField(String fieldName, String fieldType, boolean PK) {
        this(fieldName, fieldType, PK, false, null, null);
    }

    public DBField(String fieldName, String fieldType) {
        this(fieldName, fieldType, false);
    }

    @Override
    public String toString() {
        String str = fieldName + " " + fieldType;
        //if (fk) str += ", FOREIGN KEY(" + fieldName + ") REFERENCES " + refTable + "(" + refField + ")";
        return str;
    }
}

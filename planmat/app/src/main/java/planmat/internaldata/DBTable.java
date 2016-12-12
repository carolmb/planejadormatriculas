package planmat.internaldata;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luisa on 12/12/2016.
 */

public class DBTable {

    public String name;
    public List<DBField> fields;

    public DBTable(String name) {
        this.name = name;
        fields = new ArrayList<DBField>();
    }

    @Override
    public String toString() {
        if(fields.isEmpty()) return null;

        String str = "CREATE TABLE " + name + "(";
        //add fields
        str += fields.get(0).toString();
        for(int i = 1; i < fields.size(); i++)
            str += ", " + fields.get(i).toString();

        //gather primary keys
        List<String> pks = new ArrayList<String>();
        for(DBField f : fields)
            if(f.pk) pks.add(f.fieldName);

        //add primary key constraints
        //not having at least ONE primary key will cause it to crash.
        //This is "correct", as every table must have a primary key,
        //but we should be less drastic and throws an error message
        //instead of allowing it to crash.
        str += ", CONSTRAINT pks PRIMARY KEY (";
        str += pks.get(0);
        for(int i = 1; i < pks.size(); i++)
            str += ", " + pks.get(i);
        str += ")";

        //gather foreign keys
        List<DBField> fks = new ArrayList<DBField>();
        for(DBField f : fields)
            if(f.fk) fks.add(f);

        //add foreign key constraints
        for(DBField f : fks)
            str += ", FOREIGN KEY(" + f.fieldName + ") REFERENCES " + f.refTable + "(" + f.refField + ")";

        str += ")";

        return str;
    }
}

package planmat.internaldata;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import planmat.datarepresentation.ClassList;
import planmat.datarepresentation.Component;
import planmat.datarepresentation.Requirements;
import planmat.datarepresentation.StatList;

/**
 * Created by Luisa on 09/12/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    //[LUÍS] Tudo público pra não dar trabalho.
    private class DBField {
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

    private class DBTable {
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

    // metadata
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "PlanmatDB";
    private static DBTable componentTable, classTable, statTable, reqCompTable, reqTable;
    private static List<DBTable> tables;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        tables = new ArrayList<DBTable>();

        //Create database scheme
        componentTable = new DBTable("Component");
        tables.add(componentTable);
        componentTable.fields.add( new DBField("Code", "VARCHAR(7)", true) );
        componentTable.fields.add( new DBField("Name", "VARCHAR(256)") );
        componentTable.fields.add( new DBField("Workload", "INTEGER") );

        classTable = new DBTable("Class");
        tables.add(classTable);
        classTable.fields.add( new DBField("Code", "INTEGER", true) );
        classTable.fields.add( new DBField("Year", "INTEGER", true) );
        classTable.fields.add( new DBField("Semester", "INTEGER", true) );
        classTable.fields.add( new DBField("Hour", "VARCHAR(16)") );
        classTable.fields.add( new DBField("Professors", "VARCHAR(256)") );
        classTable.fields.add( new DBField("ComponentCode", "VARCHAR(7)", false,
                                            true, componentTable.name, componentTable.fields.get(0).fieldName) );

        statTable = new DBTable("Stat");
        tables.add(statTable);
        statTable.fields.add( new DBField("Code", "INTEGER", true) );
        statTable.fields.add( new DBField("Year", "INTEGER", true) );
        statTable.fields.add( new DBField("Semester", "INTEGER", true) );
        statTable.fields.add( new DBField("Successes", "INTEGER") );
        statTable.fields.add( new DBField("Fails", "INTEGER") );
        statTable.fields.add( new DBField("Quits", "INTEGER") );
        statTable.fields.add( new DBField("ComponentCode", "VARCHAR(7)", false,
                                            true, componentTable.name, componentTable.fields.get(0).fieldName) );

        reqTable = new DBTable("Requirements");
        tables.add(reqTable);
        reqTable.fields.add( new DBField("Code", "INTEGER", true) );
        reqTable.fields.add( new DBField("Name", "VARCHAR(256)"));

        reqCompTable = new DBTable("RequirementsComponent");
        tables.add(reqCompTable);
        reqCompTable.fields.add( new DBField("Semester", "INTEGER") );
        reqCompTable.fields.add( new DBField("RequirementsCode", "INTEGER", true,
                                            true, reqTable.name, "Code") );
        reqCompTable.fields.add( new DBField("ComponentCode", "VARCHAR(7)", true,
                                            true, componentTable.name, "Code") );

        Log.e("DATABASE HANDLER", "Database was created.");
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e("DATABASE HANDLER", "creating tables:");
        for(DBTable t : tables) {
            String script = t.toString();

            if(script != null) {
                db.execSQL(t.toString());
                Log.e(t.name, t.toString());
            }
        }
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e("DATABASE HANDLER", "updating database...");

        for(DBTable t : tables)
            db.execSQL("DROP TABLE IF EXISTS " + t.name);

        // Create tables again
        onCreate(db);
    }

    public boolean hasComponent(Component comp) {
        // TODO: verificar se tal componente já foi inserido no DB
        return false;
    }

    public void insertComponent(Component comp) {
        SQLiteDatabase db = this.getWritableDatabase();

        String s = "INSERT INTO ";
        s += componentTable.name + " VALUES (";
        s += "\"" + comp.getCode() + "\", ";
        s += "\"" + comp.getName() + "\", ";
        s += comp.getWorkload() + ")";

        db.execSQL(s);
        db.close();

        Log.e("DATABASE INSERT", s);
    }

    public void insertStat(StatList.Entry entry) {
        // TODO
    }

    public void insertClass(ClassList.Entry entry) {
        // TODO
    }

    public Requirements getRequirements(String code) {
        // TODO
        return null;
    }

    public StatList getStatList(String code) {
        // TODO
        return null;
    }

    public ClassList getClassList(String code) {
        // TODO
        return null;
    }

}
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

    private class DBField {
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

            if (pk) str += " PRIMARY KEY";
            if (fk) str += ", FOREIGN KEY(" + fieldName + ") REFERENCES (" + refTable + "(" + refField + ")";

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
            str += fields.get(0).toString();
            for(int i = 1; i < fields.size(); i++)
                str += ", " + fields.get(i).toString();
            str += ")";

            return str;
        }
    }

    // metadata
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "PlanmatDB";
    private static List<DBTable> tables;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        tables = new ArrayList<DBTable>();

        //Create database scheme
        DBTable t1 = new DBTable("Table1");
        t1.fields.add( new DBField("Field1", "INTEGER") );
        tables.add(t1);

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
        // Drop older table if existed
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again -> [LUÍS] Se não dropar
        // as tabelas antes vai dar erro.
        onCreate(db);
    }

    public boolean hasComponent(Component comp) {
        // TODO: verificar se tal componente já foi inserido no DB
        return false;
    }

    public void insertComponent(Component comp) {
        // TODO
    }

    public void insertStat(StatList.Entry entry) {
        // TODO
    }

    public void insertClass(ClassList.Entry entry) {
        // TODO
        SQLiteDatabase db = getWritableDatabase();
        String s = "INSERT INTO Table1 VALUES (" + entry.getID() + ")";
        db.execSQL(s);

        Log.e("DATABASE HANDLER", "Inserted value");
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
package planmat.internaldata;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import planmat.datarepresentation.ClassList;
import planmat.datarepresentation.Component;
import planmat.datarepresentation.Requirements;
import planmat.datarepresentation.Semester;
import planmat.datarepresentation.StatList;

/**
 * Created by Luisa on 09/12/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // metadata
    private static final int DATABASE_VERSION = 6;
    private static final String DATABASE_NAME = "PlanmatDB";
    private static DBTable componentTable, classTable, statTable, reqCompTable, reqTable;
    private static List<DBTable> tables;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        tables = new ArrayList<>();

        //Create database scheme
        componentTable = new DBTable("Component");
        tables.add(componentTable);
        componentTable.fields.add( new DBField("Code", "VARCHAR(32)", true) );
        componentTable.fields.add( new DBField("Name", "VARCHAR(256)") );
        componentTable.fields.add( new DBField("Workload", "INTEGER") );

        classTable = new DBTable("Class");
        tables.add(classTable);
        classTable.fields.add( new DBField("Code", "VARCHAR(8)", true) );
        classTable.fields.add( new DBField("Year", "INTEGER", true) );
        classTable.fields.add( new DBField("Semester", "INTEGER", true) );
        classTable.fields.add( new DBField("Hour", "VARCHAR(16)") );
        classTable.fields.add( new DBField("Professors", "VARCHAR(256)") );
        classTable.fields.add( new DBField("ComponentCode", "VARCHAR(32)", true,
                                            true, componentTable.name, componentTable.fields.get(0).fieldName) );

        statTable = new DBTable("Stat");
        tables.add(statTable);
        statTable.fields.add( new DBField("Code", "INTEGER", true) );
        statTable.fields.add( new DBField("Year", "INTEGER", true) );
        statTable.fields.add( new DBField("Semester", "INTEGER", true) );
        statTable.fields.add( new DBField("Successes", "INTEGER") );
        statTable.fields.add( new DBField("Fails", "INTEGER") );
        statTable.fields.add( new DBField("Quits", "INTEGER") );
        statTable.fields.add( new DBField("ComponentCode", "VARCHAR(32)", true,
                                            true, componentTable.name, componentTable.fields.get(0).fieldName) );

        reqTable = new DBTable("Requirements");
        tables.add(reqTable);
        reqTable.fields.add( new DBField("Code", "VARCHAR(32)", true) );
        reqTable.fields.add( new DBField("Name", "VARCHAR(256)"));

        reqCompTable = new DBTable("RequirementsComponent");
        tables.add(reqCompTable);
        reqCompTable.fields.add( new DBField("Semester", "INTEGER") );
        reqCompTable.fields.add( new DBField("RequirementsCode", "VARCHAR(32)", true,
                                            true, reqTable.name, "Code") );
        reqCompTable.fields.add( new DBField("ComponentCode", "VARCHAR(32)", true,
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
        String s = "SELECT Code ";
        s += "FROM " + componentTable.name + " ";
        s += "WHERE Code = \"" + comp.getCode() + "\"";

        Cursor cursor = getReadableDatabase().rawQuery(s, null);
        if (cursor.moveToNext()) {
            Log.d("Component Exists", comp.getCode());
            return true;
        } else {
            return false;
        }
    }

    public void insertComponent(Component comp, Requirements req, int i) {
        SQLiteDatabase db = this.getWritableDatabase();

        String s = "INSERT INTO ";
        s += componentTable.name + " VALUES (";
        s += "\"" + comp.getCode() + "\", ";
        s += "\"" + comp.getName().replace("\"", "") + "\", ";
        s += comp.getWorkload() + ")";

        db.execSQL(s);

        s = "INSERT INTO ";
        s += reqCompTable.name + " VALUES (";
        s += i + ", ";
        s += "\"" + req.getID() + "\", ";
        s += "\"" + comp.getCode() + "\")";

        db.execSQL(s);
        db.close();

        Log.e("DATABASE INSERT", s);
    }

    public Requirements getRequirements(String code) {
        ArrayList<Semester> semesters = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM " + componentTable.name
                + " JOIN " + reqCompTable.name
                + " ON " + reqCompTable.fields.get(2).fieldName + " = " + componentTable.fields.get(0).fieldName
                + " WHERE " + reqCompTable.fields.get(1).fieldName + " = \"" + code + "\""
                + " ORDER BY " + reqCompTable.fields.get(0).fieldName;
        Cursor cursor = db.rawQuery(query, null);

        Log.e("DATABASE SELECT", query);

        int currentSemester = -1;
        Semester semester = new Semester(new ArrayList<Component>());

        while(cursor.moveToNext()) {
            int newSemester = cursor.getInt(3);
            if (newSemester != currentSemester) {
                currentSemester = newSemester;
                semester = new Semester(new ArrayList<Component>());
            }
            String c = cursor.getString(0);
            String name = cursor.getString(1);
            int workload = cursor.getInt(2);
            Component comp = new Component(c, name, workload);
            semester.getComponents().add(comp);
        }

        Requirements req = new Requirements(code, semesters);
        return req;
    }

    public void insertStat(StatList.Entry entry, String code) { // It's incomplete
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            String s = "INSERT INTO ";
            s += statTable.name + " VALUES(";
            s += entry.getCode() + ", ";
            s += entry.getYear() + ", ";
            s += entry.getSemester() + ", ";
            s += entry.getSuccesses() + ", ";
            s += entry.getFails() + ", ";
            s += entry.getQuits() + ", ";
            s += "\"" + code + "\"";
            s += ")";
            db.execSQL(s);
            db.close();
            Log.e("DATABASE INSERT", s);
        } catch(SQLiteConstraintException e) {
            e.printStackTrace();
        }
    }

    public StatList getStatList(String code) {
        SQLiteDatabase db = this.getReadableDatabase();
        String s = "SELECT * ";
        s += "FROM " + statTable.name + " ";
        s += "WHERE ComponentCode = \"" + code + "\";";
        StatList list = new StatList();
        Cursor cursor = db.rawQuery(s, null);
        while(cursor.moveToNext()) {
            String id = cursor.getString(0);
            int year = cursor.getInt(1);
            int sem = cursor.getInt(2);
            int suc = cursor.getInt(3);
            int fail = cursor.getInt(4);
            int quit = cursor.getInt(5);
            StatList.Entry entry = new StatList.Entry(id, suc, quit, fail, year, sem);
            list.getEntries().add(entry);
        }
        db.close();
        Log.e("DATABASE SELECT", s);
        return list;
    }


    public void insertClass(ClassList.Entry entry, String code) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            // id, year, semester, hour, professors, componentCode
            String s = "INSERT INTO ";
            s += classTable.name + " VALUES(";
            s += "\"" + entry.getCode() + "\"" + ", ";
            s += entry.getYear() + ", ";
            s += entry.getSemester() + ", ";
            s += "\"" + entry.getHour() + "\"" + ", ";
            s += "\"" + entry.getProfessors() + "\"" + ", ";
            s += "\"" + code + "\"";
            s += ")";
            db.execSQL(s);
            db.close();
            Log.e("DATABASE INSERT", s);
        } catch(SQLiteConstraintException e) {
            e.printStackTrace();
        }
    }

    public ClassList getClassList(String code) {
        SQLiteDatabase db = this.getReadableDatabase();
        String s = "SELECT * ";
        s += "FROM " + classTable.name + " ";
        s += "WHERE ComponentCode = \"" + code + "\";";
        ClassList list = new ClassList();
        Cursor cursor = db.rawQuery(s, null);
        while(cursor.moveToNext()) {
            String id = cursor.getString(0);
            int year = cursor.getInt(1);
            int sem = cursor.getInt(2);
            String h = cursor.getString(3);
            String prof = cursor.getString(4);
            Log.e("Code", id);
            Log.e("ano", "" + year);
            Log.e("semstre", "" + sem);
            Log.e("horario", h);
            Log.e("prof", prof);
            ClassList.Entry entry = new ClassList.Entry(id, prof, h, year, sem);
            list.getEntries().add(entry);
        }
        db.close();
        Log.e("DATABASE SELECT", s);
        return list;
    }

}

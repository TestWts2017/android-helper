package com.wings.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.wings.utils.Column;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;


/**
 * Purpose: Create SQLite Database for android
 *
 * @author HetalD
 * Created On June 17,2019
 * Modified On June 20,2019
 */

public class SQLiteDBHelper extends SQLiteOpenHelper {

    private HashSet<String> tables = new HashSet<>();
    private String DATABASE_NAME, SQL = "";
    private ArrayList<Column> columns = new ArrayList<>();
    private SQLiteDatabase writableDatabase;
    private ContentValues contentValues = new ContentValues();
    private boolean initedDb = false;
    private Context context;
    private SQLiteDatabase.CursorFactory factory;
    private int version;
    private DatabaseErrorHandler errorHandler;

    /**
     * Initialize database with context and database name
     * <p>
     * Use:
     * <p>
     * 1)To Add Table with Column:
     * SQLiteDBHelper liteClass = SQLiteDBHelper.init(this, "TESTDB")
     * .setTableName("DEMO_TABLE")
     * .addColumn(new Column("Name", "text"))
     * .addColumn(new Column("Age", "text", "unique"))
     * .doneTableColumn();
     * <p>
     * 2) To Add data in table
     * <p>
     * liteClass.addData(1, c1)
     * .addData(2, c2)
     * .doneDataAdding("DEMO_TABLE");
     * </p>
     * 3) To update data based on index
     * <p>
     * liteClass.updateData(1, "Nikunj")
     * .rowID(1, "DEMO_TABLE");
     * </p>
     *
     * @param context Context of the activity
     * @param dbName  String name of the database
     * @return SQLiteDBHelper
     */
    public static SQLiteDBHelper init(Context context, String dbName) {
        if (!dbName.endsWith(".db"))
            dbName += ".db";
        dbName = dbName.replaceAll(" ", "_");
        return new SQLiteDBHelper(context, dbName, null, 1);
    }

    /**
     * Initialize database with context, database name and database version
     *
     * @param context Context of the activity
     * @param dbName  String name of the database
     * @param version Integer version of the database
     * @return SQLiteDBHelper
     */
    public static SQLiteDBHelper init(Context context, String dbName, int version) {
        if (!dbName.endsWith(".db"))
            dbName += ".db";
        dbName = dbName.replaceAll(" ", "_");
        return new SQLiteDBHelper(context, dbName, null, version);
    }


    /**
     * Initialize database with context, database name, SQLite Cursor factory and database version
     *
     * @param context Context of the activity
     * @param dbName  String name of the database
     * @param factory SQLite Cursor factory
     * @param version Integer version of the database
     * @return SQLiteDBHelper
     */
    public static SQLiteDBHelper init(Context context, String dbName, SQLiteDatabase.CursorFactory factory, int version) {
        if (!dbName.endsWith(".db"))
            dbName += ".db";
        dbName = dbName.replaceAll(" ", "_");
        return new SQLiteDBHelper(context, dbName, factory, version);
    }


    /**
     * Initialize database with context, database name, SQLite Cursor factory, database version and database error handler
     *
     * @param context      Context of the activity
     * @param dbName       String name of the database
     * @param factory      SQLite Cursor factory
     * @param version      Integer version of the database
     * @param errorHandler Database error handler
     * @return SQLiteDBHelper
     */
    public static SQLiteDBHelper init(Context context, String dbName, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        if (!dbName.endsWith(".db"))
            dbName += ".db";
        dbName = dbName.replaceAll(" ", "_");
        return new SQLiteDBHelper(context, dbName, factory, version, errorHandler);
    }


    /**
     * Initialize the database
     */
    private void initDatabase() {
        writableDatabase = getWritableDatabase();
        initedDb = true;
    }


    /**
     * Create SQLite database - default method of SQLite
     *
     * @param db SQLite database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        this.writableDatabase = db;
        db.execSQL(SQL);
    }

    /**
     * Upgrade database if version of database is changed - default method of SQLite
     *
     * @param db         SQLite database
     * @param oldVersion Integer old version of the database
     * @param newVersion Integer new version of the database
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (tables != null && tables.size() > 0) {
            Iterator<String> i = tables.iterator();
            while (i.hasNext()) {
                db.execSQL(" DROP TABLE IF EXISTS " + i.next());
            }
        }
        onCreate(db);
    }

    /**
     * Set the name of the table
     *
     * @param tableName String name of the table
     * @return SQLiteDBHelper
     */
    public SQLiteDBHelper setTableName(String tableName) {
        String table_name = tableName.replaceAll(" ", "_");
        tables.add(table_name);
        return this;
    }

    /**
     * Add column into the table
     *
     * @param column Column with column name and coumn type (Example: addColumn(new Column("C1","text")))
     * @return SQLiteDBHelper
     */
    public SQLiteDBHelper addColumn(Column column) {
        columns.add(column);
        return this;
    }

    /**
     * Complete the process of column creation and make the table
     *
     * @param tableName name of table
     * @return SQLiteDBHelper
     */
    public SQLiteDBHelper doneTableColumn(String tableName) {
        SQL = " CREATE TABLE " + tableName + " ( ID INTEGER PRIMARY KEY AUTOINCREMENT, ";
        for (int i = 0; i < columns.size(); i++) {
            SQL += " " + columns.get(i).columnName + " " + columns.get(i).columnDataType + " ";
            if (i == columns.size() - 1) {
                SQL += " ) ";
            } else {
                SQL += " , ";
            }
        }

        if (!initedDb || writableDatabase == null) initDatabase();
        return this;
    }

    /**
     * Add data into table
     *
     * @param columnNumber Integer column number in which data will added
     * @param data         String data to add into given column
     * @return SQLiteDBHelper
     */
    public SQLiteDBHelper addData(int columnNumber, String data) {
        if (!initedDb || writableDatabase == null) initDatabase();
        contentValues.put(columns.get(columnNumber - 1).columnName, data);
        return this;
    }

    /**
     * Add data into table
     *
     * @param columnName String column name in which data will added
     * @param data       String data to add into given column
     * @return SQLiteDBHelper
     */
    public SQLiteDBHelper addData(String columnName, String data) {
        columnName = columnName.replaceAll(" ", "_");
        if (!initedDb || writableDatabase == null) initDatabase();
        contentValues.put(columnName, data);
        return this;
    }

    /**
     * Add data into table
     *
     * @param columnNumber Integer column number in which data will added
     * @param data         Integer data to add into given column
     * @return SQLiteDBHelper
     */
    public SQLiteDBHelper addData(int columnNumber, int data) {
        if (!initedDb || writableDatabase == null) initDatabase();
        contentValues.put(columns.get(columnNumber - 1).columnName, data);
        return this;
    }

    /**
     * Add data into table
     *
     * @param columnName String column name in which data will added
     * @param data       Integer data to add into given column
     * @return SQLiteDBHelper
     */
    public SQLiteDBHelper addData(String columnName, int data) {
        columnName = columnName.replaceAll(" ", "_");
        if (!initedDb || writableDatabase == null) initDatabase();
        contentValues.put(columnName, data);
        return this;
    }


    /**
     * Complete Add operation by giving true(if done) or false(if not done)
     *
     * @param tableName name of table
     * @return boolean
     */
    public boolean doneDataAdding(String tableName) {
        long result = writableDatabase.insert(tableName, null, contentValues);
        contentValues = new ContentValues();

        if (result == -1)
            return false;
        else
            return true;
    }

    /**
     * Update String data of the table by given column number
     *
     * @param columnNumber Integer column number in which you want to update data
     * @param data         String data which you want to update
     * @return SQLiteDBHelper
     */
    public SQLiteDBHelper updateData(int columnNumber, String data) {
        if (!initedDb || writableDatabase == null) initDatabase();
        contentValues.put(columns.get(columnNumber - 1).columnName, data);
        return this;
    }

    /**
     * Update data to table
     *
     * @param tableName     table name
     * @param contentValues content values
     * @param query         query
     * @return the number of rows affected
     */
    public int updateData(String tableName, ContentValues contentValues, String query) {
        if (!initedDb || writableDatabase == null) initDatabase();
        return writableDatabase.update(tableName, contentValues, query, null);
    }

    /**
     * Update - Get the row by ID to update the data
     *
     * @param id        Integer ID of the row
     * @param tableName name of table
     * @return boolean - returns true if row is updated, false if row is not updated
     */
    public boolean rowID(int id, String tableName) {
        try {
            return writableDatabase.update(tableName, contentValues, "id = ?", new String[]{String.valueOf(id)}) > 0;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get all the column from table
     *
     * @return String[] array of columns
     */
    public String[] getAllColumns() {
        String allColNames[] = new String[columns.size() + 1];
        allColNames[0] = "ID";
        for (int i = 0; i < columns.size(); i++) {
            allColNames[i + 1] = columns.get(i).columnName;
        }
        return allColNames;
    }


    /**
     * Get all data from table
     *
     * @param tableName name of table
     * @return Cursor
     */
    public Cursor getAllData(String tableName) {
        if (!initedDb || writableDatabase == null) initDatabase();
        return writableDatabase.rawQuery("select * from " + tableName, null);
    }

    /**
     * Get all data from table by order either in ascending or descending
     *
     * @param columnNumber Integer column number to which you want to order
     * @param ascending    boolean - true for ascending - false for descending
     * @param tableName    name of table
     * @return cursor
     */
    public Cursor getAllDataOrderedBy(int columnNumber, boolean ascending, String tableName) {
        String postfix = ascending ? "" : " DESC ";
        String colNam = columnNumber == 0 ? " ID " : columns.get(columnNumber - 1).columnName;
        if (!initedDb || writableDatabase == null) initDatabase();
        Cursor res = writableDatabase.rawQuery("select * from " + tableName + " ORDER BY " + colNam + postfix, null);
        return res;
    }

    /**
     * Get one row from the table by row ID
     *
     * @param rowID     Integer row ID for get any row from table
     * @param tableName name of table
     * @return cursor
     */
    public Cursor getOneRowData(int rowID, String tableName) {
        if (!initedDb || writableDatabase == null) initDatabase();
        String allColNames[] = new String[columns.size() + 1];
        allColNames[0] = "ID";
        for (int i = 0; i < columns.size(); i++) {
            allColNames[i + 1] = columns.get(i).columnName;
        }
        Cursor cursor = writableDatabase.query(tableName,
                allColNames, allColNames[0].toString() + "=?",
                new String[]{String.valueOf(rowID)},
                null, null, null, "1");

        if (cursor.getCount() > 0) {
            return cursor;
        } else {
            return null;
        }
    }

    /**
     * Get data from the table by column number and string value
     *
     * @param columnNumber Integer column number
     * @param value        String value for give a condition to get data
     * @param tableName    name of table
     * @return cursor
     */
    public Cursor getOneRowData(int columnNumber, String value, String tableName) {
        if (!initedDb || writableDatabase == null) initDatabase();
        String allColNames[] = new String[columns.size() + 1];
        allColNames[0] = "ID";
        for (int i = 0; i < columns.size(); i++) {
            allColNames[i + 1] = columns.get(i).columnName;
        }
        Cursor cursor = writableDatabase.query(tableName,
                allColNames, allColNames[columnNumber].toString() + "=?",
                new String[]{value},
                null, null, null, "1");

        if (cursor.getCount() > 0) {
            return cursor;
        } else {
            return null;
        }
    }


    /**
     * Get data from the table by column name and string value
     *
     * @param columnName String column name
     * @param value      String value for give a condition to get data
     * @param tableName  name of table
     * @return cursor
     */
    public Cursor getOneRowData(String columnName, String value, String tableName) {
        if (!initedDb || writableDatabase == null) initDatabase();
        String allColNames[] = new String[columns.size() + 1];
        allColNames[0] = "ID";
        for (int i = 0; i < columns.size(); i++) {
            allColNames[i + 1] = columns.get(i).columnName;
        }
        Cursor cursor = writableDatabase.query(tableName,
                allColNames, " " + columnName + " " + "=?",
                new String[]{value},
                null, null, null, "1");

        if (cursor.getCount() > 0) {
            return cursor;
        } else {
            return null;
        }
    }


    /**
     * Search data from the table
     *
     * @param columnNumber  Integer column number in which you want to search
     * @param valueToSearch String value which you want to search
     * @param limit         Integer limit - to set the limit of the result
     * @param tableName     name of table
     * @return cursor
     */
    public Cursor searchInColumn(int columnNumber, String valueToSearch, int limit, String tableName) {
        if (!initedDb || writableDatabase == null) initDatabase();
        String allColNames[] = new String[columns.size() + 1];
        allColNames[0] = "ID";
        for (int i = 0; i < columns.size(); i++) {
            allColNames[i + 1] = columns.get(i).columnName;
        }
        Cursor cursor = writableDatabase.query(tableName,
                allColNames, allColNames[columnNumber].toString() + "=?",
                new String[]{valueToSearch},
                null, null, null, limit == -1 ? null : String.valueOf(limit));

        if (cursor.getCount() > 0) {
            return cursor;
        } else {
            return null;
        }
    }


    /**
     * Search data from the table
     *
     * @param columnName    String column name in which you want to search
     * @param valueToSearch String value which you want to search
     * @param limit         Integer limit - to set the limit of the result
     * @param tableName     name of table
     * @return cursor
     */
    public Cursor searchInColumn(String columnName, String valueToSearch, int limit, String tableName) {
        if (!initedDb || writableDatabase == null) initDatabase();
        String allColNames[] = new String[columns.size() + 1];
        allColNames[0] = "ID";
        for (int i = 0; i < columns.size(); i++) {
            allColNames[i + 1] = columns.get(i).columnName;
        }
        Cursor cursor = writableDatabase.query(tableName,
                allColNames, " " + columnName + " " + "=?",
                new String[]{valueToSearch},
                null, null, null, limit == -1 ? null : String.valueOf(limit));

        if (cursor.getCount() > 0) {
            return cursor;
        } else {
            return null;
        }
    }

    /**
     * Match column from the give data
     *
     * @param columnsToMatch String array of column name from table - to match the column
     * @param valuesToMatch  String array of values - to match the data from give array of column
     * @param tableName      name of table
     * @return boolean - returns true if match otherwise false if not match
     */
    public boolean matchColumns(String columnsToMatch[], String valuesToMatch[], String tableName) {
        String query = "";
        for (int i = 0; i < columnsToMatch.length; i++) {
            query += columnsToMatch[i] + " = ? ";
            if (i != columnsToMatch.length - 1) {
                query += " AND ";
            }
        }
        Cursor cursor = writableDatabase.query(tableName, columnsToMatch, query, valuesToMatch, null, null, null);
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Delete the row by it's ID
     *
     * @param id        Integer ID of the row
     * @param tableName name of table
     * @return boolean - true if row is deleted, false if not delted
     */
    public boolean deleteRow(int id, String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(tableName, "id = ?", new String[]{String.valueOf(id)}) == 1;
    }

    /**
     * Delete row by column number and integer data
     *
     * @param columnNumber Integer column number in which you want delete data
     * @param valueToMatch Integer data to delete from the row
     * @param tableName    name of table
     * @return boolean - true if row is deleted, false if not deleted
     */
    public boolean deleteRow(int columnNumber, int valueToMatch, String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(tableName, columns.get(columnNumber - 1).columnName + " = ?", new String[]{String.valueOf(valueToMatch)}) == 1;
    }


    /**
     * Delete row by column number and string data
     *
     * @param columnNumber Integer column number in which you want delete data
     * @param valueToMatch String data to delete from the row
     * @param tableName    name of table
     * @return boolean - true if row is deleted, false if not deleted
     */
    public boolean deleteRow(int columnNumber, String valueToMatch, String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(tableName, columns.get(columnNumber - 1).columnName + " = ?", new String[]{valueToMatch}) == 1;
    }

    /**
     * Delete row by column name and integer data
     *
     * @param columnName   String column name in which you want delete data
     * @param valueToMatch Integer data to delete from the row
     * @param tableName    name of table
     * @return boolean - true if row is deleted, false if not deleted
     */
    public boolean deleteRow(String columnName, int valueToMatch, String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(tableName, columnName + " = ?", new String[]{String.valueOf(valueToMatch)}) == 1;
    }

    /**
     * Delete row by column name and string data
     *
     * @param columnName   String column name in which you want delete data
     * @param valueToMatch String data to delete from the row
     * @param tableName    name of table
     * @return boolean - true if row is deleted, false if not deleted
     */
    public boolean deleteRow(String columnName, String valueToMatch, String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(tableName, columnName + " = ?", new String[]{valueToMatch}) == 1;
    }

    /**
     * Delete all data from table
     *
     * @param tableName name of table
     */
    public void deleteAllDataFromTable(String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + tableName);
    }


    /**
     * Delete every table data
     */
    public void deleteAllTableData() {
        SQLiteDatabase db = this.getWritableDatabase();
        if (tables != null && tables.size() > 0) {
            Iterator<String> i = tables.iterator();
            while (i.hasNext()) {
                db.execSQL("delete from " + i.next());
            }
        }
    }


    /**
     * Delete database
     *
     * @param dbName database name
     */
    public void deleteDatabase(String dbName) {
        if (context != null) {
            context.deleteDatabase(dbName);
        }
    }

    // Saving, just in case
    // Codes below this might once or never be used

    private SQLiteDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

        //
        this.context = context;
        this.DATABASE_NAME = name;
        this.factory = factory;
        this.version = version;
    }

    private SQLiteDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);

        //
        this.context = context;
        this.DATABASE_NAME = name;
        this.factory = factory;
        this.version = version;
        this.errorHandler = errorHandler;
    }

}

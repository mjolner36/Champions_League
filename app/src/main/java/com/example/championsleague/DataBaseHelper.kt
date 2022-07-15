package com.example.championsleague

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.io.*


class DbHelper(private val vContext: Context) :
    SQLiteOpenHelper(vContext, DB_NAME, null, 1) {
    private val vDatabase: SQLiteDatabase
    /**
     * No need for build version check as getDataBasePath works for all versions
     * No need for open and close of Database, just open it once for the lifetime (more efficient)
     */
    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private fun checkDataBase(): Boolean {
        /**
         * Does not open the database instead checks to see if the file exists
         * also creates the databases directory if it does not exists
         * (the real reason why the database is opened, which appears to result in issues)
         */
        val db = File(vContext.getDatabasePath(DB_NAME).path) //Get the file name of the database
        Log.d("DBPATH", "DB Path is " + db.path)
        if (db.exists()) return true // If it exists then return doing nothing

        // Get the parent (directory in which the database file would be)
        val dbdir = db.parentFile
        // If the directory does not exist then make the directory (and higher level directories)
        if (!dbdir.exists()) {
            db.parentFile.mkdirs()
            dbdir.mkdirs()
        }
        return false
    }

    @Throws(SQLiteException::class)
    fun copyDB() {
        try {
            val myInput: InputStream = vContext.assets.open(DB_NAME)
            val outputFileName = vContext.getDatabasePath(DB_NAME).path //<<<<<<<<<< changed
            Log.d("LIFECYCLE", outputFileName)
            val myOutput: OutputStream = FileOutputStream(outputFileName)
            val buffer = ByteArray(1024)
            var length: Int
            while (myInput.read(buffer).also { length = it } > 0) {
                myOutput.write(buffer, 0, length)
            }
            myOutput.flush()
            myOutput.close()
            myInput.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }//cursor.getString(cursor.getColumnIndex("index")), //<<<<<<< changed due to column name change

    //db.close(); // inefficient to keep on opening and closing db
    //Cursor cursor = db.rawQuery( "SELECT * FROM lyrics" , null); // used query method generally preferred to rawQuery
    //if( cursor == null) return null; // Cursor will not be null may be empty
    //cursor.moveToFirst(); // changed to use simpler loop
    val allMatches: List<Any>
        @SuppressLint("Range")
        get() {
            val temp: MutableList<Match> = ArrayList()
            val cursor: Cursor = vDatabase.query("matches", null, null, null, null, null, null)
            //Cursor cursor = db.rawQuery( "SELECT * FROM lyrics" , null); // used query method generally preferred to rawQuery
            //if( cursor == null) return null; // Cursor will not be null may be empty
            //cursor.moveToFirst(); // changed to use simpler loop
            while (cursor.moveToNext()) {
                val match =
                    Match( //cursor.getString(cursor.getColumnIndex("index")), //<<<<<<< changed due to column name change
                        cursor.getInt(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("team1")),
                        cursor.getString(cursor.getColumnIndex("team2")),
                        cursor.getString(cursor.getColumnIndex("score1")),
                        cursor.getString(cursor.getColumnIndex("score2")),
                        cursor.getString(cursor.getColumnIndex("year")),
                        cursor.getString(cursor.getColumnIndex("winner")),
                        cursor.getString(cursor.getColumnIndex("image1")),
                        cursor.getString(cursor.getColumnIndex("image2"))
                    )
                temp.add(match)
            }
            cursor.close()
            //db.close(); // inefficient to keep on opening and closing db
            return temp
        }

    override fun onCreate(db: SQLiteDatabase) {}
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    companion object {
        private const val DB_NAME = "matches.db"
    }

    init {
        // Copy the DB if need be when instantiating the DbHelper
        if (!checkDataBase()) {
            copyDB()
        }
        vDatabase = this.writableDatabase //Get the database when instantiating
    }
}
package com.example.data.local.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.room.RoomMasterTable
import com.example.data.local.room.CurrencyExchange


class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    // below is the method for creating a database by a sqlite query
    override fun onCreate(db: SQLiteDatabase) {
        // below is a sqlite query, where column names
        // along with their data types is given
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                from_currency + " TEXT," +
                to_currency + " TEXT," +
                exchange_rate + " TEXT," +
                amount + " TEXT," +
                total + " TEXT," +
                date + " TEXT" + ")")

        // we are calling sqlite
        // method for executing our query
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        // this method is to check if table already exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    // This method is for adding data in our database
    fun addCurrency(from : String, to : String,exchange_rate1:String,amount1:String,total1:String,date1:String ){

        // below we are creating
        // a content values variable
        val values = ContentValues()

        // we are inserting our values
        // in the form of key-value pair
        values.put(from_currency, from)
        values.put(to_currency, to)
        values.put(exchange_rate, exchange_rate1)
        values.put(amount, amount1)
        values.put(total, total1)
        values.put(date, date1)

        // here we are creating a
        // writable variable of
        // our database as we want to
        // insert value in our database
        val db = this.writableDatabase

        // all values are inserted into database
        db.insert(TABLE_NAME, null, values)

        // at last we are
        // closing our database
        db.close()
    }

    // below method is to get
    // all data from our database
    open fun readCurrency(): ArrayList<CurrencyExchange>? {
        // on below line we are creating a
        // database for reading our database.
        val db = this.readableDatabase

        // on below line we are creating a cursor with query to read data from database.
        val cursorCourses = db.rawQuery("SELECT * FROM " + TABLE_NAME, null)

        // on below line we are creating a new array list.
        val CurrencyModelArrayList: ArrayList<CurrencyExchange> = ArrayList()

        // moving our cursor to first position.
        if (cursorCourses.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                CurrencyModelArrayList.add(
                    CurrencyExchange(
                        cursorCourses.getString(1),
                        cursorCourses.getString(2),
                        cursorCourses.getString(3),
                        cursorCourses.getString(4),
                        cursorCourses.getString(5),
                        cursorCourses.getString(6),
                    )
                )
            } while (cursorCourses.moveToNext())
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursorCourses.close()
        return CurrencyModelArrayList
    }

    companion object{
        // here we have defined variables for our database

        // below is variable for database name
        private val DATABASE_NAME = "Currency Exchanges"

        // below is the variable for database version
        private val DATABASE_VERSION = 1

        // below is the variable for table name
        val TABLE_NAME = "currency"

        // below is the variable for id column
        val ID_COL = "id"

        // below is the variable for name column
        val from_currency = "from_currency"

        // below is the variable for age column
        val to_currency = "to_currency"
        val exchange_rate = "exchange_rate"
        val amount = "amount"
        val total = "total"
        val date = "date"
    }
}


package com.kotlin.cursos.sqliteexample

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDBAdapter (context : Context) {
    private val nombreBd = "BdPersona"
    private var myContext : Context? = null
    private var mDbHelper : MyDBHelper? = null
    private var sqLiteDatabase : SQLiteDatabase? = null
    private var version = 1

    init{
        this.myContext = context
        mDbHelper = MyDBHelper(context, nombreBd,null,version)
    }

    inner class MyDBHelper (context: Context?,name:String?,factory: SQLiteDatabase.CursorFactory?,
                            version:Int):SQLiteOpenHelper(context,name,factory,version){
        override fun onCreate(db: SQLiteDatabase?) {
            val consulta = "CREATE TABLE persona (id Integer primary key " +
                    "autoincrement, nombre text, facultad integer);"
            db?.execSQL(consulta)
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            val consulta = "DROP TABLE IF EXISTS persona"
            db?.execSQL(consulta)
            onCreate(db)
        }
    }

    public fun open(){
        sqLiteDatabase = mDbHelper?.writableDatabase
    }

    public fun insertarPersona(nombre: String, facultad : Int){
        val cv: ContentValues = ContentValues()
        cv.put("nombre", nombre)
        cv.put("facultad",facultad)
        sqLiteDatabase?.insert("persona",null,cv)
    }

    public fun eliminarPersona(){
        sqLiteDatabase!!.delete("persona",null,null)
    }

    public fun obtenerPersonas(): List<String>{
        var lista : MutableList<String> = mutableListOf()
        var cursor: Cursor = sqLiteDatabase?.query("persona",null,null,
                null,null,null,null)!!
        if(cursor.moveToFirst()){
            do{
                lista.add(cursor.getString(1))
            }while(cursor.moveToNext())
        }
        return lista
    }

}
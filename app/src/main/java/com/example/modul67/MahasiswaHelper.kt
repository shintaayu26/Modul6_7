package com.example.modul67

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MahasiswaHelper (context : Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private val DATABASE_NAME = "mahasiswa.db"
        private val DATABASE_VERSION = 1

        private val SQL_CREATE_ENTRIES = " CREATE TABLE " +
                "${MahasiswaContract.MahasiswaEntry.TABLE_NAME}("+
                "${MahasiswaContract.MahasiswaEntry.COLOUMN_EMAIL} TEXT PRIMARY KEY,"+
                "${MahasiswaContract.MahasiswaEntry.COLOUMN_NAMA} TEXT,"+
                "${MahasiswaContract.MahasiswaEntry.COLOUMN_NIM} TEXT,"+
                "${MahasiswaContract.MahasiswaEntry.COLOUMN_PASSWORD} TEXT)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${MahasiswaContract.MahasiswaEntry.TABLE_NAME}"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    fun getData() : ArrayList<Mahasiswa>{
        val db = writableDatabase
        val mahasiswaList = ArrayList<Mahasiswa>()

        val sql = " SELECT "+
                "${MahasiswaContract.MahasiswaEntry.COLOUMN_EMAIL}, "+
                "${MahasiswaContract.MahasiswaEntry.COLOUMN_NAMA}, "+
                "${MahasiswaContract.MahasiswaEntry.COLOUMN_NIM}, "+
                "${MahasiswaContract.MahasiswaEntry.COLOUMN_PASSWORD} "+
                " FROM ${MahasiswaContract.MahasiswaEntry.TABLE_NAME} " +
                " ORDER BY ${MahasiswaContract.MahasiswaEntry.COLOUMN_NAMA} ASC "

        val cursor = db.rawQuery(sql, null)

        with(cursor){
            while (moveToNext()){
                val email = getString(getColumnIndexOrThrow(MahasiswaContract.MahasiswaEntry.COLOUMN_EMAIL))
                val nama = getString(getColumnIndexOrThrow(MahasiswaContract.MahasiswaEntry.COLOUMN_NAMA))
                val nim = getString(getColumnIndexOrThrow(MahasiswaContract.MahasiswaEntry.COLOUMN_NIM))
                val password = getString(getColumnIndexOrThrow(MahasiswaContract.MahasiswaEntry.COLOUMN_PASSWORD))

                val mahasiswa = Mahasiswa(email,nama,nim,password)
                mahasiswaList.add(mahasiswa)
            }
        }
        cursor.close()
        return mahasiswaList
    }

    fun insertData(mahasiswa: Mahasiswa){
        val db = writableDatabase
        val sql = " INSERT INTO ${MahasiswaContract.MahasiswaEntry.TABLE_NAME} " +
                "(${MahasiswaContract.MahasiswaEntry.COLOUMN_EMAIL}, " +
                "${MahasiswaContract.MahasiswaEntry.COLOUMN_NAMA}, " +
                "${MahasiswaContract.MahasiswaEntry.COLOUMN_NIM}, " +
                "${MahasiswaContract.MahasiswaEntry.COLOUMN_PASSWORD} )" +
                "VALUES ('${mahasiswa.email}','${mahasiswa.nama}','${mahasiswa.nim}','${mahasiswa.password}')"

        db.execSQL(sql)
        db.close()
    }

    fun hapusData(email : String){
        val db = writableDatabase
        val table = MahasiswaContract.MahasiswaEntry.TABLE_NAME
        val emailColoumn = MahasiswaContract.MahasiswaEntry.COLOUMN_EMAIL
        val sql = " DELETE FROM " + table + " WHERE " + emailColoumn + " = '" + email + "'"
        db.execSQL(sql)
        db.close()
    }

    fun updateData(mahasiswa: Mahasiswa){
        val db = writableDatabase

        val sql = " UPDATE ${MahasiswaContract.MahasiswaEntry.TABLE_NAME} SET " +
                "${MahasiswaContract.MahasiswaEntry.COLOUMN_NIM} = '${mahasiswa.nim}', " +
                "${MahasiswaContract.MahasiswaEntry.COLOUMN_NAMA} = '${mahasiswa.nama}', " +
                "${MahasiswaContract.MahasiswaEntry.COLOUMN_EMAIL} = '${mahasiswa.email}', " +
                "${MahasiswaContract.MahasiswaEntry.COLOUMN_PASSWORD} = '${mahasiswa.password}' " +
                " WHERE ${MahasiswaContract.MahasiswaEntry.COLOUMN_EMAIL} = '${mahasiswa.email}' "
        db.execSQL(sql)
        db.close()
    }
}
package com.example.modul67

import android.provider.BaseColumns

object MahasiswaContract {
    class MahasiswaEntry : BaseColumns {
        companion object {
            const val TABLE_NAME = "mahasiswa"
            const val COLOUMN_EMAIL = "email"
            const val COLOUMN_NAMA = "nama"
            const val COLOUMN_NIM = "nim"
            const val COLOUMN_PASSWORD = "password"
        }
    }
}
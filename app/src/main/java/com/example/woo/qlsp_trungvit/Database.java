package com.example.woo.qlsp_trungvit;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dbQuanLyTrung.sqlite";
    private static final int DATABASE_VERSION = 1;
//
//    private static final String TABLE_KHACHHANG = "KhachHang";
//    private static final String KH_MA = "ma";
//    private static final String KH_TEN = "ten";
//    private static final String KH_DIACHI = "diachi";
//    private static final String KH_SDT = "sdt";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Truy vấn không trả về kq: CREATE, INSERT, UPDATE, DELETE, ...
    public void QueryData(String sql){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
    }

    //Truy vấn có trả về kq: SELECT
    public Cursor GetData(String sql){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(sql, null);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
//        String table_KhachHang = String.format("CREATE TABLE IF NOT EXISTS %s(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s VARCHAR(30) NOT NULL, %s VARCHAR(11), %s VARCHAR(50)", TABLE_KHACHHANG, KH_MA, KH_TEN, KH_SDT, KH_DIACHI);
//        db.execSQL(table_KhachHang);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
//        String drop_KhachHang_table = String.format("DROP TABLE IF EXISTS %s", TABLE_KHACHHANG);
//        db.execSQL(drop_KhachHang_table);
//
//        onCreate(db);
    }
}

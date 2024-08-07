package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import database.DbHepler;
import models.SanPham;

public class SanPhamDao {
    private DbHepler dbHepler;
    public SanPhamDao(Context context)
    {
        dbHepler=new DbHepler(context);
    }

    //lay loai san pham

    public ArrayList<SanPham> getDSloaiSach()
    {
        ArrayList<SanPham> list= new ArrayList<>();
        SQLiteDatabase sqLiteDatabase= dbHepler.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("SELECT * FROM SANPHAM",null);
        if(cursor.getCount()>0)
        {
            cursor.moveToFirst();
            do {
                list.add(new SanPham(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3)));
            }while (cursor.moveToNext());
        }
        return  list;
    }
    public boolean themSanPham(String tenSanPham,String loaiSanPham,String giaSanPham)
    {
        SQLiteDatabase sqLiteDatabase=dbHepler.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("ten",tenSanPham);
        contentValues.put("tenloai",loaiSanPham);
        contentValues.put("gia",giaSanPham);

        long check=sqLiteDatabase.insert("SANPHAM",null,contentValues);
        return check!=-1;
    }

    public ArrayList<SanPham> timKiemSanPham(String tenSanPham) {
        ArrayList<SanPham> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHepler.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM SANPHAM WHERE ten LIKE ?", new String[]{"%" + tenSanPham + "%"});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                list.add(new SanPham(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
            } while (cursor.moveToNext());
        }
        return list;
    }
}

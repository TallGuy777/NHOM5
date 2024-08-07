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

    public  boolean suaSanPham(SanPham sanPham)
    {
        SQLiteDatabase sqLiteDatabase =dbHepler.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ten",sanPham.getTen());
        contentValues.put("tenloai",sanPham.getLoai());
        contentValues.put("gia",sanPham.getGia());

        int check= sqLiteDatabase.update("SANPHAM",contentValues,"masanpham = ?", new String[]{String.valueOf(sanPham.getMaloai())});
        return  check!=0;
    }

    public int xoaSanPham(int masanpham) {
        SQLiteDatabase sqLiteDatabase = dbHepler.getWritableDatabase();

        // kiểm tra sự tồn tại của sản phẩm
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM SANPHAM WHERE masanpham = ?", new String[]{String.valueOf(masanpham)});
        if (cursor.getCount() > 0) {
            // sản phẩm tồn tại, tiến hành xóa
            int check = sqLiteDatabase.delete("SANPHAM", "masanpham = ?", new String[]{String.valueOf(masanpham)});
            cursor.close();
            return check > 0 ? 1 : -1;  // trả về 1 nếu xóa thành công, -1 nếu xóa thất bại
        } else {
            cursor.close();
            return 0;  // trả về 0 nếu sản phẩm không tồn tại
        }
    }

}

package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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

    public ArrayList<SanPham> getDSloaiSach() {
        ArrayList<SanPham> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHepler.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = sqLiteDatabase.rawQuery("SELECT * FROM SANPHAM", null);

            // Kiểm tra số lượng cột và nếu có dữ liệu
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();

                // Lấy chỉ số cột
                int indexMaloai = cursor.getColumnIndex("masanpham");
                int indexTen = cursor.getColumnIndex("ten");
                int indexLoai = cursor.getColumnIndex("tenloai");
                int indexGia = cursor.getColumnIndex("gia");
                int indexHinhAnh = cursor.getColumnIndex("hinhanh");

                // Kiểm tra chỉ số cột hợp lệ
                if (indexMaloai != -1 && indexTen != -1 && indexLoai != -1 && indexGia != -1 && indexHinhAnh != -1) {
                    do {
                        // Lấy dữ liệu từ cursor dựa trên chỉ số cột
                        String hinhAnh = cursor.getString(indexHinhAnh);

                        list.add(new SanPham(
                                cursor.getInt(indexMaloai),   // maloai
                                cursor.getString(indexTen),    // ten
                                cursor.getString(indexLoai),   // loai
                                cursor.getString(indexGia),    // gia
                                hinhAnh                       // hinhAnh
                        ));
                    } while (cursor.moveToNext());
                } else {
                    // Log lỗi hoặc xử lý khi chỉ số cột không hợp lệ
                    Log.e("SanPhamDao", "Invalid column index");
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return list;
    }



    public ArrayList<SanPham> timKiemSanPham(String tenSanPham) {
        ArrayList<SanPham> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHepler.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM SANPHAM WHERE ten LIKE ?", new String[]{"%" + tenSanPham + "%"});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                // Thay đổi đoạn mã để truyền tên hình ảnh vào constructor
                list.add(new SanPham(
                        cursor.getInt(0),           // maloai
                        cursor.getString(1),        // ten
                        cursor.getString(2),        // loai
                        cursor.getString(3),        // gia
                        cursor.getString(4)         // hinhAnh
                ));

            } while (cursor.moveToNext());
        }
        return list;
    }


    public boolean themSanPham(String tenSanPham, String loaiSanPham, String giaSanPham, String hinhAnh) {
        SQLiteDatabase sqLiteDatabase = dbHepler.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ten", tenSanPham);
        contentValues.put("tenloai", loaiSanPham);
        contentValues.put("gia", giaSanPham);
        contentValues.put("hinhanh", hinhAnh); // Thêm tên hình ảnh

        long check = sqLiteDatabase.insert("SANPHAM", null, contentValues);
        return check != -1;
    }

    public boolean suaSanPham(SanPham sanPham) {
        SQLiteDatabase sqLiteDatabase = dbHepler.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ten", sanPham.getTen());
        contentValues.put("tenloai", sanPham.getLoai());
        contentValues.put("gia", sanPham.getGia());
        contentValues.put("hinhanh", sanPham.getHinhAnh()); // Cập nhật tên hình ảnh

        int check = sqLiteDatabase.update("SANPHAM", contentValues, "masanpham = ?", new String[]{String.valueOf(sanPham.getMaloai())});
        return check != 0;
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

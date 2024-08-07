package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import database.DbHepler;
import models.NguoiDung;

public class NguoiDungDAO {
    private DbHepler dbHepler;

    public NguoiDungDAO(Context context) {
        dbHepler = new DbHepler(context);
    }

    // Kiểm tra thông tin đăng nhập
    public boolean KiemTraDangNhap(String email, String password) {
        SQLiteDatabase sqLiteDatabase = dbHepler.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM NGUOIDUNG WHERE email = ? AND matkhau = ?", new String[]{email, password});
        boolean result = cursor.getCount() > 0;
        cursor.close(); // Đóng cursor sau khi sử dụng
        return result;
    }

    public boolean DangKyTaiKhoan(NguoiDung nguoiDung) {
        SQLiteDatabase sqLiteDatabase = dbHepler.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ten", nguoiDung.getTen()); // Thay tennguoidung thành ten
        contentValues.put("phone", nguoiDung.getPhone()); // Thay sodienthoai thành phone
        contentValues.put("email", nguoiDung.getEmail());
        contentValues.put("matkhau", nguoiDung.getMatkhau()); // Thay matkhau thành matkhau
        contentValues.put("diachi", nguoiDung.getDiachi());
        contentValues.put("role", nguoiDung.getRole()); // Thay 1 thành nguoiDung.getRole() nếu cần

        long check = sqLiteDatabase.insert("NGUOIDUNG", null, contentValues);
        return check != -1;
    }

    public boolean KiemTraEmailDaTonTai(String email) {
        SQLiteDatabase sqLiteDatabase = dbHepler.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM NGUOIDUNG WHERE email = ?", new String[]{email});
        boolean result = cursor.getCount() > 0;
        cursor.close(); // Đóng cursor sau khi sử dụng
        return result;
    }
}

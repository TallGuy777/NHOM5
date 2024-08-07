package dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import database.DbHepler;
import models.NguoiDung;

public class NguoiDungDAO {
    private DbHepler dbHepler;
    private SharedPreferences sharedPreferences;

    public NguoiDungDAO(Context context) {
        dbHepler = new DbHepler(context);
        sharedPreferences = context.getSharedPreferences("dataUser", Context.MODE_PRIVATE);
    }

    // Kiểm tra thông tin đăng nhập
    public boolean KiemTraDangNhap(String email, String password) {
        SQLiteDatabase sqLiteDatabase = dbHepler.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM NGUOIDUNG WHERE email = ? AND matkhau = ?", new String[]{email, password});

        boolean isLoggedIn = false;
        if (cursor.moveToFirst()) {
            // Đảm bảo con trỏ đang ở dòng đầu tiên trước khi truy cập dữ liệu
            @SuppressLint("Range") int role = cursor.getInt(cursor.getColumnIndex("role"));
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("role", role);
            editor.apply();
            isLoggedIn = true;
        }

        cursor.close(); // Đảm bảo đóng cursor sau khi sử dụng
        return isLoggedIn;
    }

    public boolean DangKyTaiKhoan(NguoiDung nguoiDung) {
        SQLiteDatabase sqLiteDatabase = dbHepler.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ten", nguoiDung.getTen());
        contentValues.put("phone", nguoiDung.getPhone());
        contentValues.put("email", nguoiDung.getEmail());
        contentValues.put("matkhau", nguoiDung.getMatkhau());
        contentValues.put("diachi", nguoiDung.getDiachi());
        contentValues.put("role", nguoiDung.getRole());

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

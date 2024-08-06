package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHepler extends SQLiteOpenHelper {
    public DbHepler(Context context)
    {
        super((context),"QUANLY",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng SANPHAM
        String tSanPham = "CREATE TABLE SANPHAM(" +
                "masanpham INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ten TEXT, " +
                "tenloai TEXT," +
                "gia TEXT)";
        db.execSQL(tSanPham);
        // Chèn dữ liệu mẫu vào SANPHAM
        db.execSQL("INSERT INTO SANPHAM(ten, tenloai, gia) VALUES('Chuối', 'Trái cây', '10.000')");
        db.execSQL("INSERT INTO SANPHAM(ten, tenloai, gia) VALUES('Rau xà lách', 'Rau củ', '5.000')");

        // Tạo bảng NGUOIDUNG
        String tNguoiDung = "CREATE TABLE NGUOIDUNG(" +
                "manguoidung INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ten TEXT, " +
                "email TEXT, " +
                "phone TEXT, " +
                "diachi TEXT, " +
                "matkhau TEXT, " +
                "role INTEGER)";
        db.execSQL(tNguoiDung);
        db.execSQL("INSERT INTO NGUOIDUNG VALUES(1, 'Thịnh', 'thinh1@gmail.com', '12124211515', 'Hốc Môn TP.HCM', '1', 1), " +
                "(404, 'ADMIN', 'admin', '12124211515', 'Hốc Môn TP.HCM', '123', 2)");

        // Tạo bảng HOADON
        String tHoaDon = "CREATE TABLE HOADON(" +
                "mahoadon INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ngaymua TEXT, " +
                "tongtien INTEGER, " +
                "manguoidung INTEGER, " +
                "FOREIGN KEY(manguoidung) REFERENCES NGUOIDUNG(manguoidung))";
        db.execSQL(tHoaDon);

        // Tạo bảng HOADONCHITIET
        String tHoaDonChiTiet = "CREATE TABLE HOADONCHITIET(" +
                "mahoadon INTEGER REFERENCES HOADON(mahoadon), " +
                "masanpham INTEGER REFERENCES SANPHAM(masanpham), " +
                "tensanpham TEXT, " +
                "soluong INTEGER, " +
                "tongtien INTEGER)";
        db.execSQL(tHoaDonChiTiet);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS SANPHAM");
            db.execSQL("DROP TABLE IF EXISTS NGUOIDUNG");
            db.execSQL("DROP TABLE IF EXISTS HOADON");
            db.execSQL("DROP TABLE IF EXISTS HOADONCHITIET");
            onCreate(db);
        }
    }


}

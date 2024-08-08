package models;

public class SanPham {
    private int maloai;      // ID sản phẩm
    private String ten;      // Tên sản phẩm
    private String loai;     // Loại sản phẩm
    private String gia;      // Giá sản phẩm
    private String hinhAnh;  // Tên hình ảnh (drawable) của sản phẩm

    public SanPham(int maloai, String ten, String loai, String gia, String hinhAnh) {
        this.maloai = maloai;
        this.ten = ten;
        this.loai = loai;
        this.gia = gia;
        this.hinhAnh = hinhAnh;
    }

    public int getMaloai() {
        return maloai;
    }

    public void setMaloai(int maloai) {
        this.maloai = maloai;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
}

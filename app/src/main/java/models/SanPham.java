package models;

public class SanPham {
    private  int maloai;
    private  String ten;
    private  String loai;
    private  String gia;

    public SanPham(int maloai, String ten, String loai,String gia ) {
        this.gia = gia;
        this.loai = loai;
        this.ten = ten;
        this.maloai = maloai;
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
}

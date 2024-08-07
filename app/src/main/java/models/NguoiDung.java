package models;

public class NguoiDung {
    private int manguoidung;
    private String ten;
    private String email;
    private String phone;
    private String diachi;
    private String matkhau;
    private int role;

    public NguoiDung(int manguoidung, String ten, String email, String phone, String diachi, String matkhau, int role) {
        this.manguoidung = manguoidung;
        this.ten = ten;
        this.email = email;
        this.phone = phone;
        this.diachi = diachi;
        this.matkhau = matkhau;
        this.role = role;
    }

    public NguoiDung(String ten, String email, String phone, String diachi, String matkhau, int role) {
        this.ten = ten;
        this.email = email;
        this.phone = phone;
        this.diachi = diachi;
        this.matkhau = matkhau;
        this.role = role;
    }

    public int getManguoidung() {
        return manguoidung;
    }

    public void setManguoidung(int manguoidung) {
        this.manguoidung = manguoidung;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}

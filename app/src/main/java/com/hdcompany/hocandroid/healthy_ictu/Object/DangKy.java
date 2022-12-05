package com.hdcompany.hocandroid.healthy_ictu.Object;

public class DangKy {
    private int id;
    private String tendangnhap;
    private String matkhau;
    private String hoten;
    private String ngaysinh;
    private String diachi;
    private String hinhanh;

    public DangKy() {
    }

    public DangKy(int id, String tendangnhap, String matkhau, String hoten, String ngaysinh, String diachi, String hinhanh) {
        this.id = id;
        this.tendangnhap = tendangnhap;
        this.matkhau = matkhau;
        this.hoten = hoten;
        this.ngaysinh = ngaysinh;
        this.diachi = diachi;
        this.hinhanh = hinhanh;
    }

    public String getTendangnhap() {
        return tendangnhap;
    }

    public void setTendangnhap(String tendangnhap) {
        this.tendangnhap = tendangnhap;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "DangKy{" +
                "id=" + id +
                ", tendangnhap='" + tendangnhap + '\'' +
                ", matkhau='" + matkhau + '\'' +
                ", hoten='" + hoten + '\'' +
                ", ngaysinh='" + ngaysinh + '\'' +
                ", diachi='" + diachi + '\'' +
                ", hinhanh='" + hinhanh + '\'' +
                '}';
    }
}

package com.hdcompany.hocandroid.healthy_ictu.Object;

import java.io.Serializable;

public class DangNhap implements Serializable {
    private int id;
    private String tendangnhap;
    private String hoten;
    private String matkhau;
    private String ngaysinh;
    private String diachi;
    private String image;

    public DangNhap() {
    }

    public DangNhap(int id, String tendangnhap, String hoten, String matkhau, String ngaysinh, String diachi, String image) {
        this.id = id;
        this.tendangnhap = tendangnhap;
        this.hoten = hoten;
        this.matkhau = matkhau;
        this.ngaysinh = ngaysinh;
        this.diachi = diachi;
        this.image = image;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "DangNhap{" +
                "id=" + id +
                ", tendangnhap='" + tendangnhap + '\'' +
                ", matkhau='" + matkhau + '\'' +
                '}';
    }
}

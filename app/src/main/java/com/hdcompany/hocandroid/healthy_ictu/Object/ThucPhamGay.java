package com.hdcompany.hocandroid.healthy_ictu.Object;

public class ThucPhamGay {
    private int id;
    private String tenthucpham;
    private String giaca;
    private String diachi;
    private String hinhanh;

    public ThucPhamGay() {
    }

    public ThucPhamGay(int id, String tenthucpham, String giaca, String diachi, String hinhanh) {
        this.id = id;
        this.tenthucpham = tenthucpham;
        this.giaca = giaca;
        this.diachi = diachi;
        this.hinhanh = hinhanh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenthucpham() {
        return tenthucpham;
    }

    public void setTenthucpham(String tenthucpham) {
        this.tenthucpham = tenthucpham;
    }

    public String getGiaca() {
        return giaca;
    }

    public void setGiaca(String giaca) {
        this.giaca = giaca;
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


    @Override
    public String toString() {
        return "hinhanh = " + hinhanh;
    }
}

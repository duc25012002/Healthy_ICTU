package com.hdcompany.hocandroid.healthy_ictu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hdcompany.hocandroid.healthy_ictu.Activity.HienThiDanhSachDangNhap;
//import com.hdcompany.hocandroid.healthy_ictu.Activity.ListNguoiDungDangKy;
import com.hdcompany.hocandroid.healthy_ictu.Activity.ListThucPhamBeo;
import com.hdcompany.hocandroid.healthy_ictu.Activity.ListThucPhamGay;
import com.hdcompany.hocandroid.healthy_ictu.Activity.XuLyDangKy;
import com.hdcompany.hocandroid.healthy_ictu.Activity.XuLyThucPhamBeo;
import com.hdcompany.hocandroid.healthy_ictu.Activity.XuLyThucPhamGay;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void xuLuVao(View view) {
        Intent intent = new Intent(MainActivity.this, HienThiDanhSachDangNhap.class);
        startActivity(intent);
    }
}
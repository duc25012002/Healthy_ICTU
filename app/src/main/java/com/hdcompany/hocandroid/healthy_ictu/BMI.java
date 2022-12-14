package com.hdcompany.hocandroid.healthy_ictu;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.hdcompany.hocandroid.healthy_ictu.Activity.ChinhSuaNguoiDung;
import com.hdcompany.hocandroid.healthy_ictu.Activity.ListThucPhamBeo;
import com.hdcompany.hocandroid.healthy_ictu.Activity.ListThucPhamGay;
import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class BMI extends AppCompatActivity {
    EditText edtChieuCao, edtCanNang;
    Button btnThucHienTinh;
    double H, W, BMI;
    String chieuCao, canNang;
    Timer timer;
    TimerTask timerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);
        Intent intentt = getIntent();
        addControls();
        addEvents();
    }

    public void addControls() {
        edtChieuCao = (EditText) findViewById(R.id.edtChieuCao);
        edtCanNang = (EditText) findViewById(R.id.edtCanNang);
        btnThucHienTinh = (Button) findViewById(R.id.btnTinhToan);
        chieuCao = edtChieuCao.getText().toString();
        canNang = edtCanNang.getText().toString();

    }

    public void addEvents() {
        Random random = new Random();
        int alpha = 255;
        int red = random.nextInt(256);
        int blue = random.nextInt(256);
        int green = random.nextInt(256);
        int color = Color.argb(alpha, red, green, blue);
        btnThucHienTinh.setTextColor(color);
        btnThucHienTinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chieuCao.isEmpty() && canNang.isEmpty()) {
                    tinhToan();
                } else {
                    tinhToan();
                }
            }
        });

    }

    private void tinhToan() {
        FancyGifDialog.Builder builder = new FancyGifDialog.Builder(this);
        H = Double.parseDouble(edtChieuCao.getText().toString());
        W = Double.parseDouble(edtCanNang.getText().toString());
        BMI = W / Math.pow(H, 2);

        if (BMI < 18.5) {
            builder.setTitle("BMI: " + BMI + "\nB???n ??ang g???y");
        } else if (BMI <= 24.99) {
            builder.setTitle("BMI: " + BMI + "\nB???n ??ang c??n ?????i");
        } else if (BMI <= 29.9) {
            builder.setTitle("BMI: " + BMI + "\nB???n ??ang b??o b???c I");
        } else if (BMI <= 34.9) {
            builder.setTitle("BMI: " + BMI + "\nB???n ??ang b??o b???c II");
        } else {
            builder.setTitle("BMI: " + BMI + "\nB???n ??ang qu?? th???a c??n");
        }

        builder.setMessage("B???n c?? mu???n t??m ki???m s???n ph???m ph?? h???p kh??ng?"); // or pass like R.string.description_from_resources
        builder.setTitleTextColor(R.color.black);
        builder.setDescriptionTextColor(R.color.btn_logut_bg)
                .setNegativeBtnText("Kh??ng") // or pass it like android.R.string.cancel
                .setPositiveBtnBackground(R.color.mau_Hong);
        builder.setPositiveBtnText("Ok") // or pass it like android.R.string.ok
                .setNegativeBtnBackground(R.color.mau_Xam)
                .setGifResource(R.drawable.gif_xuong)   //Pass your Gif here
                .isCancellable(true)
                .OnPositiveClicked(new FancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                        if (BMI < 18.5) {
                            activityGay();
                        }
                        if (BMI >= 25) {
                            activityBeo();
                        } else {
                            Toast.makeText(BMI.this, "Hi???n t???i ch??ng t??i ??ang c???p nh???t th??m th???c ph???m cho ng?????i c??n ?????i", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .OnNegativeClicked(new FancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                        Toast.makeText(BMI.this, "C???m ??n b???n ???? quan t??m", Toast.LENGTH_SHORT).show();
                    }
                });
        builder.build();
    }

    public void activityGay() {
        Intent intent = new Intent(com.hdcompany.hocandroid.healthy_ictu.BMI.this, ListThucPhamGay.class);
        startActivity(intent);
    }

    public void activityBeo() {
        Intent intent = new Intent(com.hdcompany.hocandroid.healthy_ictu.BMI.this, ListThucPhamBeo.class);
        startActivity(intent);
    }

    public void SuaThongTinCaNhan(View view) {

        Intent intent = new Intent(com.hdcompany.hocandroid.healthy_ictu.BMI.this, ChinhSuaNguoiDung.class);
        startActivity(intent);
    }
}

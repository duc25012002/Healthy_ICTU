package com.hdcompany.hocandroid.healthy_ictu.Activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hdcompany.hocandroid.healthy_ictu.R;
import com.hdcompany.hocandroid.healthy_ictu.utils.Utils;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class XuLyDangKy extends AppCompatActivity {
    EditText edthoVaTen, edtDiaChi, edtNgaySinh, edtTaiKhoan, edtMatKhau;
    Button btnThoat, btnXacNhan, btnChonAnh;
    ImageView imgavt;
    Bitmap bitmap;
    public static final String TAG = XuLyDangKy.class.getSimpleName();
    String encodeImageString;
    public static final String URL_INSERT = Utils.URL + "insert.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        addControl();
        addEvents();
    }

    private void addControl() {
        edthoVaTen = (EditText) findViewById(R.id.edthoVaTen);
        edtDiaChi = findViewById(R.id.edtDiaChi);
        edtNgaySinh = (EditText) findViewById(R.id.edtNgaySinh);
        edtTaiKhoan = (EditText) findViewById(R.id.edtTaiKhoan);
        edtMatKhau = (EditText) findViewById(R.id.edtPass);
        btnThoat = (Button) findViewById(R.id.btnThoat);
        btnXacNhan = (Button) findViewById(R.id.btnXacNhan);
        btnChonAnh = (Button) findViewById(R.id.btnChonAnh);
        imgavt = (ImageView) findViewById(R.id.imgAVT);
    }

    private void addEvents() {
        btnChonAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(XuLyDangKy.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent, "Browse Image"), 1);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();
            }
        });

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hoten = edthoVaTen.getText().toString().trim();
                String ngaysinh = edtNgaySinh.getText().toString().trim();
                String diachi = edtDiaChi.getText().toString().trim();
                String taikhoan = edtTaiKhoan.getText().toString().trim();
                String matkhau = edtMatKhau.getText().toString().trim();
                if (taikhoan.isEmpty() || matkhau.isEmpty() || hoten.isEmpty() || ngaysinh.isEmpty() || diachi.isEmpty()) {
                    Toast.makeText(XuLyDangKy.this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    themNguoiDung(URL_INSERT);
                }
            }
        });
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri filepath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filepath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                imgavt.setImageBitmap(bitmap);
                encodeBitmapImage(bitmap);
            } catch (Exception e) {

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void encodeBitmapImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] bytesoftImage = byteArrayOutputStream.toByteArray();
        encodeImageString = android.util.Base64.encodeToString(bytesoftImage, Base64.DEFAULT);
    }


    private void themNguoiDung(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //Đẩy dữ liệu lên
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                FancyToast.makeText(XuLyDangKy.this, "Đăng ký thành công", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                imgavt.setImageResource(R.drawable.ic_baseline_border_bottom_24);
                startActivity(new Intent(XuLyDangKy.this, HienThiDanhSachDangNhap.class));
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                FancyToast.makeText(XuLyDangKy.this, "Lỗi", FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
            }
        })//gõ getParams
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("taikhoan", edtTaiKhoan.getText().toString().trim());
                params.put("matkhau", edtMatKhau.getText().toString().trim());
                params.put("hoten", edthoVaTen.getText().toString().trim());
                params.put("ngaysinh", edtNgaySinh.getText().toString().trim());
                params.put("diachi", edtDiaChi.getText().toString().trim());
                params.put("upload", encodeImageString);
                return params;
            }
        };
        requestQueue.add(stringRequest);

    }


}
package com.hdcompany.hocandroid.healthy_ictu.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class XuLyThucPhamGay extends AppCompatActivity {

    EditText edtTenThucPham, edtGiaCa, edtdiaChiBan;
    ImageView imgATP;
    Button btnChonAnhThucPham, btnThoatDang, btnXacNhanDang;
    Bitmap bitmap;
    String encodeImageString;
    public static final String URL_INSERTXULY = Utils.URL + "thucphamgayinsert.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xu_ly_thuc_pham_gay);
        addControl();
        addEvents();
    }

    private void addControl() {
        edtTenThucPham = (EditText) findViewById(R.id.edtTenThucPham);
        edtGiaCa = (EditText) findViewById(R.id.edtGiaCa);
        edtdiaChiBan = (EditText) findViewById(R.id.edtDiaChiBan);
        imgATP = findViewById(R.id.imgATP);
        btnChonAnhThucPham = findViewById(R.id.btnChonAnhThucPham);
        btnThoatDang = findViewById(R.id.btnThoatDang);
        btnXacNhanDang = findViewById(R.id.btnXacNhanDang);
    }

    private void addEvents() {
        btnChonAnhThucPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(XuLyThucPhamGay.this)
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

        btnXacNhanDang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenthucpham = edtTenThucPham.getText().toString().trim();
                String giaca = edtGiaCa.getText().toString().trim();
                String diachi = edtdiaChiBan.getText().toString().trim();
                if (tenthucpham.isEmpty() || giaca.isEmpty() | diachi.isEmpty()) {
                    Toast.makeText(XuLyThucPhamGay.this, "Error", Toast.LENGTH_SHORT).show();
                } else {
                    themThucPhamGay(URL_INSERTXULY);

                }
            }
        });

        btnThoatDang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 1 && resultCode == RESULT_OK) {
            Uri filepath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filepath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                imgATP.setImageBitmap(bitmap);
                encodeBitmapImage(bitmap);
            }catch (Exception e) {

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




    private void themThucPhamGay(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //Đẩy dữ liệu lên
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(XuLyThucPhamGay.this, "Success", Toast.LENGTH_SHORT).show();
                imgATP.setImageResource(R.drawable.account);
//                startActivity(new Intent(XuLyThucPhamGay.this, HienThiDanhSachDangNhap.class));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(XuLyThucPhamGay.this, "Lỗi", Toast.LENGTH_SHORT).show();
            }
        })//gõ getParams
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();


                params.put("tenthucpham", edtTenThucPham.getText().toString().trim());
                params.put("giaca", edtGiaCa.getText().toString().trim());
                params.put("diachi", edtdiaChiBan.getText().toString().trim());
                params.put("upload", encodeImageString);
                return params;
            }
        };
        requestQueue.add(stringRequest);

    }
}
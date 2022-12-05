package com.hdcompany.hocandroid.healthy_ictu.Activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.bumptech.glide.Glide;
import com.hdcompany.hocandroid.healthy_ictu.BMI;
import com.hdcompany.hocandroid.healthy_ictu.R;
import com.hdcompany.hocandroid.healthy_ictu.retrofit.ApiData;
import com.hdcompany.hocandroid.healthy_ictu.retrofit.RetrofitInstance;
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

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ChinhSuaNguoiDung extends AppCompatActivity{

    EditText edthotenup, edtngaysinhup, edtdiachiup, edtMatKhauUp;
    TextView idNguoiSua;
    Button btnXacNhanUp, btnHuyUp, btnUpAnh;
    ImageView imgavatarUp;
    public static final String TAG = ChinhSuaNguoiDung.class.getSimpleName();
    Bitmap bitmap;
    String s = null;
    String encodeImageStringUp;
    public static final String URL_UPDATE = "https://hoangduc.xyz/updatenguoidung.php";

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiData apiData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.hdcompany.hocandroid.healthy_ictu.R.layout.activity_chinh_sua_nguoi_dung);
        apiData = RetrofitInstance.getInstance(Utils.URL).create(ApiData.class);
        addControls();
        addEvent();
    }

    public void addControls() {
        idNguoiSua = findViewById(R.id.idNguoiSua);
        edthotenup = findViewById(R.id.edthotenup);
        edtngaysinhup = findViewById(R.id.edtngaysinhup);
        edtdiachiup = findViewById(R.id.edtdiachiup);
        edtMatKhauUp = findViewById(R.id.edtMatKhauUp);
        imgavatarUp = findViewById(R.id.imgavatarUp);
        btnXacNhanUp = findViewById(R.id.btnXacNhanUp);
        btnHuyUp = findViewById(R.id.btnHuyUp);
        btnUpAnh = findViewById(R.id.btnUpAnh);
        // cap nhap thong tin len view


        idNguoiSua.setText(Utils.User_current.getId() + "");
        edthotenup.setText(Utils.User_current.getHoten());
        edtngaysinhup.setText(Utils.User_current.getNgaysinh());
        edtdiachiup.setText(Utils.User_current.getDiachi());
        edtMatKhauUp.setText(Utils.User_current.getMatkhau());
        Glide.with(this).load(Utils.URL+"images/"+Utils.User_current.getImage()).into(imgavatarUp);

    }

    private void addEvent() {
        btnUpAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(ChinhSuaNguoiDung.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent, "Browse Image"), 1000);
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

        btnXacNhanUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hoten = edthotenup.getText().toString().trim();
                String ngaysinh = edtngaysinhup.getText().toString().trim();
                String diachi = edtdiachiup.getText().toString().trim();
                String matkhau = edtMatKhauUp.getText().toString().trim();
                s = idNguoiSua.getText().toString().trim();
                if (hoten.isEmpty() || matkhau.isEmpty() || ngaysinh.isEmpty() || diachi.isEmpty()) {
                    FancyToast.makeText(ChinhSuaNguoiDung.this, "Vui lòng nhập đủ thông tin", FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();
                } else {
                   // capNhatNguoiDung(URL_UPDATE);
                    updateInfoRetrofit(hoten,ngaysinh,diachi,matkhau);

                }
            }
        });

        btnHuyUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void updateInfoRetrofit(String hoten, String ngaysinh, String diachi, String matkhau) {
        compositeDisposable.add(apiData.updateInfo(Utils.User_current.getId(),matkhau,hoten,diachi,ngaysinh,encodeImageStringUp )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        messModel -> {
                            if (messModel.isSucces()){
                                FancyToast.makeText(this, "Chỉnh sửa thành công", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                            }else {
                                FancyToast.makeText(this, "Chỉnh sửa thất bại", FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                            }
                        },
                        throwable -> {
                            Log.d("loi", throwable.getMessage());
                        }
                ));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1000 && resultCode == RESULT_OK) {
            Uri filepath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filepath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                imgavatarUp.setImageBitmap(bitmap);
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
        encodeImageStringUp = android.util.Base64.encodeToString(bytesoftImage, Base64.DEFAULT);
    }


//    private void capNhatNguoiDung(String url) {
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        //Đẩy dữ liệu lên
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        if (response.trim().equals("success")) {
//
//                            Toast.makeText(ChinhSuaNguoiDung.this, "Success", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(ChinhSuaNguoiDung.this, BMI.class));
//
//                        } else {
//                            Toast.makeText(ChinhSuaNguoiDung.this, "Cannot Update", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(ChinhSuaNguoiDung.this, "Lỗi, vui lòng thử lại", Toast.LENGTH_SHORT).show();
//                    }
//                }
//        )//đẩy dữ liệu lên gio no khong chay ham duopi
//        {
//            @Nullable
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("taikhoan", idNguoiSua.getText().toString().trim());
//                params.put("matkhau", edtMatKhauUp.getText().toString().trim());
//                params.put("hoten", edthotenup.getText().toString().trim());
//                params.put("ngaysinh", edtngaysinhup.getText().toString().trim());
//                params.put("diachi", edtdiachiup.getText().toString().trim());
//                params.put("image", encodeImageStringUp);
//                return params;
//            }
//        };
//        requestQueue.add(stringRequest);
//    }

}

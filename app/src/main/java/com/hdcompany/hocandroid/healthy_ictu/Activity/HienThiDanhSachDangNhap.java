package com.hdcompany.hocandroid.healthy_ictu.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.hdcompany.hocandroid.healthy_ictu.BMI;
import com.hdcompany.hocandroid.healthy_ictu.Object.DangNhap;
import com.hdcompany.hocandroid.healthy_ictu.R;
import com.hdcompany.hocandroid.healthy_ictu.retrofit.ApiData;
import com.hdcompany.hocandroid.healthy_ictu.retrofit.RetrofitInstance;
import com.hdcompany.hocandroid.healthy_ictu.utils.Utils;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class HienThiDanhSachDangNhap extends AppCompatActivity {
    CallbackManager callbackManager;
    LoginButton fbLoginButton;
    CheckBox chkLuu;
    private static final String TAG = HienThiDanhSachDangNhap.class.getSimpleName();
    EditText edtUserName, edtPassWord;
    Button btnLogin;
    private ProgressDialog pDialog;
    public static final String URL_LOGIN = Utils.URL + "login1.php";
    public static final String KEY_USERNAME = "taikhoan";
    public static final String KEY_PASSWORD = "matkhau";


    ApiData apiData;
    CompositeDisposable compositeDisposable = new CompositeDisposable();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hien_thi_danh_sach_dang_nhap);
        //khoi tao api ket noi data
        apiData  = RetrofitInstance.getInstance(Utils.URL).create(ApiData.class);

        addControls();
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        fbLoginButton = (LoginButton) findViewById(R.id.login_button);
        //https://developers.facebook.com/docs/facebook-login/permissions#reference
        fbLoginButton.setReadPermissions("email");
        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "======Facebook login success======");
                Log.d(TAG, "Facebook Access Token: " + loginResult.getAccessToken().getToken());
                Toast.makeText(HienThiDanhSachDangNhap.this, "Login Facebook success.", Toast.LENGTH_SHORT).show();

                getFbInfo();
            }

            @Override
            public void onCancel() {
                Toast.makeText(HienThiDanhSachDangNhap.this, "Login Facebook cancelled.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Log.e(TAG, "======Facebook login error======");
                Log.e(TAG, "Error: " + error.toString());
                Toast.makeText(HienThiDanhSachDangNhap.this, "Login Facebook error.", Toast.LENGTH_SHORT).show();
            }
        });
        addEvents();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void getFbInfo() {
        if (AccessToken.getCurrentAccessToken() != null) {
            GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(final JSONObject me, GraphResponse response) {
                            if (me != null) {
                                Log.i("Login: ", me.optString("name"));
                                Log.i("ID: ", me.optString("id"));
                                Toast.makeText(HienThiDanhSachDangNhap.this, "Name: " + me.optString("name"), Toast.LENGTH_SHORT).show();
                                Toast.makeText(HienThiDanhSachDangNhap.this, "ID: " + me.optString("id"), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,link");
            request.setParameters(parameters);
            request.executeAsync();
        }
    }

    public void addControls() {
        edtUserName = findViewById(R.id.edtTenDangNhap);
        edtPassWord = findViewById(R.id.edtMatKhau);
        btnLogin = findViewById(R.id.btnDangNhap);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Đang đăng nhập...");
        pDialog.setCanceledOnTouchOutside(false);
    }

    private void addEvents() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get value input
                String username = edtUserName.getText().toString().trim();
                String password = edtPassWord.getText().toString().trim();
                // Call method
               // loginAccount(username, password);
                dangnhapRetrofit(username, password);

            }
        });
    }

    private void dangnhapRetrofit(String username, String password) {
        pDialog.show();
        //retrofit kho hieu nhi a
        compositeDisposable.add(apiData.dangnhap(username,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        userModel -> {
                            if (userModel.isSucces()){
                                FancyToast.makeText(this, "Đăng nhập thành công", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();

                                Utils.User_current = userModel.getResult().get(0);
                                Intent intent = new Intent(HienThiDanhSachDangNhap.this, BMI.class);
                                startActivity(intent);
                                pDialog.dismiss();
                            }else {
                                FancyToast.makeText(this, "Sai tên đăng nhập hoặc mật khẩu", FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();
                                pDialog.dismiss();

                            }
                            },
                        throwable -> {
                            Log.d("loi", throwable.getMessage());
                            pDialog.dismiss();
                        }
                ));

    }

//    public void loginAccount(final String username, final String password) {
//
//        if (checkEditText(edtUserName) && checkEditText(edtPassWord)) {
//            pDialog.show();
//            StringRequest requestLogin = new StringRequest(Request.Method.POST, URL_LOGIN,
//                    new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            Log.d(TAG, response);
//                            String message = "";
//                            try {
//                                JSONObject jsonObject = new JSONObject(response);
//                                if (jsonObject.getInt("success") == 1) {
//                                    DangNhap dangnhap = new DangNhap();
//                                    dangnhap.setTendangnhap(jsonObject.getString("taikhoan"));
//                                    dangnhap.setMatkhau(jsonObject.getString("matkhau"));
//                                    dangnhap.setHoten(jsonObject.getString("hoten"));
//                                    dangnhap.setNgaysinh(jsonObject.getString("ngaysinh"));
//                                    dangnhap.setDiachi(jsonObject.getString("diachi"));
//                                    dangnhap.setId(jsonObject.getInt("id"));
//                                    message = jsonObject.getString("message");
//                                    Utils.dangNhap = dangnhap;
//
//                                    Intent intent = new Intent(HienThiDanhSachDangNhap.this, BMI.class);
//                                    startActivity(intent);
//                                } else {
//                                    message = jsonObject.getString("message");
//                                    Toast.makeText(HienThiDanhSachDangNhap.this, message, Toast.LENGTH_LONG).show();
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                            pDialog.dismiss();
//                        }
//                    },
//                    new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            VolleyLog.d(TAG, "Error: " + error.getMessage());
//                            pDialog.dismiss();
//                            Toast.makeText(HienThiDanhSachDangNhap.this, "no", Toast.LENGTH_SHORT).show();
//                        }
//                    }) {
//                @Override
//                protected Map<String, String> getParams() {
//                    Map<String, String> params = new HashMap<>();
//                    params.put(KEY_USERNAME, username);
//                    params.put(KEY_PASSWORD, password);
//                    return params;
//                }
//            };
//            RequestQueue queue = Volley.newRequestQueue(this);
//            queue.add(requestLogin);
//        }
//    }
    private boolean checkEditText(EditText editText) {
        if (editText.getText().toString().trim().length() > 0)
            return true;
        else {
            editText.setError("Vui lòng nhập dữ liệu!");
        }
        return false;
    }

    public void xuLyDangKy(View view) {
        Intent intent = new Intent(HienThiDanhSachDangNhap.this, XuLyDangKy.class);
        startActivity(intent);
    }
}

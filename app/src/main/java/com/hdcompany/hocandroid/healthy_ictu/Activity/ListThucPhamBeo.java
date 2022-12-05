package com.hdcompany.hocandroid.healthy_ictu.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.hdcompany.hocandroid.healthy_ictu.Object.ThucPhamBeo;
import com.hdcompany.hocandroid.healthy_ictu.Object.ThucPhamGay;
import com.hdcompany.hocandroid.healthy_ictu.R;
import com.hdcompany.hocandroid.healthy_ictu.ThucPhamBeoAdapter;
import com.hdcompany.hocandroid.healthy_ictu.ThucPhamGayAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListThucPhamBeo extends AppCompatActivity {
    String urlGetData = "http://hoangduc.xyz/getdatathucphambeo.php";
    ListView lvThucPhamBeo;
    ArrayList<ThucPhamBeo> arrayThucPhamBeo;
    ThucPhamBeoAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_thuc_pham_beo);

        lvThucPhamBeo = (ListView) findViewById(R.id.lvThucPhamBeo);
        arrayThucPhamBeo = new ArrayList<>();
        adapter = new ThucPhamBeoAdapter(this, R.layout.mau_thuc_pham_beo, arrayThucPhamBeo);
        lvThucPhamBeo.setAdapter(adapter);
        GetData(urlGetData);
    }
    private void GetData(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        arrayThucPhamBeo.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);

                                arrayThucPhamBeo.add(new ThucPhamBeo(
                                        //khớp với đặt dâtbase
                                        object.getInt("id"),
                                        object.getString("tenthucpham"),
                                        object.getString("giaca"),
                                        object.getString("diachi"),
                                        object.getString("hinhanh")
                                ));
                            } catch (Exception e) {
                                Log.e("Lỗi", e.toString());
                            }
                        }
                        adapter.notifyDataSetChanged();//cập nhật lại mảng

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ListThucPhamBeo.this, "Lỗi", Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue.add(jsonArrayRequest);
    }
}
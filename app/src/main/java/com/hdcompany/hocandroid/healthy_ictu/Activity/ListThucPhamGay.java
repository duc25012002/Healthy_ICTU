package com.hdcompany.hocandroid.healthy_ictu.Activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hdcompany.hocandroid.healthy_ictu.Object.ThucPhamGay;
import com.hdcompany.hocandroid.healthy_ictu.R;
import com.hdcompany.hocandroid.healthy_ictu.ThucPhamGayAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListThucPhamGay extends AppCompatActivity {
    String urlGetData = "http://hoangduc.xyz/getdatathucphamgay.php";
    ListView lvThucPhamGay;
    ArrayList<ThucPhamGay> arrayThucPhamGay;
    ThucPhamGayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_thuc_pham_gay);
        lvThucPhamGay = (ListView) findViewById(R.id.lvThucPhamGay);
        arrayThucPhamGay = new ArrayList<>();
        adapter = new ThucPhamGayAdapter(this, R.layout.mau_thuc_pham_gay, arrayThucPhamGay);

        lvThucPhamGay.setAdapter(adapter);
        GetData(urlGetData);
    }
    private void GetData(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        arrayThucPhamGay.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);

                                arrayThucPhamGay.add(new ThucPhamGay(
                                        //kh???p v???i ?????t d??tbase
                                        object.getInt("id"),
                                        object.getString("tenthucpham"),
                                        object.getString("giaca"),
                                        object.getString("diachi"),
                                        object.getString("hinhanh")
                                ));
                            } catch (Exception e) {
                                Log.e("L???i", e.toString());
                            }
                        }
                        adapter.notifyDataSetChanged();//c???p nh???t l???i m???ng

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ListThucPhamGay.this, "L???i", Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue.add(jsonArrayRequest);
    }

}
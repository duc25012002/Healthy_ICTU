package com.hdcompany.hocandroid.healthy_ictu;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import java.util.List;

import com.bumptech.glide.Glide;
import com.hdcompany.hocandroid.healthy_ictu.Activity.ListThucPhamGay;
import com.hdcompany.hocandroid.healthy_ictu.Object.ThucPhamGay;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


public class ThucPhamGayAdapter extends BaseAdapter {

    private ListThucPhamGay context;
    private int layout;
    private List<ThucPhamGay> thucPhamGayList;

    public ThucPhamGayAdapter(ListThucPhamGay context, int layout, List<ThucPhamGay> thucPhamGayList) {
        this.context = context;
        this.layout = layout;
        this.thucPhamGayList = thucPhamGayList;
    }

    public ThucPhamGayAdapter() {
    }

    @Override
    public int getCount() {
        return thucPhamGayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {

        TextView txtTenSanPhamGay, txtGiaSanPhamGay, txtViTriSanPhamGay;
        ImageView imgAVTSanPhamGay;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);

            holder.txtTenSanPhamGay = (TextView) convertView.findViewById(R.id.txtTenSanPhamGay);
            holder.txtGiaSanPhamGay = (TextView) convertView.findViewById(R.id.txtGiaSanPhamGay);
            holder.txtViTriSanPhamGay = (TextView) convertView.findViewById(R.id.txtViTriSanPhamGay);
            holder.imgAVTSanPhamGay = (ImageView) convertView.findViewById(R.id.imgAVTSanPhamGay);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ThucPhamGay thucphamgay = thucPhamGayList.get(position);

        holder.txtTenSanPhamGay.setText(thucphamgay.getTenthucpham());
        holder.txtGiaSanPhamGay.setText(thucphamgay.getGiaca());
        holder.txtViTriSanPhamGay.setText(thucphamgay.getDiachi());

        String path = "http://hoangduc.xyz/images/"+thucphamgay.getHinhanh();
        Glide.with(context).load(path).into(holder.imgAVTSanPhamGay);

        return convertView;
    }
}

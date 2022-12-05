package com.hdcompany.hocandroid.healthy_ictu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hdcompany.hocandroid.healthy_ictu.Activity.ListThucPhamBeo;
import com.hdcompany.hocandroid.healthy_ictu.Activity.ListThucPhamGay;
import com.hdcompany.hocandroid.healthy_ictu.Object.ThucPhamBeo;
import com.hdcompany.hocandroid.healthy_ictu.Object.ThucPhamGay;

import java.util.List;

public class ThucPhamBeoAdapter extends BaseAdapter {
    private ListThucPhamBeo context;
    private int layout;
    private List<ThucPhamBeo> thucPhamBeoList;

    public ThucPhamBeoAdapter(ListThucPhamBeo context, int layout, List<ThucPhamBeo> thucPhamBeoList) {
        this.context = context;
        this.layout = layout;
        this.thucPhamBeoList = thucPhamBeoList;
    }

    public ThucPhamBeoAdapter() {
    }

    @Override
    public int getCount() {
        return thucPhamBeoList.size();
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

        TextView txtTenSanPhamBeo, txtGiaSanPhamBeo, txtViTriSanPhamBeo;
        ImageView imgAVTSanPhamBeo;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ThucPhamBeoAdapter.ViewHolder holder;
        if (convertView == null) {
            holder = new ThucPhamBeoAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);

            holder.txtTenSanPhamBeo = (TextView) convertView.findViewById(R.id.txtTenSanPhamBeo);
            holder.txtGiaSanPhamBeo = (TextView) convertView.findViewById(R.id.txtGiaSanPhamBeo);
            holder.txtViTriSanPhamBeo = (TextView) convertView.findViewById(R.id.txtViTriSanPhamBeo);
            holder.imgAVTSanPhamBeo = (ImageView) convertView.findViewById(R.id.imgAVTSanPhamBeo);
            convertView.setTag(holder);

        } else {
            holder = (ThucPhamBeoAdapter.ViewHolder) convertView.getTag();
        }
        ThucPhamBeo thucphambeo = thucPhamBeoList.get(position);

        holder.txtTenSanPhamBeo.setText(thucphambeo.getTenthucpham());
        holder.txtGiaSanPhamBeo.setText(thucphambeo.getGiaca());
        holder.txtViTriSanPhamBeo.setText(thucphambeo.getDiachi());

        String path = "http://hoangduc.xyz/images/"+thucphambeo.getHinhanh();
        Glide.with(context).load(path).into(holder.imgAVTSanPhamBeo);

        return convertView;
    }
}

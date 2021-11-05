package com.example.btlandroidnc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class QuanLyMatHangAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<QuanLyMatHangClass> listMatHang;
    public QuanLyMatHangAdapter(Context c, ArrayList<QuanLyMatHangClass> listMatHangIn)
    {
        context =c;
        this.listMatHang = listMatHangIn;
    }
    @Override
    public int getCount() {
        return listMatHang.size();
    }

    @Override
    public Object getItem(int position) {
        return listMatHang.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater == null)
        {
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView == null)
        {
            convertView = inflater.inflate(R.layout.row_item_mathang,null);
        }
        ImageView imgClass = convertView.findViewById(R.id.icon_mathang);
        TextView tenMH = convertView.findViewById(R.id.txtTenMH);
        TextView thongtin = convertView.findViewById(R.id.txtthongtin);
        imgClass.setImageResource(R.drawable.mathang);
        tenMH.setText(listMatHang.get(position).getTenMH());
        thongtin.setText("Đơn Giá: "+listMatHang.get(position).getDonGia().toString()+"- Số lượng: "+listMatHang.get(position).getSoLuong().toString());
        return convertView;
    }
}

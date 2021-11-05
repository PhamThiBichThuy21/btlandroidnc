package com.example.btlandroidnc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class QuanLyNCCAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<QuanLyNCCClass> lstNCC;
    public QuanLyNCCAdapter(Context c, List<QuanLyNCCClass> listNhaCungCap)
    {
        context =c;
        this.lstNCC = listNhaCungCap;
    }
    @Override
    public int getCount() {
        return lstNCC.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater == null)
        {
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView == null)
        {
            convertView = inflater.inflate(R.layout.row_item_ncc,null);
        }
        ImageView imgNCC = convertView.findViewById(R.id.icon_ncc);
        TextView tenNCC = convertView.findViewById(R.id.txtTenNCC);
        TextView DiaChiNCC = convertView.findViewById(R.id.txtDiaChi);
        TextView Sdt = convertView.findViewById(R.id.txtSdt);
        imgNCC.setImageResource(R.drawable.list);
        tenNCC.setText(lstNCC.get(position).getTenNCC());
        DiaChiNCC.setText(lstNCC.get(position).getDiaChi());
        Sdt.setText(lstNCC.get(position).getSdt());
        return convertView;
    }
}

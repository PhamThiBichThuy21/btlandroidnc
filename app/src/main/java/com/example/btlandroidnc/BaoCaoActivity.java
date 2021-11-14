package com.example.btlandroidnc;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BaoCaoActivity extends AppCompatActivity {
    public ArrayList<QuanLyMatHangClass> lstMatHang;
    private QuanLyMatHangAdapter adapter;
    private DatabaseReference dataFireBase;
    PieChart pieChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bao_cao);

        ActionBar actionBar = getSupportActionBar();
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        pieChart = (PieChart)findViewById(R.id.piechart);
        lstMatHang = new ArrayList<QuanLyMatHangClass>();
        adapter = new QuanLyMatHangAdapter(BaoCaoActivity.this, lstMatHang);
        dataFireBase = FirebaseDatabase.getInstance().getReference();
        ArrayList<PieEntry> visitors = new ArrayList<>();
        dataFireBase.child("MatHang").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lstMatHang.clear();
                for(DataSnapshot item:dataSnapshot.getChildren()) {
                    QuanLyMatHangClass qll = item.getValue(QuanLyMatHangClass.class);
                    lstMatHang.add(qll);
                }
                adapter.notifyDataSetChanged();
                for(int i = 0; lstMatHang.size() > i; i++)
                {
                    visitors.add(new PieEntry(lstMatHang.get(i).SoLuong,lstMatHang.get(i).TenMH));
                }
                PieDataSet pieDataSet = new PieDataSet(visitors,"Chú thích");
                pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                pieDataSet.setValueTextColor(Color.BLACK);
                pieDataSet.setValueTextSize(14f);

                PieData pieData = new PieData(pieDataSet);

                pieChart.setData(pieData);
                pieChart.getDescription().setEnabled(false);
                pieChart.setCenterText("Số lượng mặt hàng");
                pieChart.animate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home: onBackPressed(); return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
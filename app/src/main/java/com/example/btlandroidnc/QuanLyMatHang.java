package com.example.btlandroidnc;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class QuanLyMatHang extends AppCompatActivity {
    private ArrayList<QuanLyMatHangClass> lstmathang;
    private ArrayList<QuanLyMatHangClass> lstmathangTK;
    private DatabaseReference datamathang;
    private QuanLyMatHangAdapter adapter;
    private QuanLyMatHangClass QLMatHang;
    private SwipeMenuListView listViewMatHang;
    private ArrayAdapter<String> arrayAdapterNcc;
    private ArrayList<String>spinnerDatalist;
    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;
    String x = null;
    String TenNCC = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_mat_hang);

        ActionBar actionBar = getSupportActionBar();
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        listViewMatHang = (SwipeMenuListView) findViewById(R.id.danhsachmathang);
        lstmathang = new ArrayList<QuanLyMatHangClass>();
        adapter = new QuanLyMatHangAdapter(QuanLyMatHang.this, lstmathang);
        listViewMatHang.setAdapter(adapter);
        datamathang = FirebaseDatabase.getInstance().getReference();
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setIcon(R.drawable.ic_baseline_create_24);
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                openItem.setWidth(120);

                // set item title

                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF0,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(120);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_baseline_delete_24);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        listViewMatHang.setMenuCreator(creator);
        listViewMatHang.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        showDialogEditMH(lstmathang.get(position));
                        break;
                    case 1:
                        deleteMH(lstmathang.get(position));
                        Toast.makeText(QuanLyMatHang.this, "Xóa", Toast.LENGTH_SHORT).show();
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
        datamathang.child("MatHang").orderByChild("tenMH").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lstmathang.clear();
                for (DataSnapshot unit : dataSnapshot.getChildren()){
                    QuanLyMatHangClass QLSV = unit.getValue(QuanLyMatHangClass.class);
                    lstmathang.add(QLSV);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(QuanLyMatHang.this, "Đã xảy ra lỗi vui lòng thử lại", Toast.LENGTH_SHORT).show();
            }
        });
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.add_MH);
        floatingActionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(QuanLyMatHang.this, AddMatHang.class);
                startActivity(intent);
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
    // Tìm kiếm mặt hàng
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_app, menu);
        SearchView search = (SearchView) menu.findItem(R.id.menuSearch).getActionView();
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //Log.d("TAG", "showInfoMH: "+lstmathang);
                for(int i = 0; lstmathang.size() > i; i++)
                {
                    if(lstmathang.get(i).TenMH.contains(s))
                    {
                        QuanLyMatHangClass timmh = new QuanLyMatHangClass (lstmathang.get(i).MaMH,lstmathang.get(i).TenMH,
                                lstmathang.get(i).Dvt,lstmathang.get(i).XuatXu,lstmathang.get(i).MoTa,lstmathang.get(i).TenNCC,
                                lstmathang.get(i).DonGia, lstmathang.get(i).SoLuong);
                        lstmathangTK = new ArrayList<QuanLyMatHangClass>();
                        lstmathangTK.add(timmh);
                        adapter = new QuanLyMatHangAdapter(QuanLyMatHang.this, lstmathangTK);
                        listViewMatHang.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }

        });
        return super.onCreateOptionsMenu(menu);
    }
    public void deleteMH(final QuanLyMatHangClass lstMH)
    {
        datamathang.child("MatHang").child(lstMH.getMaMH()).removeValue();
        lstmathang.remove(lstMH);
        adapter.notifyDataSetChanged();
        datamathang.child("MatHang").child(lstMH.getMaMH()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Toast.makeText(QuanLyMatHang.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(QuanLyMatHang.this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void showDialogEditMH(final QuanLyMatHangClass lstMH)
    {
        final AlertDialog.Builder alert = new AlertDialog.Builder(QuanLyMatHang.this);
        View mViewEditMatHang = (View)getLayoutInflater().inflate(R.layout.edit_mathang,null);
        alert.setView(mViewEditMatHang);
        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);

        // GET VIEWS
        Button btnEditMH = (Button) mViewEditMatHang.findViewById(R.id.btnEditMH);
        Button btnCancelEditMH = (Button) mViewEditMatHang.findViewById(R.id.btnCancelEditMH);

        final EditText etEditMaMH = (EditText)mViewEditMatHang.findViewById(R.id.etEditMaMH);
        final EditText etEditTenMH = (EditText)mViewEditMatHang.findViewById(R.id.etEditTenMH);
        final EditText etEditDonGia = (EditText)mViewEditMatHang.findViewById(R.id.etEditDonGia);
        final EditText etEditDvt = (EditText)mViewEditMatHang.findViewById(R.id.etEditDvt);
        final EditText etEditSoLuong = (EditText)mViewEditMatHang.findViewById(R.id.etEditSoLuong);
        final EditText etEditXuatXu = (EditText)mViewEditMatHang.findViewById(R.id.etEditXuatXu);
        final Spinner snEditNCC = (Spinner)mViewEditMatHang.findViewById(R.id.snEditNCC);
        final EditText etEditMoTa = (EditText)mViewEditMatHang.findViewById(R.id.etEditMoTa);

        //SET TEXT TO EDIT TEXT
        etEditMaMH.setText(lstMH.getMaMH());
        etEditTenMH.setText(lstMH.getTenMH());
        etEditDonGia.setText(lstMH.getDonGia().toString());
        etEditDvt.setText(lstMH.getDvt());
        etEditSoLuong.setText(lstMH.getSoLuong().toString());
        etEditXuatXu.setText(lstMH.getXuatXu());
        //snEditNCC.set(lstMH.getTenNCC());
        etEditMoTa.setText(lstMH.getMoTa());
        showDataSpinner();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        snEditNCC.setAdapter(arrayAdapter);
        snEditNCC.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TenNCC = arrayList.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnCancelEditMH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        // BUTTON EDIT CLICK -  ADD DATA TO FIREBASE
        btnEditMH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add data to firebase
                datamathang.child("MatHang").child(etEditMaMH.getText().toString()).removeValue();
                lstmathang.remove(lstMH);
                QuanLyMatHangClass qlMH = new QuanLyMatHangClass(etEditMaMH.getText().toString(),etEditTenMH.getText().toString(),
                        etEditDvt.getText().toString(),etEditXuatXu.getText().toString(),etEditMoTa.getText().toString(),lstMH.getTenNCC(),
                        Float.parseFloat(etEditDonGia.getText().toString()), Float.parseFloat(etEditSoLuong.getText().toString()));
                datamathang.child("MatHang").child(etEditMaMH.getText().toString()).setValue(qlMH);
                lstmathang.add(qlMH);

                datamathang.child("MatHang").child(etEditMaMH.getText().toString()).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Toast.makeText(QuanLyMatHang.this, "Chỉnh sửa thành công", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(QuanLyMatHang.this, "Chỉnh sửa thất bại", Toast.LENGTH_SHORT).show();
                    }
                });

                adapter.notifyDataSetChanged();
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
    private void showDataSpinner() {
        datamathang.child("NCC").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot item: snapshot.getChildren()){
                    arrayList.add(item.child("TenNCC").getValue().toString());
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
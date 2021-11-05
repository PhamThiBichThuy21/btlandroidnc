package com.example.btlandroidnc;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

public class QuanLyNCC extends AppCompatActivity {
    private ArrayList<QuanLyNCCClass> lstncc;
    private DatabaseReference datancc;
    private QuanLyNCCAdapter adapter;
    private QuanLyNCCClass QLNCC;
    private SwipeMenuListView listViewNCC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_n_c_c);
        listViewNCC = (SwipeMenuListView) findViewById(R.id.danhsachncc);
        lstncc = new ArrayList<QuanLyNCCClass>();
        adapter = new QuanLyNCCAdapter(QuanLyNCC.this, lstncc);
        listViewNCC.setAdapter(adapter);
        datancc = FirebaseDatabase.getInstance().getReference();
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
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
        listViewNCC.setMenuCreator(creator);
        listViewNCC.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        showDialogEdit(lstncc.get(position));
                        //Toast.makeText(QuanlyNCCActivity.this, "Sửa", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        deleteNcc(lstncc.get(position));
                        //Toast.makeText(QuanlyNCCActivity.this, "Xóa", Toast.LENGTH_SHORT).show();
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
        datancc.child("NCC").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lstncc.clear();
                for (DataSnapshot unit : dataSnapshot.getChildren()){
                    QuanLyNCCClass qlncc = unit.getValue(QuanLyNCCClass.class);
                    lstncc.add(qlncc);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.themNCC);
        floatingActionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                final AlertDialog.Builder alert = new AlertDialog.Builder(QuanLyNCC.this);
                View mViewQuanLyNcc = (View)getLayoutInflater().inflate(R.layout.add_ncc,null);
                final EditText etMaNCC = (EditText)mViewQuanLyNcc.findViewById(R.id.etMaNCC);
                final EditText etTenNCC = (EditText)mViewQuanLyNcc.findViewById(R.id.etTenNCC);
                final EditText etDiaChiNCC = (EditText)mViewQuanLyNcc.findViewById(R.id.etDiaChiNCC);
                final EditText etPhone = (EditText)mViewQuanLyNcc.findViewById(R.id.etPhone);

                Button btnAdd = (Button)mViewQuanLyNcc.findViewById(R.id.btnAddNCC);
                btnAdd.setText("Thêm");
                Button btnCancel = (Button)mViewQuanLyNcc.findViewById(R.id.btnCancelNCC);
                alert.setView(mViewQuanLyNcc);

                final AlertDialog alertDialog = alert.create();
                alertDialog.setCanceledOnTouchOutside(false);
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                if(etMaNCC.getText().toString() ==""){
                    Toast.makeText(QuanLyNCC.this, "Nhập mã nhà cung cấp", Toast.LENGTH_SHORT).show();
                }
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // add data to firebase
                        QuanLyNCCClass qlncc = new QuanLyNCCClass(etMaNCC.getText().toString(),etTenNCC.getText().toString(),etDiaChiNCC.getText().toString(),etPhone.getText().toString());
                        datancc.child("NCC").child(etMaNCC.getText().toString()).child("MaNCC").setValue(etMaNCC.getText().toString());
                        datancc.child("NCC").child(etMaNCC.getText().toString()).child("TenNCC").setValue(etTenNCC.getText().toString());
                        datancc.child("NCC").child(etMaNCC.getText().toString()).child("DiaChi").setValue(etDiaChiNCC.getText().toString());
                        datancc.child("NCC").child(etMaNCC.getText().toString()).child("Sdt").setValue(etPhone.getText().toString());
                        lstncc.add(qlncc);
                        adapter.notifyDataSetChanged();
                        datancc.child("NCC").child(etMaNCC.getText().toString()).addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                Toast.makeText(QuanLyNCC.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(QuanLyNCC.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                            }
                        });
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
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
    public void deleteNcc(final QuanLyNCCClass lstnhacungcap)
    {
        //Toast.makeText(QuanlyNCCActivity.this, lstnhacungcap.getMaNCC(), Toast.LENGTH_SHORT).show();
        datancc.child("NCC").child(lstnhacungcap.getMaNCC()).child("MaNCC").removeValue();
        datancc.child("NCC").child(lstnhacungcap.getMaNCC()).child("TenNCC").removeValue();
        datancc.child("NCC").child(lstnhacungcap.getMaNCC()).child("DiaChi").removeValue();
        datancc.child("NCC").child(lstnhacungcap.getMaNCC()).child("Sdt").removeValue();
        lstncc.remove(lstnhacungcap);
        adapter.notifyDataSetChanged();
        datancc.child("NCC").child(lstnhacungcap.getMaNCC()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Toast.makeText(QuanLyNCC.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(QuanLyNCC.this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void showDialogEdit(final QuanLyNCCClass lstnhacungcap)
    {
//        Toast.makeText(QuanlyNCCActivity.this, lstnhacungcap.getMaNCC(), Toast.LENGTH_SHORT).show();
        final AlertDialog.Builder alert = new AlertDialog.Builder(QuanLyNCC.this);
        View mViewEditncc = (View)getLayoutInflater().inflate(R.layout.edit_ncc,null);
        alert.setView(mViewEditncc);
        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);

        // GET VIEWS
        Button showEditSv = (Button)mViewEditncc.findViewById(R.id.btnEditNCC);
        Button cancelEditNcc = (Button)mViewEditncc.findViewById(R.id.btnCancelEditNCC);
        final EditText txtEditMaNCC = (EditText) mViewEditncc.findViewById(R.id.etEditMaNCC);
        final EditText txtEditTenNcc = (EditText) mViewEditncc.findViewById(R.id.etEditTenNCC);
        final EditText txtEditDiaChi = (EditText) mViewEditncc.findViewById(R.id.etEditDiaChiNCC);
        final EditText txtEditSdt = (EditText) mViewEditncc.findViewById(R.id.etEditPhone);

        //SET TEXT TO EDIT TEXT
        txtEditMaNCC.setText(lstnhacungcap.getMaNCC());
        txtEditTenNcc.setText(lstnhacungcap.getTenNCC());
        txtEditDiaChi.setText(lstnhacungcap.getDiaChi());
        txtEditSdt.setText(lstnhacungcap.getSdt());


        cancelEditNcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        // BUTTON EDIT CLICK -  ADD DATA TO FIREBASE
        showEditSv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // remove data in firebase
                datancc.child("NCC").child(lstnhacungcap.getMaNCC()).child("MaNCC").removeValue();
                datancc.child("NCC").child(lstnhacungcap.getMaNCC()).child("TenNCC").removeValue();
                datancc.child("NCC").child(lstnhacungcap.getMaNCC()).child("DiaChi").removeValue();
                datancc.child("NCC").child(lstnhacungcap.getMaNCC()).child("Sdt").removeValue();
                lstncc.remove(lstnhacungcap);
                // add data to firebase
                QuanLyNCCClass qlncc = new QuanLyNCCClass(txtEditMaNCC.getText().toString(),txtEditTenNcc.getText().toString(),txtEditDiaChi.getText().toString(),txtEditSdt.getText().toString());
                datancc.child("NCC").child(txtEditMaNCC.getText().toString()).child("MaNCC").setValue(txtEditMaNCC.getText().toString());
                datancc.child("NCC").child(txtEditMaNCC.getText().toString()).child("TenNCC").setValue(txtEditTenNcc.getText().toString());
                datancc.child("NCC").child(txtEditMaNCC.getText().toString()).child("DiaChi").setValue(txtEditDiaChi.getText().toString());
                datancc.child("NCC").child(txtEditMaNCC.getText().toString()).child("Sdt").setValue(txtEditSdt.getText().toString());
                lstncc.add(qlncc);
                adapter.notifyDataSetChanged();
                datancc.child("NCC").child(txtEditMaNCC.getText().toString()).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Toast.makeText(QuanLyNCC.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(QuanLyNCC.this, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
                adapter.notifyDataSetChanged();
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
}
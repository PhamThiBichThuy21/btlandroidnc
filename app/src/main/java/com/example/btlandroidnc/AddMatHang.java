package com.example.btlandroidnc;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.huawei.hms.hmsscankit.ScanUtil;
import com.huawei.hms.ml.scan.HmsScan;
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.StateSet.TAG;

public class AddMatHang extends AppCompatActivity {
    private static final int REQUEST_CODE_SCAN = 0X01;
    public static final int DEFAULT_VIEW = 0x22;
    private ArrayList<QuanLyMatHangClass> lstmathang;
    private DatabaseReference datamathang;
    private QuanLyMatHangAdapter adapter;
    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;
    ImageButton scanbtn;
    EditText etAddMaMH, etAddTenMH, etAddDonGia, etAddDvt, etAddSoLuong, etAddXuatXu,etAddMoTa;
    Spinner spAddNCC;
    String TenNCC = null;
    Button btnAdd, btnCancel;
    HmsScanAnalyzerOptions options;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mat_hang);
        datamathang = FirebaseDatabase.getInstance().getReference();
        lstmathang = new ArrayList<QuanLyMatHangClass>();
        adapter = new QuanLyMatHangAdapter(AddMatHang.this, lstmathang);
        getView();
        showDataSpinner();
            arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spAddNCC.setAdapter(arrayAdapter);
        spAddNCC.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TenNCC = arrayList.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        scanbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanCode();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddMatHang.this, QuanLyMatHang.class);
                startActivity(intent);
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add data to firebase
                QuanLyMatHangClass qlMH = new QuanLyMatHangClass(etAddMaMH.getText().toString(),etAddTenMH.getText().toString(),
                        etAddDvt.getText().toString(),etAddXuatXu.getText().toString(),etAddMoTa.getText().toString(),TenNCC,
                        Float.parseFloat(etAddDonGia.getText().toString()), Float.parseFloat(etAddSoLuong.getText().toString()));
                datamathang.child("MatHang").child(etAddMaMH.getText().toString()).setValue(qlMH);
                lstmathang.add(qlMH);
                adapter.notifyDataSetChanged();

                datamathang.child("MatHang").child(etAddMaMH.getText().toString()).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Toast.makeText(AddMatHang.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddMatHang.this, QuanLyMatHang.class);
                        startActivity(intent);
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
                        Toast.makeText(AddMatHang.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    private void scanCode(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.requestPermissions(
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE},
                    DEFAULT_VIEW);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissions == null || grantResults == null || grantResults.length < 2 || grantResults[0] !=
                PackageManager.PERMISSION_GRANTED || grantResults[1] != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (requestCode == DEFAULT_VIEW) {
            //A new screen will open to scan barcode and the control will go to Scan kit SDK.
            ScanUtil.startScan(AddMatHang.this, REQUEST_CODE_SCAN, new HmsScanAnalyzerOptions.Creator (). setHmsScanTypes (HmsScan.ALL_SCAN_TYPE, HmsScan.ALL_SCAN_TYPE) .create ());
            //Once the barcode is detected the control will again come back to parent application inside
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        if (requestCode == REQUEST_CODE_SCAN) {
            Object obj = data.getParcelableExtra(ScanUtil.RESULT);
            Log.d(TAG, "KQ: "+((HmsScan) obj).getOriginalValue());
            //result u will get
            if (obj instanceof HmsScan) {
                if (!TextUtils.isEmpty(((HmsScan) obj).getOriginalValue())) {
                    //Toast.makeText(this, ((HmsScan) obj).getOriginalValue(), Toast.LENGTH_SHORT).show();
                    etAddMaMH.setText(((HmsScan) obj).getOriginalValue());
                    //AddMatHang.this.finish();
                }
                //return;
            }
        }
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
    public void getView(){
        etAddMaMH = (EditText)findViewById(R.id.etAddMaMH);
        etAddTenMH = (EditText)findViewById(R.id.etAddTenMH);
        etAddDonGia = (EditText)findViewById(R.id.etAddDonGia);
        etAddDvt = (EditText)findViewById(R.id.etAddDvt);
        etAddSoLuong = (EditText)findViewById(R.id.etAddSoLuong);
        etAddXuatXu = (EditText)findViewById(R.id.etAddXuatXu);
        spAddNCC = (Spinner)findViewById(R.id.snAddNCC);
        etAddMoTa = (EditText)findViewById(R.id.etAddMoTa);
        btnAdd = (Button)findViewById(R.id.btnAddMH);
        btnCancel = (Button)findViewById(R.id.btnCancelMH);
        scanbtn = (ImageButton) findViewById(R.id.btnScan);
    }
}
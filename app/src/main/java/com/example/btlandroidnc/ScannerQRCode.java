package com.example.btlandroidnc;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

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

public class ScannerQRCode extends AppCompatActivity {
    private static final int REQUEST_CODE_SCAN = 0X01;
    public static final int DEFAULT_VIEW = 0x22;
    public ArrayList<QuanLyMatHangClass> lstMatHang;
    private DatabaseReference mData;
    private QuanLyMatHangAdapter adapter;
    int checkmh=0;
    private DatabaseReference dataFireBase;
    ImageButton bncheck;
    public TextView txtTenmathang, txtdvt, txtxuatxu, txtsoluong, txtdongia, txtmota, txtnhacc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner_q_r_code);

        ActionBar actionBar = getSupportActionBar();
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        getView();
        lstMatHang = new ArrayList<QuanLyMatHangClass>();
        adapter = new QuanLyMatHangAdapter(ScannerQRCode.this, lstMatHang);
        mData = FirebaseDatabase.getInstance().getReference();
        dataFireBase = FirebaseDatabase.getInstance().getReference();
        dataFireBase.child("MatHang").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lstMatHang.clear();
                Toast.makeText(getApplicationContext(), "Lay du lieu", Toast.LENGTH_LONG).show();
                Log.d("loioday1",dataSnapshot.getChildren().toString());
                for (DataSnapshot unit : dataSnapshot.getChildren()) {
                    Log.d("loioday",unit.getValue().toString());
                    QuanLyMatHangClass lstMH = unit.getValue(QuanLyMatHangClass.class);
                    lstMatHang.add(lstMH);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Log.d("TAG", "showInfoMH: "+lstMatHang);
        bncheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanCode();
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
    public void getView(){
        txtTenmathang   = (TextView) findViewById(R.id.txtTenmathang);
        txtdvt          = (TextView) findViewById(R.id.txtdvt);
        txtxuatxu       = (TextView) findViewById(R.id.txtxuatxu);
        txtsoluong      = (TextView) findViewById(R.id.txtsoluong);
        txtdongia       = (TextView) findViewById(R.id.txtdongia);
        txtmota         = (TextView) findViewById(R.id.txtmota);
        txtnhacc        = (TextView) findViewById(R.id.txtnhacc);
        bncheck         = (ImageButton)findViewById(R.id.imbnCheck);

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
            ScanUtil.startScan(ScannerQRCode.this, REQUEST_CODE_SCAN, new HmsScanAnalyzerOptions.Creator (). setHmsScanTypes (HmsScan.ALL_SCAN_TYPE, HmsScan.ALL_SCAN_TYPE) .create ());
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
                    //txtdongia.setText(((HmsScan) obj).getOriginalValue());
                    showInfoMH(((HmsScan) obj).getOriginalValue());
                    //AddMatHang.this.finish();
                }
                //return;
            }
        }
    }
    public void showInfoMH(final String mamh) {
        Log.d("TAG", "showInfoMH22: "+lstMatHang);
        for(int i = 0; lstMatHang.size() > i; i++)
        {
            if(lstMatHang.get(i).MaMH.equals(mamh))
            {
                //Toast.makeText(this, "Mặt hàng đã tồn tại "+lstMatHang.get(i).DonGia.toString(), Toast.LENGTH_SHORT).show();
                final TextView txtMaSv = (TextView)findViewById(R.id.txtTenmathang);
                txtTenmathang.setText(lstMatHang.get(i).TenMH);
                txtdvt.setText(lstMatHang.get(i).Dvt);
                txtdongia.setText(lstMatHang.get(i).DonGia.toString());
                txtnhacc.setText(lstMatHang.get(i).TenNCC);
                txtsoluong.setText(lstMatHang.get(i).SoLuong.toString());
                txtmota.setText(lstMatHang.get(i).MoTa);
                txtxuatxu.setText(lstMatHang.get(i).XuatXu);
                checkmh = 1;
                return;
            }
        }
        if(checkmh == 0)
        {
            Intent intent = new Intent(ScannerQRCode.this, AddMatHang.class);
            /*intent.putExtra(etAddMaMH, mamh); truyền dữ liệu sang AddMatHangActivity*/
            startActivity(intent);
            Toast.makeText(this, "Mặt hàng chưa tồn tại ", Toast.LENGTH_SHORT).show();
        }
    }
    public void gotoAddMH(View view)
    {
        Intent intent = new Intent(ScannerQRCode.this, AddMatHang.class);
        startActivity(intent);
    }
}
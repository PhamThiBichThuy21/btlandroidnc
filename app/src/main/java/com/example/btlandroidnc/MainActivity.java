package com.example.btlandroidnc;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View header=navigationView.getHeaderView(0);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    public void goToMH(View view)
    {
        Intent intent = new Intent(MainActivity.this, QuanLyMatHang.class);
        startActivity(intent);
        Toast.makeText(this, "Quản Lý mặt hàng", Toast.LENGTH_SHORT).show();
    }
    public void goToNCC(View view)
    {
        Intent intent = new Intent(MainActivity.this, QuanLyNCC.class);
        startActivity(intent);
        Toast.makeText(this, "Quản Lý nhà cung cấp", Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            Toast.makeText(this, "Đang ở trang chủ", Toast.LENGTH_SHORT).show();
        }else if (id == R.id.nav_ncc) {
            Intent intent = new Intent(MainActivity.this, QuanLyNCC.class);
            startActivity(intent);
        }else if (id == R.id.nav_mathang) {
            Intent intent = new Intent(MainActivity.this, QuanLyMatHang.class);
            startActivity(intent);
        }
        return false;
    }
}
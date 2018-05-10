package com.example.woo.qlsp_trungvit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ToolbarWidgetWrapper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.woo.qlsp_trungvit.R;

public class MainActivity extends AppCompatActivity {
    CardView cv_muavao, cv_banra, cv_khachhang, cv_thongke;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        addControls();
        addEvents();
    }

    private void addEvents() {
        cv_muavao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MuaVao.class);
                startActivity(intent);
            }
        });

        cv_banra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BanRa.class);
                startActivity(intent);
            }
        });

        cv_khachhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, KhachHang.class);
                startActivity(intent);
            }
        });

        cv_thongke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ThongKe.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.exit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.thoat){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void addControls() {
        cv_muavao = (CardView) findViewById(R.id.cv_muavao);
        cv_banra = (CardView) findViewById(R.id.cv_banra);
        cv_khachhang = (CardView) findViewById(R.id.cv_khachhang);
        cv_thongke = (CardView) findViewById(R.id.cv_thongke);
    }
}

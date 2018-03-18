package com.example.woo.qlsp_trungvit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.example.woo.qlsp_trungvit.R;

public class MainActivity extends AppCompatActivity {
    CardView cv_muavao, cv_banra, cv_khachhang, cv_thongke;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    private void addControls() {
        cv_muavao = (CardView) findViewById(R.id.cv_muavao);
        cv_banra = (CardView) findViewById(R.id.cv_banra);
        cv_khachhang = (CardView) findViewById(R.id.cv_khachhang);
        cv_thongke = (CardView) findViewById(R.id.cv_thongke);
    }
}

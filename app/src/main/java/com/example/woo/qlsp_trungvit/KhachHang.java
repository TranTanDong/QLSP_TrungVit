package com.example.woo.qlsp_trungvit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.woo.qlsp_trungvit.Adapter.KhachHangAdapter;
import com.example.woo.qlsp_trungvit.Model.ListKhachHang;

import java.util.ArrayList;

public class KhachHang extends AppCompatActivity {

    public static final int CODE_REQUEST_KHACHHANG = 1;
    public static final int CODE_RESULT_KHACHHANG = 2;

    RecyclerView rcv_khachHang;
    ArrayList<ListKhachHang> khachHangs = new ArrayList<>();
    KhachHangAdapter khachHangAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khach_hang);
        addControls();
        addEvents();
    }

    private void addControls() {
        khachHangs.add(new ListKhachHang("Mơ Văn Mộng", "0125458740", "P1-ST"));
        khachHangs.add(new ListKhachHang("Ngớ Thị Ngẩn", "0125875840", "P2-ST"));
        khachHangs.add(new ListKhachHang("Điên Nặng Điện", "0125452740", "P3-ST"));
        khachHangs.add(new ListKhachHang("Khung Quyền Khùng", "0141458740", "P4-ST"));
        khachHangs.add(new ListKhachHang("Cho Sắc Chó", "0125898740", "P5-ST"));

        rcv_khachHang = (RecyclerView)findViewById(R.id.rcv_khachHang);
        rcv_khachHang.setLayoutManager(new LinearLayoutManager(this));
        khachHangAdapter = new KhachHangAdapter(this, rcv_khachHang, khachHangs);
        rcv_khachHang.setAdapter(khachHangAdapter);
    }

    private void addEvents() {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_addkhachhang, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.addkhachhang){
            Intent intent = new Intent(KhachHang.this, AddKhachHang.class);
            startActivityForResult(intent, CODE_REQUEST_KHACHHANG);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}

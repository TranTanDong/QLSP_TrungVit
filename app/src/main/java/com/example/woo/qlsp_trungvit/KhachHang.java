package com.example.woo.qlsp_trungvit;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.woo.qlsp_trungvit.Adapter.KhachHangAdapter;
import com.example.woo.qlsp_trungvit.Interface.IKhachHang;
import com.example.woo.qlsp_trungvit.Model.ListKhachHang;

import java.util.ArrayList;

public class KhachHang extends AppCompatActivity implements IKhachHang {

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
        khachHangs.add(new ListKhachHang("Ngớ Thị Ngẩn", "0125875840", "P2-CM"));
        khachHangs.add(new ListKhachHang("Điên Nặng Điện", "0125452740", "P3-TN"));
        khachHangs.add(new ListKhachHang("Khung Quyền Khùng", "0141458740", "P4-HN"));
        khachHangs.add(new ListKhachHang("Ê Sắc Ế", "0125898740", "P5-QN"));
        khachHangs.add(new ListKhachHang("Láo Văn Cá", "012543740", "P6-VT"));
        khachHangs.add(new ListKhachHang("Bò Thị Lếch", "012587650", "P7-ĐN"));
        khachHangs.add(new ListKhachHang("Kiếm Văn Chuyện", "0129894740", "P8-KG"));
        khachHangs.add(new ListKhachHang("Đánh Văn Chạy", "0125890740", "P9-KH"));
        khachHangs.add(new ListKhachHang("Chạy Thị Chú", "0123458740", "Q1-HG"));

        rcv_khachHang = (RecyclerView)findViewById(R.id.rcv_khachHang);
        rcv_khachHang.setLayoutManager(new LinearLayoutManager(this));
        khachHangAdapter = new KhachHangAdapter(this, khachHangs, this);
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

        if (requestCode == CODE_REQUEST_KHACHHANG && resultCode == CODE_RESULT_KHACHHANG){
            String tenKH = data.getStringExtra("TenKH");
            String sdtKH = data.getStringExtra("SDTKH");
            String diachiKH = data.getStringExtra("DiaChiKH");

            khachHangs.add(new ListKhachHang(tenKH, sdtKH, diachiKH));
            khachHangAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void DelKhachHang(final int pos) {
        khachHangs.remove(pos);
        Toast.makeText(this,"Đã Click Xóa", Toast.LENGTH_LONG).show();
    }

    @Override
    public void CallKhachHang(final int pos) {
        Uri uri = Uri.parse("tel:"+khachHangs.get(pos).getSdtKH().toString());
        //Log.i("TTD", uri.toString());
        Intent cIntent = new Intent(Intent.ACTION_DIAL);
        cIntent.setData(uri);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(cIntent);
        //Toast.makeText(this,"Đã Click Gọi", Toast.LENGTH_LONG).show();
    }
}

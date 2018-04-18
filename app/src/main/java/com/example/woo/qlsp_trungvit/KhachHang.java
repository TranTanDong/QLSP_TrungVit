package com.example.woo.qlsp_trungvit;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.Manifest;

import com.example.woo.qlsp_trungvit.Adapter.KhachHangAdapter;
import com.example.woo.qlsp_trungvit.Interface.IKhachHang;
import com.example.woo.qlsp_trungvit.Model.ListKhachHang;

import java.util.ArrayList;

public class KhachHang extends AppCompatActivity implements IKhachHang {

    public static final int CODE_REQUEST_KHACHHANG = 1;
    public static final int CODE_RESULT_KHACHHANG = 2;
    private int REQUEST_CALL_PHONE = 0;

    RecyclerView rcv_khachHang;
    public static ArrayList<ListKhachHang> khachHangs = new ArrayList<>();
    KhachHangAdapter khachHangAdapter;
    private int vt;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khach_hang);
        addControls();
        addEvents();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL_PHONE && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            callCus();
        }
        else {
            Toast.makeText(KhachHang.this, "Bạn chưa cho phép gọi!", Toast.LENGTH_LONG).show();
        }
    }

    private void callCus() {
        Uri uri = Uri.parse("tel:"+khachHangs.get(vt).getSdtKH().toString());
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


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void addControls() {

        //Thêm dữ liệu vào khachHangs để test
        khachHangs.clear();
        khachHangs.add(new ListKhachHang(1, "Mơ Văn Mộng", "0125458740", "P1-ST"));
        khachHangs.add(new ListKhachHang(2, "Ngớ Thị Ngẩn", "0125875840", "P2-CM"));
        khachHangs.add(new ListKhachHang(3, "Điên Nặng Điện", "0125452740", "P3-TN"));
        khachHangs.add(new ListKhachHang(4, "Khung Quyền Khùng", "0141458740", "P4-HN"));
        khachHangs.add(new ListKhachHang(5, "Ê Sắc Ế", "0125898740", "P5-QN"));
        khachHangs.add(new ListKhachHang(6, "Láo Văn Cá", "012543740", "P6-VT"));
        khachHangs.add(new ListKhachHang(7, "Bò Thị Lếch", "012587650", "P7-ĐN"));
        khachHangs.add(new ListKhachHang(8, "Kiếm Văn Chuyện", "0129894740", "P8-KG"));
        khachHangs.add(new ListKhachHang(9, "Đánh Văn Chạy", "0125890740", "P9-KH"));
        khachHangs.add(new ListKhachHang(10, "Chạy Thị Chú", "0123458740", "Q1-HG"));

        //Xử lý RecyclerView rcv_khachHang
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CODE_REQUEST_KHACHHANG && resultCode == CODE_RESULT_KHACHHANG){
            String tenKH = data.getStringExtra("TenKH");
            String sdtKH = data.getStringExtra("SDTKH");
            String diachiKH = data.getStringExtra("DiaChiKH");

            khachHangs.add(new ListKhachHang(khachHangs.size()+1, tenKH, sdtKH, diachiKH));
            khachHangAdapter.notifyDataSetChanged();
            AddGiaoDich.arrayList.add(tenKH);
        }
    }


    //Click Button Xóa Khách Hàng
    @Override
    public void DelKhachHang(final int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(KhachHang.this);
        builder.setTitle("Xóa Khách Hàng");
        builder.setMessage("Bạn muốn xóa '"+khachHangs.get(pos).getTenKH()+"'?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                khachHangs.remove(pos);
                khachHangAdapter.notifyDataSetChanged();
//                Toast.makeText(this,"Đã Click Xóa", Toast.LENGTH_LONG).show();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    //Click Button Gọi Khách Hàng
    @Override
    public void CallKhachHang(final int pos) {
        vt = pos;
        int checkPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        if (checkPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    REQUEST_CALL_PHONE);
            return;
        }
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


    //Click Item Khách Hàng
    @Override
    public void ClickItemKhachHang(int pos) {
        Toast.makeText(KhachHang.this,khachHangs.get(pos).getMaKH()+" "+khachHangs.get(pos).getTenKH(), Toast.LENGTH_SHORT).show();
    }

    //Click Button Sửa Khách Hàng
    @Override
    public void EditKhachHang(final int pos) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(KhachHang.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_edit_khachhang, null);
        builder.setView(dialogView);
        builder.setCancelable(false);

        final EditText et_tenKH_edit, et_sdt_edit, et_diachi_edit;
        et_tenKH_edit = dialogView.findViewById(R.id.et_tenKH_edit);
        et_sdt_edit = dialogView.findViewById(R.id.et_sdt_edit);
        et_diachi_edit = dialogView.findViewById(R.id.et_diachi_edit);

        //Gán dữ liệu
        et_tenKH_edit.setText(khachHangs.get(pos).getTenKH().toString());
        et_sdt_edit.setText(khachHangs.get(pos).getSdtKH().toString());
        et_diachi_edit.setText(khachHangs.get(pos).getDiachiKH());


        builder.setPositiveButton("SỬA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String tenKH = khachHangs.get(pos).getTenKH().toString();
                String sdtKH = khachHangs.get(pos).getSdtKH().toString();
                String dcKH = khachHangs.get(pos).getDiachiKH().toString();
                int maKH = khachHangs.get(pos).getMaKH();
                if (tenKH.equals(et_tenKH_edit.getText().toString()) && sdtKH.equals(et_sdt_edit.getText().toString()) && dcKH.equals(et_diachi_edit.getText().toString())){
                    dialogInterface.dismiss();
                }else {
                    khachHangs.remove(pos);
                    khachHangs.add(new ListKhachHang(maKH, et_tenKH_edit.getText().toString(), et_sdt_edit.getText().toString(), et_diachi_edit.getText().toString()));
                    khachHangAdapter.notifyDataSetChanged();
                    dialogInterface.dismiss();
                }
            }
        });

        builder.setNegativeButton("HỦY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        //Toast.makeText(KhachHang.this, "Muốn sửa "+khachHangs.get(pos).getTenKH()+" à! Đéo nhé!", Toast.LENGTH_SHORT).show();
    }
}

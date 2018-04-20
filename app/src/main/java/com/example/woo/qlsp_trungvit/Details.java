package com.example.woo.qlsp_trungvit;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class Details extends AppCompatActivity {

    private TextView    tv_soLuong_detail,
                        tv_donGia_detail,
                        tv_loai_detail,
                        tv_thoiGian_detail,
                        tv_khachHang_detail,
                        tv_tinhTien_detail,
                        title_detail;
    private ImageView img_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        addControls();
        addEvents();
    }

    private void addControls() {
        tv_soLuong_detail = findViewById(R.id.tv_soLuong_detail);
        tv_donGia_detail = findViewById(R.id.tv_donGia_detail);
        tv_loai_detail = findViewById(R.id.tv_loai_detail);
        tv_thoiGian_detail = findViewById(R.id.tv_thoiGian_detail);
        tv_khachHang_detail = findViewById(R.id.tv_khachHang_detail);
        tv_tinhTien_detail = findViewById(R.id.tv_tinhTien_detail);
        title_detail = findViewById(R.id.title_detail);
        img_detail = findViewById(R.id.img_detail);

        Intent dIntent = getIntent();
        int SL = dIntent.getIntExtra("SLD", -1);
        int DG = dIntent.getIntExtra("DGD", -1);
        String L = dIntent.getStringExtra("LD");
        String TG = dIntent.getStringExtra("TGD");
        String KH = dIntent.getStringExtra("KHD");
        int M = dIntent.getIntExtra("MD", -1);
        int P = dIntent.getIntExtra("PD", -1);

        //Set giá trị
        tv_soLuong_detail.setText(String.valueOf(SL));
        tv_donGia_detail.setText(String.valueOf(DG));
        tv_loai_detail.setText(L);
        tv_thoiGian_detail.setText(TG);
        tv_khachHang_detail.setText(KH);
        tv_tinhTien_detail.setText(String.valueOf(SL*DG));


       // Log.i("Detail", SL+" "+DG+" "+L+" "+TG+" "+KH+" "+M);
    }

    private void addEvents() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.edit_detail){

        }

        if (item.getItemId()==R.id.del_detail){
            AlertDialog.Builder builder = new AlertDialog.Builder(Details.this);
            builder.setTitle("Xóa Sản Phẩm");
            builder.setMessage("Bạn muốn xóa giao dịch này không?");
            builder.setCancelable(false);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
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

        return super.onOptionsItemSelected(item);
    }
}
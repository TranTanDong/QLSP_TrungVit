package com.example.woo.qlsp_trungvit;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.woo.qlsp_trungvit.Adapter.SanPhamAdapter;
import com.example.woo.qlsp_trungvit.Interface.ISanPham;
import com.example.woo.qlsp_trungvit.Model.ListSanPham;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DetailThongKe extends AppCompatActivity implements ISanPham {

    private RecyclerView rcv_detailThongKe;
    private ArrayList<ListSanPham> detailThongKes = new ArrayList<>();
    private SanPhamAdapter detailThongKeAdapter;

    private Database database;

    private TextView tv_tongSoLuongTK, tv_giaTrungBinhTK, tv_tongTienTK, tv_demItemTK;
    private DecimalFormat dcf = new DecimalFormat("#,###,###,###");

    Intent tkIntent;
    String Time;
    int Flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_thong_ke);
        addControls();
        addEvents();

        showListDetail();
    }

    private void showListDetail() {
        String tabname = "BanRa", col="BR_MA";
        if (Flag == ThongKe.CODE_REQUEST_MV){
            tabname = "MuaVao";
            col = "MV_MA";
        }

        Cursor data = database.GetData("SELECT * FROM "+tabname+" ORDER BY "+col+" DESC");
        detailThongKes.clear();
        int sumSL = 0;
        int sumTien = 0;
        double avgDG;

        int l = Time.length();

        while (data.moveToNext()){
            int Ma = data.getInt(0);
            int SL = data.getInt(1);
            int DG = data.getInt(2);
            String L = data.getString(3);
            String TG = data.getString(4);
            int MaKH = data.getInt(5);

            Cursor datatenKH = database.GetData("SELECT * FROM KhachHang WHERE KH_MA="+MaKH+"");
            String tenKH;
            if (datatenKH.moveToFirst()){
                tenKH = datatenKH.getString(1);
            } else tenKH = "GUEST";

            if (Time.equals(TG.substring(0, l))){
                sumTien += SL*DG;
                sumSL += SL;
                detailThongKes.add(new ListSanPham(Ma, SL, DG, L, TG, tenKH));
            }

        }
        data.close();
        detailThongKeAdapter.notifyDataSetChanged();

        tv_tongTienTK.setText(dcf.format(sumTien)+"đ");
        tv_tongSoLuongTK.setText(dcf.format(sumSL)+"");
        if (detailThongKes.size() != 0){
            avgDG = sumTien/sumSL;
            tv_giaTrungBinhTK.setText(dcf.format(avgDG)+"");
        } else tv_giaTrungBinhTK.setText("0");

        tv_demItemTK.setText("Số giao dịch: "+detailThongKes.size());
    }

    private void addControls() {
        database = new Database(DetailThongKe.this);

        tv_tongSoLuongTK = findViewById(R.id.tv_tongSoLuongTK);
        tv_giaTrungBinhTK = findViewById(R.id.tv_giaTrungBinhTK);
        tv_tongTienTK = findViewById(R.id.tv_tongTienTK);
        tv_demItemTK = findViewById(R.id.tv_demItemTK);


        //Xử lý RecyclerView rcv_muaVao
        rcv_detailThongKe = findViewById(R.id.rcv_detailThongKe);
        rcv_detailThongKe.setLayoutManager(new LinearLayoutManager(this));
        detailThongKeAdapter = new SanPhamAdapter(DetailThongKe.this, detailThongKes, this);
        rcv_detailThongKe.setAdapter(detailThongKeAdapter);


        //Nhận dữ liệu từ ThongKe
        tkIntent = getIntent();
        Time = tkIntent.getStringExtra("Time");
        Flag = tkIntent.getIntExtra("Flag", -1);
//        Toast.makeText(DetailThongKe.this, Time+" "+Flag, Toast.LENGTH_SHORT).show();
        if (Flag == ThongKe.CODE_REQUEST_MV){
            setTitle("Chi Tiết Mua Vào "+Time);
        }else setTitle("Chi Tiết Bán Ra "+Time);
    }

    private void addEvents() {

    }

    @Override
    public void ClickItemSanPham(int pos) {

    }
}

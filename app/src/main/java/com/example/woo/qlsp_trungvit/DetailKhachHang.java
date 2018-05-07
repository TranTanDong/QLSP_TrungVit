package com.example.woo.qlsp_trungvit;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.woo.qlsp_trungvit.Adapter.SanPhamAdapter;
import com.example.woo.qlsp_trungvit.Interface.ISanPham;
import com.example.woo.qlsp_trungvit.Model.ListSanPham;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DetailKhachHang extends AppCompatActivity implements ISanPham{

    private RecyclerView rcv_muaKH, rcv_banKH;
    private TabHost tabHost;
    private TextView tv_tongSL_Mua, tv_giaTB_Mua, tv_tongTien_Mua, tv_tongSL_Ban, tv_giaTB_Ban, tv_tongTien_Ban, tv_demItemKHMV, tv_demItemKHBR;

    ArrayList<ListSanPham> muaSanPhams = new ArrayList<>();
    ArrayList<ListSanPham> banSanPhams = new ArrayList<>();
    private SanPhamAdapter muaAdapter, banAdapter;

    Database database;

    private DecimalFormat dcf = new DecimalFormat("#,###,###,###");

    int MA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_khach_hang);
        addControls();
        addEvents();

        showListMuaVaoOfKhachHang();
        showListBanRaOfKhachHang();
    }


    private void addControls() {

        database = new Database(DetailKhachHang.this);

        tv_tongSL_Mua = findViewById(R.id.tv_tongSL_Mua);
        tv_giaTB_Mua = findViewById(R.id.tv_giaTB_Mua);
        tv_tongTien_Mua = findViewById(R.id.tv_tongTien_Mua);
        tv_tongSL_Ban = findViewById(R.id.tv_tongSL_Ban);
        tv_giaTB_Ban = findViewById(R.id.tv_giaTB_Ban);
        tv_tongTien_Ban = findViewById(R.id.tv_tongTien_Ban);
        tv_demItemKHMV = findViewById(R.id.tv_demItemKHMV);
        tv_demItemKHBR = findViewById(R.id.tv_demItemKHBR);

        //Nhận dữ liệu từ ClickItemKhachHang
        Intent fIntent = getIntent();
        MA = fIntent.getIntExtra("KH_MA", -1);
        String TEN = fIntent.getStringExtra("KH_TEN");

        setTitle(TEN);

        //Xử lý TabHost
        tabHost = findViewById(R.id.tabHost);
        tabHost.setup();

       TabHost.TabSpec tab1 = tabHost.newTabSpec("t1");
       tab1.setIndicator("MUA");
       tab1.setContent(R.id.tab1);
       tabHost.addTab(tab1);

        TabHost.TabSpec tab2 = tabHost.newTabSpec("t2");
        tab2.setIndicator("BÁN");
        tab2.setContent(R.id.tab2);
        tabHost.addTab(tab2);


        //Xử lý RecyclerView
        rcv_muaKH = findViewById(R.id.rcv_muaKH);
        rcv_muaKH.setLayoutManager(new LinearLayoutManager(this));
        muaAdapter = new SanPhamAdapter(DetailKhachHang.this, muaSanPhams, this);
        rcv_muaKH.setAdapter(muaAdapter);

        rcv_banKH = findViewById(R.id.rcv_banKH);
        rcv_banKH.setLayoutManager(new LinearLayoutManager(this));
        banAdapter = new SanPhamAdapter(DetailKhachHang.this, banSanPhams, this);
        rcv_banKH.setAdapter(banAdapter);




    }

    private void showListMuaVaoOfKhachHang() {
        Cursor data = database.GetData("SELECT * FROM MuaVao WHERE MV_MAKH="+MA+" ORDER BY MV_MA DESC");
        muaSanPhams.clear();
        long sumSL = 0;
        long sumTien = 0;
        double avgDG;
        while (data.moveToNext()){
            int Ma = data.getInt(0);
            int SL = data.getInt(1);
            int DG = data.getInt(2);
            String L = data.getString(3);
            String TG = data.getString(4);
            int MaKH = data.getInt(5);

            long sl = SL, dg = DG;
            sumTien += sl*dg;
            sumSL += sl;

            Cursor datatenKH = database.GetData("SELECT * FROM KhachHang WHERE KH_MA="+MaKH+"");
            datatenKH.moveToFirst();
            String tenKH = datatenKH.getString(1);
            muaSanPhams.add(new ListSanPham(Ma, SL, DG, L, TG, ""));
        }
        data.close();
        muaAdapter.notifyDataSetChanged();

        tv_tongSL_Mua.setText(dcf.format(sumSL)+"");
        tv_tongTien_Mua.setText(dcf.format(sumTien)+"đ");
        if (muaSanPhams.size()!=0){
           avgDG = sumTien/sumSL;
           tv_giaTB_Mua.setText(dcf.format(avgDG)+"");
        } else tv_giaTB_Mua.setText("0");

        tv_demItemKHMV.setText("Số giao dịch: "+muaSanPhams.size());
    }

    private void showListBanRaOfKhachHang() {
        Cursor data = database.GetData("SELECT * FROM BanRa WHERE BR_MAKH="+MA+" ORDER BY BR_MA DESC");
        banSanPhams.clear();
        long sumSL = 0;
        long sumTien = 0;
        double avgDG;
        while (data.moveToNext()){
            int Ma = data.getInt(0);
            int SL = data.getInt(1);
            int DG = data.getInt(2);
            String L = data.getString(3);
            String TG = data.getString(4);
            int MaKH = data.getInt(5);

            long sl = SL, dg = DG;
            sumTien += sl*dg;
            sumSL += sl;

            Cursor datatenKH = database.GetData("SELECT * FROM KhachHang WHERE KH_MA="+MaKH+"");
            datatenKH.moveToFirst();
            String tenKH = datatenKH.getString(1);
            banSanPhams.add(new ListSanPham(Ma, SL, DG, L, TG, ""));
        }
        data.close();
        banAdapter.notifyDataSetChanged();

        tv_tongSL_Ban.setText(dcf.format(sumSL)+"");
        tv_tongTien_Ban.setText(dcf.format(sumTien)+"đ");
        if (banSanPhams.size()!=0){
            avgDG = sumTien/sumSL;
            tv_giaTB_Ban.setText(dcf.format(avgDG)+"");
        } else tv_giaTB_Ban.setText("0");

        tv_demItemKHBR.setText("Số giao dịch: "+banSanPhams.size());
    }

    private void addEvents() {

    }

    @Override
    public void ClickItemSanPham(int pos) {

    }
}

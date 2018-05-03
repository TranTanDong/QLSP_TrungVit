package com.example.woo.qlsp_trungvit;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.woo.qlsp_trungvit.Adapter.ThongKeBRAdapter;
import com.example.woo.qlsp_trungvit.Adapter.ThongKeMVAdapter;
import com.example.woo.qlsp_trungvit.Interface.IThongKeBR;
import com.example.woo.qlsp_trungvit.Interface.IThongKeMV;

import java.util.ArrayList;
import java.util.Collections;

public class ThongKe extends AppCompatActivity implements IThongKeMV, IThongKeBR {

    private RecyclerView rcv_thongKeMV, rcv_thongKeBR;
    private ThongKeMVAdapter thongKeMVAdapter;
    private ThongKeBRAdapter thongKeBRAdapter;
    private ArrayList<String> listTimeThongKeMV = new ArrayList<String>();
    private ArrayList<String> listTimeThongKeBR = new ArrayList<String>();
    //private ArrayList<Long> listTien = new ArrayList<Long>();

    private TabHost tabHost;

    Database database;

    public static final int CODE_REQUEST_MV = 16;
    public static final int CODE_REQUEST_BR = 17;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);
        addControls();
        addEvents();

        addListTimeMV(7);
        addListTimeBR(7);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.item_view_time, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.ngay:
                setTitle("Thống Kê Theo Ngày");
                addListTimeBR(10);
                addListTimeMV(10);
                break;
            case R.id.thang:
                setTitle("Thống Kê Theo Tháng");
                addListTimeBR(7);
                addListTimeMV(7);
                break;
            case R.id.nam:
                setTitle("Thống Kê Theo Năm");
                addListTimeMV(4);
                addListTimeBR(4);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addControls() {

        database = new Database(ThongKe.this);


        //Xử lý TabHost
        tabHost = findViewById(R.id.tabHostTK);
        tabHost.setup();

        TabHost.TabSpec tab1 = tabHost.newTabSpec("t1");
        tab1.setIndicator("MUA");
        tab1.setContent(R.id.tab1);
        tabHost.addTab(tab1);

        TabHost.TabSpec tab2 = tabHost.newTabSpec("t2");
        tab2.setIndicator("BÁN");
        tab2.setContent(R.id.tab2);
        tabHost.addTab(tab2);


        rcv_thongKeMV = findViewById(R.id.rcv_thongKeMV);
        rcv_thongKeMV.setLayoutManager(new LinearLayoutManager(this));
        thongKeMVAdapter = new ThongKeMVAdapter(ThongKe.this, listTimeThongKeMV, this);
        rcv_thongKeMV.setAdapter(thongKeMVAdapter);

        rcv_thongKeBR = findViewById(R.id.rcv_thongKeBR);
        rcv_thongKeBR.setLayoutManager(new LinearLayoutManager(this));
        thongKeBRAdapter = new ThongKeBRAdapter(ThongKe.this, listTimeThongKeBR, this);
        rcv_thongKeBR.setAdapter(thongKeBRAdapter);
    }

    private void addListTimeMV(int i) {
        listTimeThongKeMV.clear();
        Cursor data = database.GetData("SELECT * FROM MuaVao");
        while (data.moveToNext()){
            String TG = data.getString(4);

            String tg = TG.substring(0, i);

            if (listTimeThongKeMV.contains(tg) == false){
                listTimeThongKeMV.add(tg);
            }
        }
        data.close();
        Collections.sort(listTimeThongKeMV);
        thongKeMVAdapter.notifyDataSetChanged();
    }

    private void addListTimeBR(int i) {
        listTimeThongKeBR.clear();
        Cursor data = database.GetData("SELECT * FROM BanRa");
        while (data.moveToNext()){
            String TG = data.getString(4);

            String tg = TG.substring(0, i);
            if (listTimeThongKeBR.contains(tg) == false){
                listTimeThongKeBR.add(tg);
            }
        }
        data.close();
        Collections.sort(listTimeThongKeBR);
        thongKeBRAdapter.notifyDataSetChanged();
    }

    private void addEvents() {

    }

    @Override
    public void ClickItemThongKeMV(int i) {
        Intent mIntent = new Intent(ThongKe.this, DetailThongKe.class);
        mIntent.putExtra("Time", listTimeThongKeMV.get(i));
        mIntent.putExtra("Flag", CODE_REQUEST_MV);
        startActivityForResult(mIntent, CODE_REQUEST_MV);
//        Toast.makeText(this, listTimeThongKeMV.get(i), Toast.LENGTH_LONG).show();
    }

    @Override
    public void ClickItemThongKeBR(int i) {
        Intent bIntent = new Intent(ThongKe.this, DetailThongKe.class);
        bIntent.putExtra("Time", listTimeThongKeBR.get(i));
        bIntent.putExtra("Flag", CODE_REQUEST_BR);
        startActivityForResult(bIntent, CODE_REQUEST_BR);
//        Toast.makeText(this, listTimeThongKeBR.get(i), Toast.LENGTH_LONG).show();
    }
}

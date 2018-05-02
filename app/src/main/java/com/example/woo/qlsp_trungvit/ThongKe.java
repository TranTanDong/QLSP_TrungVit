package com.example.woo.qlsp_trungvit;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.woo.qlsp_trungvit.Adapter.ThongKeAdapter;
import com.example.woo.qlsp_trungvit.Interface.IThongKe;

import java.util.ArrayList;

public class ThongKe extends AppCompatActivity implements IThongKe {

    private RecyclerView rcv_thongKe;
    private ThongKeAdapter thongKeAdapter;
    private ArrayList<String> listTimeThongKes = new ArrayList<>();
    private ArrayList<Long> listTien = new ArrayList<Long>();

    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);
        addControls();
        addEvents();

        addListTime();
    }

    private void addControls() {

        database = new Database(ThongKe.this);


        rcv_thongKe = findViewById(R.id.rcv_thongKe);
        rcv_thongKe.setLayoutManager(new LinearLayoutManager(this));
        thongKeAdapter = new ThongKeAdapter(ThongKe.this, listTimeThongKes, this);
        rcv_thongKe.setAdapter(thongKeAdapter);
    }

    private void addListTime() {
        listTien.clear();
        listTimeThongKes.clear();
        Cursor data = database.GetData("SELECT * FROM MuaVao");
        while (data.moveToNext()){
            int SL = data.getInt(1);
            int DG = data.getInt(2);
            String TG = data.getString(4);

            String tg = TG.substring(0, 7);

            if (listTimeThongKes.contains(tg)){

                int i = listTimeThongKes.indexOf(tg);
                long t = listTien.get(i);
                t += SL*DG;
                listTien.set(i, t);

            } else {
                listTimeThongKes.add(tg);
                long t = SL*DG;
                listTien.add(t);
            }
        }
        data.close();

        thongKeAdapter.notifyDataSetChanged();
    }

    private void addEvents() {

    }

    @Override
    public void ClickItemThongKe(int i) {
        Toast.makeText(this, listTien.get(i)+"", Toast.LENGTH_LONG).show();
    }
}

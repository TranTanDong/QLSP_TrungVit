package com.example.woo.qlsp_trungvit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MuaVao extends AppCompatActivity {

    public static final int CODE_REQUEST_ADDSP = 3;
    public static final int CODE_RESULT_ADDSP = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mua_vao);
        addControls();
        addEvents();
    }

    private void addEvents() {

    }

    private void addControls() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.add){
            Intent intent = new Intent(MuaVao.this, AddGiaoDich.class);
            startActivityForResult(intent, CODE_REQUEST_ADDSP);
        }
        return super.onOptionsItemSelected(item);
    }
}

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

import java.text.DecimalFormat;

public class Details extends AppCompatActivity {

    private TextView    tv_soLuong_detail,
                        tv_donGia_detail,
                        tv_loai_detail,
                        tv_thoiGian_detail,
                        tv_khachHang_detail,
                        tv_tinhTien_detail,
                        title_detail;
    private ImageView img_detail;

    private int SL, DG, M, C;
    private String L, TG, KH;
    private Intent dIntent;

    private DecimalFormat dcf = new DecimalFormat("#,###,###,###");

    public static final int REQUEST_EDIT = 6;
    public static final int RESULT_EDITMV = 7;
    public static final int RESULT_EDITBR = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        addControls();
        addEvents();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode==REQUEST_EDIT && resultCode==RESULT_EDITMV && data!=null) || (requestCode==REQUEST_EDIT && resultCode==RESULT_EDITBR && data!=null)){
            String SL = data.getStringExtra("ESL");
            String DG = data.getStringExtra("EDG");
            String L = data.getStringExtra("EL");
            String TG = data.getStringExtra("ETG");
            int M = data.getIntExtra("EM", -1);
            String KH = data.getStringExtra("EKH");
            Log.i("InfEdit", SL+L+DG+TG+M+KH);


            dIntent.putExtra("MBSL", SL);
            dIntent.putExtra("MBDG", DG);
            dIntent.putExtra("MBL", L);
            dIntent.putExtra("MBTG", TG);
            dIntent.putExtra("MBKH", KH);
            dIntent.putExtra("MBM", M);

            if (resultCode==RESULT_EDITMV){
                setResult(MuaVao.CODE_RESULT_DETAILE, dIntent);
            }else setResult(BanRa.CODE_RESULT_DETAILBRE, dIntent);

            finish();
        }
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

        //Xử lý thông tin từ MuaVao | BanRa gửi qua
        dIntent = getIntent();
        C = dIntent.getIntExtra("CodeDetail", -1);

        //Set giá trị
        if (C==MuaVao.CODE_REQUEST_DETAIL || C==BanRa.CODE_REQUEST_DETAILBR){
            SL = dIntent.getIntExtra("SLD", -1);
            DG = dIntent.getIntExtra("DGD", -1);
            L = dIntent.getStringExtra("LD");
            TG = dIntent.getStringExtra("TGD");
            KH = dIntent.getStringExtra("KHD");
            M = dIntent.getIntExtra("MD", -1);


            tv_soLuong_detail.setText(dcf.format(SL));
            tv_donGia_detail.setText(dcf.format(DG));
            tv_loai_detail.setText(L);
            tv_thoiGian_detail.setText(TG);
            tv_khachHang_detail.setText(KH);
            long sl=SL, dg=DG, s=sl*dg;
            tv_tinhTien_detail.setText(dcf.format(s)+"đ");
        }

        if (C==BanRa.CODE_REQUEST_DETAILBR){
            title_detail.setText("Trứng Bán Ra");
            img_detail.setImageResource(R.drawable.ic_banra);
        }


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
            Intent sIntent = new Intent(Details.this, EditGiaoDich.class);
            sIntent.putExtra("SLE", SL);
            sIntent.putExtra("DGE", DG);
            sIntent.putExtra("TGE", TG);
            sIntent.putExtra("LE", L);
            sIntent.putExtra("KHE", KH);
            sIntent.putExtra("ME", M);
            sIntent.putExtra("CE", C);
            sIntent.putExtra("CDE", REQUEST_EDIT);
            startActivityForResult(sIntent, REQUEST_EDIT);
        }

        if (item.getItemId()==R.id.del_detail){
            AlertDialog.Builder builder = new AlertDialog.Builder(Details.this);
            builder.setMessage("Bạn muốn xóa giao dịch này không?");
            builder.setCancelable(false);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dIntent.putExtra("MaDelete", M);
                    Log.i("DL", String.valueOf(M));
                    if (C==BanRa.CODE_REQUEST_DETAILBR){
                        setResult(BanRa.CODE_RESULT_DETAILBR, dIntent);
                    }else setResult(MuaVao.CODE_RESULT_DETAIL, dIntent);

                    dialogInterface.dismiss();
                    Details.this.finish();
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

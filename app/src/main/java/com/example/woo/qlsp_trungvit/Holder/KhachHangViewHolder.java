package com.example.woo.qlsp_trungvit.Holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.woo.qlsp_trungvit.R;

/**
 * Created by Woo on 3/22/2018.
 */

public class KhachHangViewHolder extends RecyclerView.ViewHolder {
    public TextView tv_tenKH;
    public TextView tv_sdtKH;
    public TextView tv_diachiKH;
    public ImageView btn_Goi;
    public ImageView btn_Xoa;

    public KhachHangViewHolder(View itemView) {
        super(itemView);

        tv_tenKH = (TextView)itemView.findViewById(R.id.tv_tenKH);
        tv_sdtKH = (TextView)itemView.findViewById(R.id.tv_sdtKH);
        tv_diachiKH = (TextView)itemView.findViewById(R.id.tv_diachiKH);
        btn_Goi = (ImageView)itemView.findViewById(R.id.btn_goi);
        btn_Xoa = (ImageView)itemView.findViewById(R.id.btn_xoa);
    }
}

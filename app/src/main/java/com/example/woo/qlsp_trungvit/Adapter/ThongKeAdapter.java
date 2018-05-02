package com.example.woo.qlsp_trungvit.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.woo.qlsp_trungvit.Interface.IThongKe;
import com.example.woo.qlsp_trungvit.R;

import java.util.ArrayList;

public class ThongKeAdapter extends RecyclerView.Adapter<ThongKeAdapter.ThongKeViewHolder> {

    Context context;
    ArrayList<String> listThongKes;
    IThongKe iThongKe;

    public ThongKeAdapter(Context context, ArrayList<String> listThongKes, IThongKe iThongKe) {
        this.context = context;
        this.listThongKes = listThongKes;
        this.iThongKe = iThongKe;
    }

    @Override
    public ThongKeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View timeView = LayoutInflater.from(context).inflate(R.layout.item_time, parent, false);
        return new ThongKeViewHolder(timeView);
    }

    @Override
    public void onBindViewHolder(ThongKeViewHolder holder, int position) {
        holder.tv_thoiGianTK.setText("THÁNG "+listThongKes.get(position));
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iThongKe.ClickItemThongKe(Integer.parseInt(view.getTag().toString()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listThongKes.size();
    }

    public class ThongKeViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_thoiGianTK;

        public ThongKeViewHolder(View itemView) {
            super(itemView);
            tv_thoiGianTK = itemView.findViewById(R.id.tv_thoiGianTK);
        }
    }
}

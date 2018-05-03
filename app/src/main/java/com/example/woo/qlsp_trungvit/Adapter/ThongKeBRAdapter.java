package com.example.woo.qlsp_trungvit.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.woo.qlsp_trungvit.Interface.IThongKeBR;
import com.example.woo.qlsp_trungvit.R;

import java.util.ArrayList;

public class ThongKeBRAdapter extends RecyclerView.Adapter<ThongKeBRAdapter.ThongKeBRViewHolder> {

    Context context;
    ArrayList<String> listThongKes;
    IThongKeBR iThongKeBR;

    public ThongKeBRAdapter(Context context, ArrayList<String> listThongKes, IThongKeBR iThongKeBR) {
        this.context = context;
        this.listThongKes = listThongKes;
        this.iThongKeBR = iThongKeBR;
    }

    @Override
    public ThongKeBRViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View timeView = LayoutInflater.from(context).inflate(R.layout.item_time, parent, false);
        return new ThongKeBRViewHolder(timeView);
    }

    @Override
    public void onBindViewHolder(ThongKeBRViewHolder holder, int position) {
        holder.tv_thoiGianTK.setText(listThongKes.get(position));
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iThongKeBR.ClickItemThongKeBR(Integer.parseInt(view.getTag().toString()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listThongKes.size();
    }

    public class ThongKeBRViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_thoiGianTK;

        public ThongKeBRViewHolder(View itemView) {
            super(itemView);
            tv_thoiGianTK = itemView.findViewById(R.id.tv_thoiGianTK);
        }
    }
}

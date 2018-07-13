package com.example.minhquan.besttrip.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minhquan.besttrip.R;
import com.example.minhquan.besttrip.model.datafirebase.Company;

import java.util.ArrayList;
import java.util.List;

public class TaxiAdapter extends RecyclerView.Adapter<TaxiAdapter.ViewHolder> {
    List<Company> data;
    Company company;
    Context context;

    public TaxiAdapter(Context context) {

        data = new ArrayList<>();
        this.context = context;
    }

    public void setData(List<Company> data) {
        this.data = data;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.taxi, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        company = data.get(position);
        //holder.imageAutoMakers.setImageResource(R.drawable.grab_logo);
        holder.tvAutoMakers.setText(company.getName());
        holder.tvPrice.setText(company.getListUser().get(0).getPrice());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageAutoMakers;
        TextView tvAutoMakers, tvPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            imageAutoMakers = itemView.findViewById(R.id.imgAutoMakers);
            tvAutoMakers = itemView.findViewById(R.id.tvAutoMakers);
            tvPrice = itemView.findViewById(R.id.tvPrice);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            Toast.makeText(context, "Clicked!!!", Toast.LENGTH_SHORT).show();
        }
    }
}

package com.example.minhquan.besttrip.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.minhquan.besttrip.R;

public class TaxiAdapter extends RecyclerView.Adapter<TaxiAdapter.ViewHolder> {

    Context context;

    public TaxiAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.taxi, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //holder.imageAutoMakers.setImageResource(R.drawable.grab_logo);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageAutoMakers;

        public ViewHolder(View itemView) {
            super(itemView);
            imageAutoMakers = itemView.findViewById(R.id.imgAutoMakers);

        }
    }
}

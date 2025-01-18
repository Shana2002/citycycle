package com.example.citycycle.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.citycycle.R;
import com.example.citycycle.helpers.OnCycleClickListener;
import com.example.citycycle.models.Cycle;
import com.example.citycycle.models.Promotion;

import java.util.List;

public class CycleAdapter extends RecyclerView.Adapter<CycleAdapter.ViewHolderCycle> {

    // variables
    List<Cycle> cycles ;
    Context context;
    OnCycleClickListener listener;

    public CycleAdapter(List<Cycle> cycles, Context context, OnCycleClickListener listener){
        this.cycles = cycles;
        this.context = context;
        this.listener = listener;
    };

    @NonNull
    @Override
    public ViewHolderCycle onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_cycle, parent, false);
        return new ViewHolderCycle(view); // Return a new ViewHolder instance
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCycle holder, int position) {
        Log.d("test","hello11111dsdds");
        Cycle cycle = cycles.get(position);
        holder.title.setText(cycle.title);
        holder.location.setText(cycle.station);

        holder.itemView.setOnClickListener(v -> listener.onCycleClick(cycle));
    }

    @Override
    public int getItemCount() {
        return cycles.size();
    }


    public static class ViewHolderCycle extends RecyclerView.ViewHolder {
        TextView title,location;
        ImageView imageView;

        public ViewHolderCycle(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            location = itemView.findViewById(R.id.location);
//            imageView = itemView.findViewById(R.id.imageView);
        }


    }
}

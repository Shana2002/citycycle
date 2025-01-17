package com.example.citycycle.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.citycycle.R;
import com.example.citycycle.models.Promotion;

import java.util.List;

public class PromotionViewAdapterHorizontal extends RecyclerView.Adapter<PromotionViewAdapterHorizontal.ViewHolder> {

    private List<Promotion> promotions;
    private Context context;

    public PromotionViewAdapterHorizontal(List<Promotion> promotions,Context context){
        this.promotions = promotions;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_promotion, parent, false);
        return new ViewHolder(view); // Return a new ViewHolder instance
    }

    // Bind data to views in the ViewHolder (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Promotion promotion = promotions.get(position);

        holder.title.setText(promotion.title);
        holder.description.setText(promotion.description);
        holder.imageView.setImageResource(promotion.resourceFile(context));
    }

    @Override
    public int getItemCount() {
        return promotions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,description;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            imageView = itemView.findViewById(R.id.promo_image);

        }
    }
}

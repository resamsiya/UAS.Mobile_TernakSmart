package com.example.ternaksmart;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<TernakData> ternakDataList;

    public HistoryAdapter(List<TernakData> ternakDataList) {
        this.ternakDataList = ternakDataList;
    }

    public void setData(List<TernakData> newData) {
        this.ternakDataList = newData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TernakData data = ternakDataList.get(position);
        holder.tvName.setText(data.getNama());
        holder.tvWeight.setText(String.format(Locale.getDefault(), "%.2f Kg", data.getBerat()));

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        String dateStr = sdf.format(new Date(data.getTimestamp()));
        holder.tvDate.setText(dateStr);

        if (data.getPhotoPath() != null && !data.getPhotoPath().isEmpty()) {
            File imgFile = new File(data.getPhotoPath());
            if (imgFile.exists()) {
                holder.ivTernakPhoto.setImageURI(Uri.fromFile(imgFile));
            } else {
                holder.ivTernakPhoto.setImageResource(R.drawable.ic_app_logo);
            }
        } else {
            holder.ivTernakPhoto.setImageResource(R.drawable.ic_app_logo);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), DetailHistoryActivity.class);
            intent.putExtra("ternak_data", data);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return ternakDataList != null ? ternakDataList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvWeight, tvDate;
        ImageView ivTernakPhoto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvHistoryName);
            tvWeight = itemView.findViewById(R.id.tvHistoryWeight);
            tvDate = itemView.findViewById(R.id.tvHistoryDate);
            ivTernakPhoto = itemView.findViewById(R.id.ivTernakPhoto);
        }
    }
}

package com.example.ternaksmart;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.chip.Chip;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetailHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_history);

        TernakData data = (TernakData) getIntent().getSerializableExtra("ternak_data");

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(v -> finish());
        }

        if (data != null) {
            ImageView ivPhoto = findViewById(R.id.ivDetailPhoto);
            TextView tvName = findViewById(R.id.tvDetailName);
            TextView tvWeight = findViewById(R.id.tvDetailWeight);
            TextView tvDate = findViewById(R.id.tvDetailDate);
            TextView tvLocation = findViewById(R.id.tvDetailLocation);
            Chip chipStatus = findViewById(R.id.chipDetailStatus);

            tvName.setText(data.getNama());
            tvWeight.setText(String.format("%.2f Kg", data.getBerat()));
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault());
            tvDate.setText(sdf.format(new Date(data.getTimestamp())));
            
            tvLocation.setText(String.format(Locale.getDefault(), "Lat: %.5f, Long: %.5f", 
                    data.getLatitude(), data.getLongitude()));

            chipStatus.setText(data.getStatus() != null ? data.getStatus() : "Sehat");

            if (data.getPhotoPath() != null && !data.getPhotoPath().isEmpty()) {
                Bitmap bitmap = BitmapFactory.decodeFile(data.getPhotoPath());
                if (bitmap != null) {
                    ivPhoto.setImageBitmap(bitmap);
                }
            }
        }
    }
}
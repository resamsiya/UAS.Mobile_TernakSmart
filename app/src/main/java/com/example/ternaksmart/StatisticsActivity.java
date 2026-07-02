package com.example.ternaksmart;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.appbar.MaterialToolbar;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executors;

public class StatisticsActivity extends AppCompatActivity {

    private TextView tvTotalTernak, tvSehat, tvSakit;
    private LineChartView lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(v -> finish());
        }

        tvTotalTernak = findViewById(R.id.tvTotalTernak);
        tvSehat = findViewById(R.id.tvSehat);
        tvSakit = findViewById(R.id.tvSakit);
        lineChart = findViewById(R.id.lineChart);

        loadStatistics();
    }

    private void loadStatistics() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<TernakData> allData = AppDatabase.getInstance(this).ternakDao().getAllData();
            
            int total = allData.size();
            int sehat = 0;
            int sakit = 0;

            // Urutkan data berdasarkan waktu untuk grafik
            List<TernakData> sortedData = new ArrayList<>(allData);
            Collections.sort(sortedData, (o1, o2) -> Long.compare(o1.getTimestamp(), o2.getTimestamp()));

            List<Double> weights = new ArrayList<>();
            for (TernakData data : allData) {
                if ("Sehat".equalsIgnoreCase(data.getStatus())) {
                    sehat++;
                } else if ("Sakit".equalsIgnoreCase(data.getStatus())) {
                    sakit++;
                }
            }

            for (TernakData data : sortedData) {
                weights.add(data.getBerat());
            }

            final int finalTotal = total;
            final int finalSehat = sehat;
            final int finalSakit = sakit;

            runOnUiThread(() -> {
                tvTotalTernak.setText(String.valueOf(finalTotal));
                tvSehat.setText(String.valueOf(finalSehat));
                tvSakit.setText(String.valueOf(finalSakit));
                lineChart.setData(weights);
            });
        });
    }
}
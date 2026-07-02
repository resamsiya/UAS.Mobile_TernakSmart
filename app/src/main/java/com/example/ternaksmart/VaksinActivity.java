package com.example.ternaksmart;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.Executors;

public class VaksinActivity extends AppCompatActivity {

    private RecyclerView rvVaksin;
    private VaksinAdapter adapter;
    private List<Vaksin> vaksinList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaksin);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
            toolbar.setNavigationOnClickListener(v -> finish());
        }

        // Animasi Masuk
        findViewById(android.R.id.content).startAnimation(
                android.view.animation.AnimationUtils.loadAnimation(this, R.anim.fade_in_up)
        );

        rvVaksin = findViewById(R.id.rvVaksin);
        rvVaksin.setLayoutManager(new LinearLayoutManager(this));

        adapter = new VaksinAdapter(vaksinList);
        rvVaksin.setAdapter(adapter);

        loadData();

        MaterialButton btnSimpan = findViewById(R.id.btnSimpanJadwal);
        btnSimpan.setOnClickListener(v -> {
            saveVaksinProgress();
        });
    }

    private void loadData() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Vaksin> savedList = AppDatabase.getInstance(this).vaksinDao().getVaksinByType("JADWAL");
            if (savedList.isEmpty()) {
                prepareDefaultData();
                AppDatabase.getInstance(this).vaksinDao().insertAll(vaksinList);
            } else {
                vaksinList.clear();
                vaksinList.addAll(savedList);
            }
            runOnUiThread(() -> adapter.notifyDataSetChanged());
        });
    }

    private void saveVaksinProgress() {
        Executors.newSingleThreadExecutor().execute(() -> {
            for (Vaksin v : vaksinList) {
                AppDatabase.getInstance(this).vaksinDao().update(v);
            }
            
            int completed = AppDatabase.getInstance(this).vaksinDao().getCompletedVaksinCount();
            int total = AppDatabase.getInstance(this).vaksinDao().getTotalVaksinCount();

            SharedPreferences sharedPref = getSharedPreferences("TernakData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("VAKSIN_SELESAI", completed);
            editor.putInt("TOTAL_VAKSIN", total);
            editor.apply();

            runOnUiThread(() -> {
                Toast.makeText(this, "Data Jadwal Vaksin Berhasil Disimpan!", Toast.LENGTH_SHORT).show();
                finish();
            });
        });
    }

    private void prepareDefaultData() {
        vaksinList.clear();
        vaksinList.add(new Vaksin(getString(R.string.vax_nd_ib_tetes), 4, false, "JADWAL"));
        vaksinList.add(new Vaksin(getString(R.string.vax_gumboro_1), 7, false, "JADWAL"));
        vaksinList.add(new Vaksin(getString(R.string.vax_nd_ib_vax), 10, false, "JADWAL"));
        vaksinList.add(new Vaksin(getString(R.string.vax_ai_h5n1), 14, false, "JADWAL"));
        vaksinList.add(new Vaksin(getString(R.string.vax_gumboro_2), 18, false, "JADWAL"));
        vaksinList.add(new Vaksin(getString(R.string.vax_nd_lasota), 28, false, "JADWAL"));
    }
}
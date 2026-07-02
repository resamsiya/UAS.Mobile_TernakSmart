package com.example.ternaksmart;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "vaksin")
public class Vaksin {
    @PrimaryKey(autoGenerate = true)
    private int id;
    
    private String nama;
    private int targetHari;
    private boolean isDone;
    private String type; // "JADWAL" or "STOK"

    public Vaksin(String nama, int targetHari, boolean isDone, String type) {
        this.nama = nama;
        this.targetHari = targetHari;
        this.isDone = isDone;
        this.type = type;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    
    public int getTargetHari() { return targetHari; }
    public void setTargetHari(int targetHari) { this.targetHari = targetHari; }
    
    public String getTargetUmurFormatted() { 
        if ("STOK".equals(type)) return "Tersedia";
        return "Hari ke-" + targetHari; 
    }

    public boolean isDone() { return isDone; }
    public void setDone(boolean done) { isDone = done; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}
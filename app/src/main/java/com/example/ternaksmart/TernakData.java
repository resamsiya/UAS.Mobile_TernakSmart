package com.example.ternaksmart;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(tableName = "ternak_data")
public class TernakData implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    
    private String nama;
    private String jenis; // Kambing, Domba, Sapi, Ayam, dll.
    private double berat;
    private String photoPath;
    private double latitude;
    private double longitude;
    private long timestamp;
    private String status; // "Sehat" atau "Sakit"

    public TernakData(String nama, String jenis, double berat, String photoPath, double latitude, double longitude, long timestamp, String status) {
        this.nama = nama;
        this.jenis = jenis;
        this.berat = berat;
        this.photoPath = photoPath;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
        this.status = status;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getJenis() { return jenis; }
    public void setJenis(String jenis) { this.jenis = jenis; }

    public double getBerat() { return berat; }
    public void setBerat(double berat) { this.berat = berat; }

    public String getPhotoPath() { return photoPath; }
    public void setPhotoPath(String photoPath) { this.photoPath = photoPath; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
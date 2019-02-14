package com.example.learningapi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Biodata {
    @Expose
    @SerializedName("id") private  int id;
    @Expose
    @SerializedName("nama") private  String nama;
    @Expose
    @SerializedName("alamat") private  String alamat;
    @Expose
    @SerializedName("success") private  Boolean success;
    @Expose
    @SerializedName("message") private  String message;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }


}

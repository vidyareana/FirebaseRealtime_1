package com.example.firebaserealtime_1;

public class Biodata {
    private String biodata_Id;
    private String biodata_Nama;
    private String biodata_Umur;
    private String biodata_JK;
    public Biodata(){

    }
    public Biodata (String bioId,String bioNama,String bioUmur, String bioJK){
        this.biodata_Id = bioId;
        this.biodata_Nama = bioNama;
        this.biodata_Umur = bioUmur;
        this.biodata_JK = bioJK;
    }

    public String getBiodata_Id() {
        return biodata_Id;
    }

    public String getBiodata_Nama() {
        return biodata_Nama;
    }

    public String getBiodata_Umur() {
        return biodata_Umur;
    }

    public String getBiodata_JK() {
        return biodata_JK;
    }
}


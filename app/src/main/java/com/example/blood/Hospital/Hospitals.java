package com.example.blood.Hospital;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Hospitals {
    private int current_page;
    private int per_page;
    private int total;
    private List<datum> data;

    public Hospitals() {
    }

    public Hospitals(int current_page, int per_page, int total, List<datum> data) {
        this.current_page = current_page;
        this.per_page = per_page;
        this.total = total;
        this.data = data;
    }

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<datum> getData() {
        return data;
    }

    public void setData(List<datum> data) {
        this.data = data;
    }
}

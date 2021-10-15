package com.example.blood.Hospital;

public class Districtnew {
    public String label;
    public String value;
    public int id;

    public Districtnew() {
    }

    public Districtnew(String label, String value, int id) {
        this.label = label;
        this.value = value;
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

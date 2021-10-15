package com.example.blood;

public class Division {
    private String _id;
    private int DivisionCode;
    private String Division;


    // Getter Methods


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getDivisionCode() {
        return DivisionCode;
    }

    public String getDivision() {
        return Division;
    }

    // Setter Methods

    public void setDivisionCode(int DivisionCode) {
        this.DivisionCode = DivisionCode;
    }

    public void setDivision(String Division) {
        this.Division = Division;
    }
}
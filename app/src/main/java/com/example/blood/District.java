package com.example.blood;

public class District {
    private String _id;
    private String DivCode;
    private String District;
    private String DistrictCode;
    private String lat;
    private String longitude;


    // Getter Methods

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getDivCode() {
        return DivCode;
    }

    public String getDistrict() {
        return District;
    }

    public String getDistrictCode() {
        return DistrictCode;
    }

    public String getLat() {
        return lat;
    }

    public String getLongitude() {
        return longitude;
    }

    // Setter Methods

    public void setDivCode(String DivCode) {
        this.DivCode = DivCode;
    }

    public void setDistrict(String District) {
        this.District = District;
    }

    public void setDistrictCode(String DistrictCode) {
        this.DistrictCode = DistrictCode;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
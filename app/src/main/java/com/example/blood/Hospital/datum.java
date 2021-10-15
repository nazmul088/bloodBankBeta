package com.example.blood.Hospital;

import java.io.Serializable;
import java.util.Date;

public class datum implements Serializable {

    private int id;
    private String name;
    private String district;
    private int general_beds;
    private int general_beds_occupied;
    private int icu_beds;
    private int icu_beds_occupied;
    private int hfn_beds;
    private int hfn_beds_occupied;
    private int hdu_beds;
    private int hdu_beds_occupied;
    private int general_beds_available;
    private int icu_beds_available;
    private int hfn_beds_available;
    private int hdu_beds_available;
    private String update_by;
    private String address;
    private double rating;
    private String google_place_id;
    private String phone_number;
    private String website;
    private String latitude;
    private String longitude;
    private String dghs_update;
    private String created_at;
    private String updated_at;
    private String distance;
    private int total_beds;
    private int total_occupied_beds;
    private int total_available_beds;
    private int oxygen_total_supply_point;
    private int oxygen_total_concentrator;
    private int oxygen_total_cylinder;
    private int oxygen_total_hfnc;
    private int oxygen_used_hfnc;


    public datum() {
    }

    public datum(int id, String name, String district, int general_beds, int general_beds_occupied, int icu_beds, int icu_beds_occupied, int hfn_beds, int hfn_beds_occupied, int hdu_beds, int hdu_beds_occupied, int general_beds_available, int icu_beds_available, int hfn_beds_available, int hdu_beds_available, String update_by, String address, double rating, String google_place_id, String phone_number, String website, String latitude, String longitude, String dghs_update, String created_at, String updated_at, String distance, int total_beds, int total_occupied_beds, int total_available_beds, int oxygen_total_supply_point, int oxygen_total_concentrator, int oxygen_total_cylinder, int oxygen_total_hfnc, int oxygen_used_hfnc) {
        this.id = id;
        this.name = name;
        this.district = district;
        this.general_beds = general_beds;
        this.general_beds_occupied = general_beds_occupied;
        this.icu_beds = icu_beds;
        this.icu_beds_occupied = icu_beds_occupied;
        this.hfn_beds = hfn_beds;
        this.hfn_beds_occupied = hfn_beds_occupied;
        this.hdu_beds = hdu_beds;
        this.hdu_beds_occupied = hdu_beds_occupied;
        this.general_beds_available = general_beds_available;
        this.icu_beds_available = icu_beds_available;
        this.hfn_beds_available = hfn_beds_available;
        this.hdu_beds_available = hdu_beds_available;
        this.update_by = update_by;
        this.address = address;
        this.rating = rating;
        this.google_place_id = google_place_id;
        this.phone_number = phone_number;
        this.website = website;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dghs_update = dghs_update;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.distance = distance;
        this.total_beds = total_beds;
        this.total_occupied_beds = total_occupied_beds;
        this.total_available_beds = total_available_beds;
        this.oxygen_total_supply_point = oxygen_total_supply_point;
        this.oxygen_total_concentrator = oxygen_total_concentrator;
        this.oxygen_total_cylinder = oxygen_total_cylinder;
        this.oxygen_total_hfnc = oxygen_total_hfnc;
        this.oxygen_used_hfnc = oxygen_used_hfnc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getGeneral_beds() {
        return general_beds;
    }

    public void setGeneral_beds(int general_beds) {
        this.general_beds = general_beds;
    }

    public int getGeneral_beds_occupied() {
        return general_beds_occupied;
    }

    public void setGeneral_beds_occupied(int general_beds_occupied) {
        this.general_beds_occupied = general_beds_occupied;
    }

    public int getIcu_beds() {
        return icu_beds;
    }

    public void setIcu_beds(int icu_beds) {
        this.icu_beds = icu_beds;
    }

    public int getIcu_beds_occupied() {
        return icu_beds_occupied;
    }

    public void setIcu_beds_occupied(int icu_beds_occupied) {
        this.icu_beds_occupied = icu_beds_occupied;
    }

    public int getHfn_beds() {
        return hfn_beds;
    }

    public void setHfn_beds(int hfn_beds) {
        this.hfn_beds = hfn_beds;
    }

    public int getHfn_beds_occupied() {
        return hfn_beds_occupied;
    }

    public void setHfn_beds_occupied(int hfn_beds_occupied) {
        this.hfn_beds_occupied = hfn_beds_occupied;
    }

    public int getHdu_beds() {
        return hdu_beds;
    }

    public void setHdu_beds(int hdu_beds) {
        this.hdu_beds = hdu_beds;
    }

    public int getHdu_beds_occupied() {
        return hdu_beds_occupied;
    }

    public void setHdu_beds_occupied(int hdu_beds_occupied) {
        this.hdu_beds_occupied = hdu_beds_occupied;
    }

    public int getGeneral_beds_available() {
        return general_beds_available;
    }

    public void setGeneral_beds_available(int general_beds_available) {
        this.general_beds_available = general_beds_available;
    }

    public int getIcu_beds_available() {
        return icu_beds_available;
    }

    public void setIcu_beds_available(int icu_beds_available) {
        this.icu_beds_available = icu_beds_available;
    }

    public int getHfn_beds_available() {
        return hfn_beds_available;
    }

    public void setHfn_beds_available(int hfn_beds_available) {
        this.hfn_beds_available = hfn_beds_available;
    }

    public int getHdu_beds_available() {
        return hdu_beds_available;
    }

    public void setHdu_beds_available(int hdu_beds_available) {
        this.hdu_beds_available = hdu_beds_available;
    }

    public String getUpdate_by() {
        return update_by;
    }

    public void setUpdate_by(String update_by) {
        this.update_by = update_by;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getGoogle_place_id() {
        return google_place_id;
    }

    public void setGoogle_place_id(String google_place_id) {
        this.google_place_id = google_place_id;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public Object getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDghs_update() {
        return dghs_update;
    }

    public void setDghs_update(String dghs_update) {
        this.dghs_update = dghs_update;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public Object getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public int getTotal_beds() {
        return total_beds;
    }

    public void setTotal_beds(int total_beds) {
        this.total_beds = total_beds;
    }

    public int getTotal_occupied_beds() {
        return total_occupied_beds;
    }

    public void setTotal_occupied_beds(int total_occupied_beds) {
        this.total_occupied_beds = total_occupied_beds;
    }

    public int getTotal_available_beds() {
        return total_available_beds;
    }

    public void setTotal_available_beds(int total_available_beds) {
        this.total_available_beds = total_available_beds;
    }

    public int getOxygen_total_supply_point() {
        return oxygen_total_supply_point;
    }

    public void setOxygen_total_supply_point(int oxygen_total_supply_point) {
        this.oxygen_total_supply_point = oxygen_total_supply_point;
    }

    public int getOxygen_total_concentrator() {
        return oxygen_total_concentrator;
    }

    public void setOxygen_total_concentrator(int oxygen_total_concentrator) {
        this.oxygen_total_concentrator = oxygen_total_concentrator;
    }

    public int getOxygen_total_cylinder() {
        return oxygen_total_cylinder;
    }

    public void setOxygen_total_cylinder(int oxygen_total_cylinder) {
        this.oxygen_total_cylinder = oxygen_total_cylinder;
    }

    public int getOxygen_total_hfnc() {
        return oxygen_total_hfnc;
    }

    public void setOxygen_total_hfnc(int oxygen_total_hfnc) {
        this.oxygen_total_hfnc = oxygen_total_hfnc;
    }

    public int getOxygen_used_hfnc() {
        return oxygen_used_hfnc;
    }

    public void setOxygen_used_hfnc(int oxygen_used_hfnc) {
        this.oxygen_used_hfnc = oxygen_used_hfnc;
    }
}

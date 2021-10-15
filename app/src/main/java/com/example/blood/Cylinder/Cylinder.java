package com.example.blood.Cylinder;

import com.example.blood.District;
import com.example.blood.Division;
import com.example.blood.Upazilla;

public class Cylinder {
    private String _id;
    private String organizationName;
    private String contactNo;
    private String remarks;
    private Division division;
    private District district;
    private Upazilla upazilla;

    public Cylinder() {
    }

    public Cylinder(String _id, String organizationName, String contactNo, String remarks, Division division, District district, Upazilla upazilla) {
        this._id = _id;
        this.organizationName = organizationName;
        this.contactNo = contactNo;
        this.remarks = remarks;
        this.division = division;
        this.district = district;
        this.upazilla = upazilla;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public Upazilla getUpazilla() {
        return upazilla;
    }

    public void setUpazilla(Upazilla upazilla) {
        this.upazilla = upazilla;
    }
}

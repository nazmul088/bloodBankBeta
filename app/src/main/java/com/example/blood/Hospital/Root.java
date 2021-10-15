package com.example.blood.Hospital;

import java.util.List;

public class Root{
    private Hospitals hospitals;
    private List<Districtnew> districts;

    public Root() {
    }

    public Root(Hospitals hospitals, List<Districtnew> districts) {
        this.hospitals = hospitals;
        this.districts = districts;
    }

    public Hospitals getHospitals() {
        return hospitals;
    }

    public void setHospitals(Hospitals hospitals) {
        this.hospitals = hospitals;
    }

    public List<Districtnew> getDistricts() {
        return districts;
    }

    public void setDistricts(List<Districtnew> districts) {
        this.districts = districts;
    }
}

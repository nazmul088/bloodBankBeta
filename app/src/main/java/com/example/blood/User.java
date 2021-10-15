package com.example.blood;

import androidx.annotation.NonNull;

public class User {
    private String _id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String gender;
    private String DOB;
    private String lastDonationDate;
    private String lastActiveAt;
    private String lastUpdateAt;
    private String bloodGroup;

    private String remarks;
    private String division;
    private String district;
    private String upazilla;

    private String mobile1;
    private String mobile2;
   // private boolean currentStatus;
    private String NID;
    private String smartCard;
    private String BirthCertificate;


    public User() {
    }


    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
    //using all attributes
    public User(String _id, String firstName, String lastName, String email, String password, String gender, String DOB, String lastDonationDate, String lastActiveAt, String lastUpdateAt, String bloodGroup, String remarks, String division, String district, String upazilla, String mobile1, String mobile2, String NID, String smartCard, String birthCertificate) {
        this._id = _id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.DOB = DOB;
        this.lastDonationDate = lastDonationDate;
        this.lastActiveAt = lastActiveAt;
        this.lastUpdateAt = lastUpdateAt;
        this.bloodGroup = bloodGroup;
        this.remarks = remarks;
        this.division = division;
        this.district = district;
        this.upazilla = upazilla;
        this.mobile1 = mobile1;
        this.mobile2 = mobile2;
        this.NID = NID;
        this.smartCard = smartCard;
        BirthCertificate = birthCertificate;
    }

    //for Sign Up
    public User(String firstName, String lastName, String email, String password, String gender, String DOB, String lastActiveAt, String lastUpdateAt, String bloodGroup, String division, String district, String upazilla, String mobile1) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.DOB = DOB;
        this.lastActiveAt = lastActiveAt;
        this.lastUpdateAt = lastUpdateAt;
        this.bloodGroup = bloodGroup;
        this.division = division;
        this.district = district;
        this.upazilla = upazilla;
        this.mobile1 = mobile1;
    }


    //This constructor is used in log in
    /*public User(String _id, String firstName, String lastName, String email, String password, String gender, String DOB, String lastDonationDate, String lastActiveAt, String lastUpdateAt, String bloodGroup, String remarks, String division, String district, String upazilla, String mobile1, String NID) {
        this._id = _id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.DOB = DOB;
        this.lastDonationDate = lastDonationDate;
        this.lastActiveAt = lastActiveAt;
        this.lastUpdateAt = lastUpdateAt;
        this.bloodGroup = bloodGroup;
        this.remarks = remarks;
        this.division = division;
        this.district = district;
        this.upazilla = upazilla;
        this.mobile1 = mobile1;
        this.NID = NID;
    }*/

    //This is also used for sign in(If smart card is used)
    public User(String _id,String firstName, String lastName, String email, String password, String gender, String DOB, String lastDonationDate, String lastActiveAt, String lastUpdateAt, String bloodGroup, String remarks, String division, String district, String upazilla, String mobile1,String smartCard) {
        this._id = _id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.DOB = DOB;
        this.lastDonationDate = lastDonationDate;
        this.lastActiveAt = lastActiveAt;
        this.lastUpdateAt = lastUpdateAt;
        this.bloodGroup = bloodGroup;
        this.remarks = remarks;
        this.division = division;
        this.district = district;
        this.upazilla = upazilla;
        this.mobile1 = mobile1;
        this.smartCard = smartCard;
    }







    //For edit profile
    public User(String firstName, String lastName, String gender, String dateOfBirth, String bloodGroup, String remarks, String division, String district, String upazilla, String mobileNo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.DOB = dateOfBirth;
        this.bloodGroup = bloodGroup;
        this.remarks = remarks;
        this.division = division;
        this.district = district;
        this.upazilla = upazilla;
        this.mobile2 = mobileNo;
    }

    public User(String firstName, String lastName, String gender, String dateOfBirth, String bloodGroup, String remarks) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.DOB = dateOfBirth;
        this.bloodGroup = bloodGroup;
        this.remarks = remarks;
    }


    // Getter Methods


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUpazilla() {
        return upazilla;
    }

    public void setUpazilla(String upazilla) {
        this.upazilla = upazilla;
    }



    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getGender() {
        return gender;
    }

    public String getDOB() {
        return DOB;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getDivision() {
        return division;
    }

    public String getDistrict() {
        return district;
    }

    public String getMobile1() {
        return mobile1;
    }


    public String getNID() {
        return NID;
    }

    // Setter Methods

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setMobile1(String mobile1) {
        this.mobile1 = mobile1;
    }


    public void setNID(String NID) {
        this.NID = NID;
    }

    public String getLastDonationDate() {
        return lastDonationDate;
    }

    public void setLastDonationDate(String lastDonationDate) {
        this.lastDonationDate = lastDonationDate;
    }

    public String getLastActiveAt() {
        return lastActiveAt;
    }

    public void setLastActiveAt(String lastActiveAt) {
        this.lastActiveAt = lastActiveAt;
    }

    public String getLastUpdateAt() {
        return lastUpdateAt;
    }

    public void setLastUpdateAt(String lastUpdateAt) {
        this.lastUpdateAt = lastUpdateAt;
    }

    public String getMobile2() {
        return mobile2;
    }

    public void setMobile2(String mobile2) {
        this.mobile2 = mobile2;
    }

    public String getSmartCard() {
        return smartCard;
    }

    public void setSmartCard(String smartCard) {
        this.smartCard = smartCard;
    }

    public String getBirthCertificate() {
        return BirthCertificate;
    }

    public void setBirthCertificate(String birthCertificate) {
        this.BirthCertificate = birthCertificate;
    }

    @NonNull
    @Override
    public String toString() {
        System.out.println(firstName +" "+ lastName +" " +email+" "+  password+" "+ gender+" " +DOB+ " "+lastDonationDate+" "+lastActiveAt+" "+lastUpdateAt+" "+
                bloodGroup+" "+ remarks+" "+ division+" "+district+" "+upazilla+" "+ mobile1+" "+NID);
        return super.toString();
    }
}

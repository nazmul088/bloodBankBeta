package com.example.blood;

public class UserSearch {
    private boolean isAdmin;
    private String _id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String gender;
    private String DOB;
    private String lastActiveAt;
    private String lastUpdateAt;
    private String lastDonationDate;
    private String bloodGroup;
    private String remarks;
    private Division division;
    private District district;
    private Upazilla upazilla;
    private String mobile1;
    private String mobile2;
    private boolean currentStatus;
    private String NID;
    private String smartCard;
    private String BirthCertificate;

    public UserSearch() {
    }

    public UserSearch(boolean isAdmin, String _id, String firstName, String lastName, String email, String password, String gender, String DOB, String lastActiveAt, String lastUpdateAt,String lastDonationDate ,String bloodGroup, String remarks, Division division, District district,Upazilla upazilla ,String mobile1,String mobile2,boolean currentStatus) {
        this.isAdmin = isAdmin;
        this._id = _id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.DOB = DOB;
        this.lastActiveAt = lastActiveAt;
        this.lastUpdateAt = lastUpdateAt;
        this.lastDonationDate = lastDonationDate;
        this.bloodGroup = bloodGroup;
        this.remarks = remarks;
        this.division = division;
        this.district = district;
        this.upazilla = upazilla;
        this.mobile1 = mobile1;
        this.mobile2 = mobile2;
        this.currentStatus = currentStatus;
    }

    public UserSearch(boolean isAdmin, String _id, String firstName, String lastName, String email, String password, String gender, String DOB, String lastActiveAt, String lastUpdateAt, String bloodGroup, String remarks, Division division, District district, String mobile1, boolean currentStatus, String NID, String smartCard, String birthCertificate) {
        this.isAdmin = isAdmin;
        this._id = _id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.DOB = DOB;
        this.lastActiveAt = lastActiveAt;
        this.lastUpdateAt = lastUpdateAt;
        this.bloodGroup = bloodGroup;
        this.remarks = remarks;
        this.division = division;
        this.district = district;
        this.mobile1 = mobile1;
        this.currentStatus = currentStatus;
        this.NID = NID;
        this.smartCard = smartCard;
        BirthCertificate = birthCertificate;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
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

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
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

    public String getMobile1() {
        return mobile1;
    }

    public void setMobile1(String mobile1) {
        this.mobile1 = mobile1;
    }


    public boolean isCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(boolean currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getLastDonationDate() {
        return lastDonationDate;
    }

    public void setLastDonationDate(String lastDonationDate) {
        this.lastDonationDate = lastDonationDate;
    }

    public String getNID() {
        return NID;
    }

    public void setNID(String NID) {
        this.NID = NID;
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
        BirthCertificate = birthCertificate;
    }

    public Upazilla getUpazilla() {
        return upazilla;
    }

    public void setUpazilla(Upazilla upazilla) {
        this.upazilla = upazilla;
    }

    public String getMobile2() {
        return mobile2;
    }

    public void setMobile2(String mobile2) {
        this.mobile2 = mobile2;
    }


}

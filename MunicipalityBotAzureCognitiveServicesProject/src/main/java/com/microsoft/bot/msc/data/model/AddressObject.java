package com.microsoft.bot.msc.data.model;


public class AddressObject {



    private String StreetAddress;
    private String City;
    private String CityEn;
    private String PostalCode;
    private String Country;


    public String getStreetAddress() {
        return StreetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        StreetAddress = streetAddress;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getCityEn() {
        return CityEn;
    }

    public void setCityEn(String cityEn) {
        CityEn = cityEn;
    }

    public String getPostalCode() {
        return PostalCode;
    }

    public void setPostalCode(String postalCode) {
        PostalCode = postalCode;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }
}
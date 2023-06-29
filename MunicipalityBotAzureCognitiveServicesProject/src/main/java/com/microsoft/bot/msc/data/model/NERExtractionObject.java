package com.microsoft.bot.msc.data.model;


public class NERExtractionObject {

        private String name;

        private String type;

        private String cuisine;

        private String location;

        private String priceRange;

        private String rating;

        private String product;

        private String amentities;



        public NERExtractionObject() {
            super();
        }


    public NERExtractionObject(String name, String type, String Location, String cuisine, String rating, String priceRange) {
        super();
        this.name = name;
        this.type = type;
        this.location = location;
        this.cuisine = cuisine;
        this.rating = rating;
        this.priceRange = priceRange;
    }

    public static NERExtractionObject NERExtractionObjectDepartment() {
        NERExtractionObject nerObject = new NERExtractionObject();
        nerObject.setName("Revenue");
        nerObject.setType("department");
        return  nerObject;
    }

    public static NERExtractionObject NERExtractionObjectRestaurant() {
        NERExtractionObject nerObject = new NERExtractionObject();
        nerObject.setType("restaurant");
        nerObject.setCuisine("italian");
        nerObject.setLocation("corinth");
        nerObject.setPriceRange("cheap");
        return  nerObject;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(String priceRange) {
        this.priceRange = priceRange;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getAmentities() {
        return amentities;
    }

    public void setAmentities(String amentities) {
        this.amentities = amentities;
    }
}
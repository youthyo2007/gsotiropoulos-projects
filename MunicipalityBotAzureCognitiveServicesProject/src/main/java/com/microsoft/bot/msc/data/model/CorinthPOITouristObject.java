package com.microsoft.bot.msc.data.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("CorinthPOITourist")
public class CorinthPOITouristObject {

        @Id
        private String id;
        private String Name;


        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }


        private String NameGr;

        public String getNameGr() {
            return NameGr;
        }

        public void setNameGr(String nameGr) {
            NameGr = nameGr;
        }


        private String Type;

        public String getType() {
            return Type;
        }

        public void setType(String type) {
            Type = type;
        }


        private String Cuisine;


        private String Amenities;


        private ContactObject Contact;

        private AddressObject Address;

        public ContactObject getContact() {
            return this.Contact;
        }

        public void setContact(ContactObject Contact) {
            this.Contact = Contact;
        };


        public String getId() {
            return this.id;
        }

        public void setId(String id) {
            this.id = id;
        }


        public CorinthPOITouristObject() {
            super();
        }

        public CorinthPOITouristObject(String id) {
            super();
            this.id = id;
        }


        public CorinthPOITouristObject(String id, String Name) {
            super();
            this.id = id;
            this.Name = Name;
        }

    public String getCuisine() {
        return Cuisine;
    }

    public void setCuisine(String cuisine) {
        Cuisine = cuisine;
    }

    public String getAmenities() {
        return Amenities;
    }

    public void setAmenities(String amenities) {
        Amenities = amenities;
    }

    public AddressObject getAddress() {
        return Address;
    }

    public void setAddress(AddressObject address) {
        Address = address;
    }
}
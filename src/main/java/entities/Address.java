package entities;

public class Address {

    private String street;
    private String country;
    private String city;

    public Address(String street, String country, String city) {
        this.setStreet(street);
        this.setCountry(country);
        this.setCity(city);
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}

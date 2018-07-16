package entities;

public class Author {

    private String firstName;
    private String lastName;
    private String country;
    private int dateOfBirth;
    private int dateOfDeath;

    public Author(String firstName,String lastName, String country, int dateOfBirth, int dateOfDeath) {
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setCountry(country);
        this.setDateOfBirth(dateOfBirth);
        this.setDateOfDeath(dateOfDeath);
    }

    public Author(String firstName,String lastName, String country, int dateOfBirth) {
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setCountry(country);
        this.setDateOfBirth(dateOfBirth);
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(int dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getDateOfDeath() {
        return dateOfDeath;
    }

    public void setDateOfDeath(int dateOfDeath) {
        this.dateOfDeath = dateOfDeath;
    }

    @Override
    public String toString() {
        return  firstName +
                ", " + lastName +
                ", " + country +
                ", " + dateOfBirth +
                ", " + dateOfDeath +
                '\n';
    }
}

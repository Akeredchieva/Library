package entities;

public class User {

    private Credentials credentials;
    private String email;
    private boolean gdpr;
    private PersonInfo personInfo;

    public User(Credentials credentials, String email, boolean gdpr, PersonInfo personInfo) {
        this.setCredentials(credentials);
        this.setEmail(email);
        this.setGdpr(gdpr);
        this.setPersonInfo(personInfo);
    }

    public Credentials getCredentials() {
        return credentials;
    }

    private void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public String getEmail() {
        return email;
    }

    private void setEmail(String email) {
        this.email = email;
    }

    public boolean getGdpr() {
        return gdpr;
    }

    public void setGdpr(boolean gdpr) {
        this.gdpr = gdpr;
    }

    public PersonInfo getPersonInfo() {
        return personInfo;
    }

    private void setPersonInfo(PersonInfo personInfo) {
        this.personInfo = personInfo;
    }
}

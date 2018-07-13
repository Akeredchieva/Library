package entities;

public enum Status {
    READ("read"),
    DOWNLOADED("downloaded"),
    OPENED("opened");

    private String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}

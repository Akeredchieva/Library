package entities;

import java.util.List;

public class QueueForBorrow {

    private List<User> waitingUsers;
    private PaperBook book;

    public QueueForBorrow(List<User> waitingUsers, PaperBook book) {
        this.waitingUsers = waitingUsers;
        this.book = book;
    }

    public List<User> getWaitingUsers() {
        return waitingUsers;
    }

    public void setWaitingUsers(List<User> waitingUsers) {
        this.waitingUsers = waitingUsers;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(PaperBook book) {
        this.book = book;
    }
}

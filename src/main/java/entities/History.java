package entities;

import java.util.List;

public class History {

    private User user;
    private List<HistoryEntry> historyOfBooks;
    private List<BorrowedBook> borrowedBooks;

    public History(User user, List<HistoryEntry> historyOfBooks, List<BorrowedBook> borrowedBooks) {
        this.setUser(user);
        this.setHistoryOfBooks(historyOfBooks);
        this.setBorrowedBooks(borrowedBooks);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<HistoryEntry> getHistoryOfBooks() {
        return historyOfBooks;
    }

    public void setHistoryOfBooks(List<HistoryEntry> historyOfBooks) {
        this.historyOfBooks = historyOfBooks;
    }

    public List<BorrowedBook> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(List<BorrowedBook> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public void addBorrowedBook(BorrowedBook borrowedBook){
        borrowedBooks.add(borrowedBook);
    }

    public void addBookInHistory(HistoryEntry HistoryEntry){
        historyOfBooks.add(HistoryEntry);
    }
}

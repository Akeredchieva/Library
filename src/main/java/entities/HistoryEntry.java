package entities;

import java.time.LocalDate;

public class HistoryEntry {

    private Status status;
    private Book book;
    private LocalDate dateOfReturn;

    public HistoryEntry(Status status, Book book, LocalDate dateOfReturn) {
        this.setStatus(status);
        this.setBook(book);
        this.setDateOfReturn(dateOfReturn);
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDate getDateOfReturn() {
        return dateOfReturn;
    }

    public void setDateOfReturn(LocalDate dateOfReturn) {
        this.dateOfReturn = dateOfReturn;
    }
}

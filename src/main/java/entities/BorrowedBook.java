package entities;

import java.time.LocalDate;

public class BorrowedBook {

    private Book book;
    private LocalDate dateOfReturn;
    private LocalDate dateOfTaken;

    public BorrowedBook(Book book, LocalDate dateOfReturn, LocalDate dateOfTaken) {
        this.setBook(book);
        this.setDateOfReturn(dateOfReturn);
        this.setDateOfTaken(dateOfTaken);
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

    public LocalDate getDateOfTaken() {
        return dateOfTaken;
    }

    public void setDateOfTaken(LocalDate dateOfTaken) {
        this.dateOfTaken = dateOfTaken;
    }
}

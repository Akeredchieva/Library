package entities;

import java.time.LocalDate;

public class BorrowedBook {

    private PaperBook book;
    private LocalDate dateOfReturn;
    private LocalDate dateOfTaken;

    public BorrowedBook(PaperBook book, LocalDate dateOfTaken) {
        this.setBook(book);
        this.setDateOfReturn(dateOfTaken.plusDays(14));
        this.setDateOfTaken(dateOfTaken);
    }

    public Book getBook() {
        return book;
    }

    public void setBook(PaperBook book) {
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

    @Override
    public String toString() {
        return  "book: " + book + "\n" +
                "dateOfReturn: " + dateOfReturn + "\n" +
                "dateOfTaken: " + dateOfTaken;
    }
}

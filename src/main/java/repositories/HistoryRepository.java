package repositories;

import entities.*;

import java.time.LocalDate;
import java.util.List;

public interface HistoryRepository {

    void addBookToBorrowed(String username, Book book, LocalDate dateOfTaken, LocalDate dateOfReturn);

    void addBookToHistory(String username,Book book, Status status);

    List<HistoryEntry> getHistoryEntry(String username);

    List<BorrowedBook> getBorrowedBooks(String username);

    BorrowedBook findExactBook(User user, Book book);

    void changeReturnDate(BorrowedBook borrowedBook, int daysOfPostponement);

    LocalDate getExpirationDate(Book book);

}

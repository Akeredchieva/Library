package repositories;

import entities.*;

import java.time.LocalDate;
import java.util.List;

public interface HistoryRepository {

    void addBookToBorrowed(User user, Book bookInput);

    void addBookToHistory(User user,Book book, Status status);

    List<HistoryEntry> getHistoryEntry(String username);

    List<BorrowedBook> getBorrowedBooks(String username);

    String getAllHistory(String username);

    BorrowedBook findExactBook(User user, Book book);

    void changeReturnDate(Book bookInput, int daysOfPostponement, User user);

    LocalDate getExpirationDate(Book book);

}

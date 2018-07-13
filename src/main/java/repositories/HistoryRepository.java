package repositories;

import entities.Book;
import entities.BorrowedBook;
import entities.HistoryEntry;
import entities.Status;

import java.time.LocalDate;
import java.util.List;

public interface HistoryRepository {

    void addBookToBorrowed(String username, Book book, LocalDate dateOfTaken, LocalDate dateOfReturn);

    void addBookToHistory(String username,Book book, Status status);

    List<HistoryEntry> getHistoryEntry(String username);

    List<BorrowedBook> getBorrowedBooks(String username);

    BorrowedBook findExactBook(Book book);

    void changeBorrowedBook(BorrowedBook borrowedBook, int daysOfPostponement);

}

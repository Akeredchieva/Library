package repositories;

import entities.*;

import java.awt.print.Paper;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class HistoryRepositoryImpl implements HistoryRepository {
    @Override
    public void addBookToBorrowed(String username, Book book, LocalDate dateOfTaken, LocalDate dateOfReturn) {

        for (int i=0; i < DBClassExample.history.size(); i++){
            if (DBClassExample.history.get(i).getUser().getCredentials().getUsername().equals(username)){
                BorrowedBook borrowedBook= new BorrowedBook(book,dateOfReturn,dateOfTaken);
                DBClassExample.history.get(i).addBorrowedBook(borrowedBook);
            }
        }

    }

    @Override
    public void addBookToHistory(String username,Book book, Status status) {

        for (int i=0; i< DBClassExample.history.size(); i++){
              if(!DBClassExample.history.get(i).getHistoryOfBooks().contains(book)){
                  HistoryEntry historyEntry = new HistoryEntry(status,book,LocalDate.now());
                  DBClassExample.history.get(i).addBookInHistory(historyEntry);
              }

        }
    }

    @Override
    public List<HistoryEntry> getHistoryEntry(String username) {

        for (int i=0; i< DBClassExample.history.size(); i++){
            if (DBClassExample.history.get(i).getUser().getCredentials().getUsername().equals(username)){
                return DBClassExample.history.get(i).getHistoryOfBooks();
            }
        }

        return null;
    }

    @Override
    public List<BorrowedBook> getBorrowedBooks(String username) {
        for (int i=0; i< DBClassExample.history.size(); i++){
            if (DBClassExample.history.get(i).getUser().getCredentials().getUsername().equals(username)){
                return DBClassExample.history.get(i).getBorrowedBooks();
            }
        }

        return null;
    }

    @Override
    public BorrowedBook findExactBook(Book book) {
        for (int i = 0; i < DBClassExample.history.size(); i++) {
            for (int j=0; j< DBClassExample.history.get(i).getBorrowedBooks().size(); j++) {
                if (DBClassExample.history.get(i).getBorrowedBooks().get(j).getBook().equals(book)) {
                    return DBClassExample.history.get(i).getBorrowedBooks().get(j);
                }
            }
        }
        return null;
    }

    @Override
    public void changeBorrowedBook(BorrowedBook borrowedBook, int daysOfPostponement) {
        for (int i = 0; i < DBClassExample.history.size(); i++) {
            for (int j = 0; j < DBClassExample.history.get(i).getBorrowedBooks().size(); j++) {
                if (DBClassExample.history.get(i).getBorrowedBooks().get(j).getBook().equals(borrowedBook.getBook())) {
                    if (DBClassExample.history.get(i).getBorrowedBooks().get(j).getDateOfTaken().until(LocalDate.now(),ChronoUnit.DAYS) <= (28 - daysOfPostponement)) {
                        LocalDate newReturnDate = DBClassExample.history.get(i).getBorrowedBooks().get(j).getDateOfReturn().plusDays(daysOfPostponement);
                        DBClassExample.history.get(i).getBorrowedBooks().get(j).setDateOfReturn(newReturnDate);
                    }
                }
            }
        }
    }

    @Override
    public LocalDate getExpirationDate(Book book) {
        if (book instanceof PaperBook){
            for (int i = 0; i < DBClassExample.history.size(); i++) {
                for (int j=0; j< DBClassExample.history.get(i).getBorrowedBooks().size(); j++){
                    if (DBClassExample.history.get(i).getBorrowedBooks().get(j).getBook().equals(book)) {
                        return DBClassExample.history.get(i).getBorrowedBooks().get(j).getDateOfReturn();
                    }
                }
            }
        }
        return null;
    }

}

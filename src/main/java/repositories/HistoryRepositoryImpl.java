package repositories;

import entities.*;

import java.time.LocalDate;
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

}

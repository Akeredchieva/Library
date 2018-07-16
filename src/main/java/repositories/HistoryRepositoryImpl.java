package repositories;

import entities.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class HistoryRepositoryImpl implements HistoryRepository {

    List<History> history = new ArrayList<>();

    public HistoryRepositoryImpl(List<History> history) {
        this.history = history;
    }

    @Override
    public void addBookToBorrowed(String username, Book book, LocalDate dateOfTaken, LocalDate dateOfReturn) {

        for (int i = 0; i < this.history.size(); i++){
            if (this.history.get(i).getUser().getCredentials().getUsername().equals(username)){
                BorrowedBook borrowedBook= new BorrowedBook(book,dateOfTaken);
                this.history.get(i).addBorrowedBook(borrowedBook);
            }
        }

    }

    @Override
    public void addBookToHistory(String username,Book book, Status status) {

        for (int i = 0; i< this.history.size(); i++){
              if(!this.history.get(i).getHistoryOfBooks().contains(book)){
                  HistoryEntry historyEntry = new HistoryEntry(status,book,LocalDate.now());
                  this.history.get(i).addBookInHistory(historyEntry);
              }

        }
    }

    @Override
    public List<HistoryEntry> getHistoryEntry(String username) {

        for (int i = 0; i< this.history.size(); i++){
            if (this.history.get(i).getUser().getCredentials().getUsername().equals(username)){
                return this.history.get(i).getHistoryOfBooks();
            }
        }

        return null;
    }

    @Override
    public List<BorrowedBook> getBorrowedBooks(String username) {
        for (int i = 0; i< this.history.size(); i++){
            if (this.history.get(i).getUser().getCredentials().getUsername().equals(username)){
                return this.history.get(i).getBorrowedBooks();
            }
        }

        return null;
    }

    @Override
    public String getAllHistory(String username) {
        StringBuilder sb = new StringBuilder();
        List<HistoryEntry> result = this.getHistoryEntry(username);
        for (HistoryEntry he : result) {
            sb.append(he.toString());
        }
        return sb.toString();
    }

    @Override
    public BorrowedBook findExactBook(User user, Book book) {
        for (int i = 0; i < this.history.size(); i++) {
            if (this.history.get(i).getUser().equals(user)) {
                for (int j = 0; j < this.history.get(i).getBorrowedBooks().size(); j++) {
                    if (this.history.get(i).getBorrowedBooks().get(j).getBook().equals(book)) {
                        return this.history.get(i).getBorrowedBooks().get(j);
                    }
                }
            }
        }
        return null;
    }

    // TODO: Napravi si koda chetim!!!!
    // TODO: Za ostavashti dni vazmojni za zaqvka ako iskash printvai si i kolko dni.
    @Override
    public void changeReturnDate(Book bookInput, int daysOfPostponement, User user) {
        if (bookInput instanceof PaperBook) {
            PaperBook book = (PaperBook) bookInput;
            for (int i = 0; i < this.history.size(); i++) {
                if (this.history.get(i).getUser().equals(user)) {
                    for (int j = 0; j < this.history.get(i).getBorrowedBooks().size(); j++) {
                        if (this.history.get(i).getBorrowedBooks().get(j).getBook().equals(book)) {
                            long days = this.history.get(i).getBorrowedBooks().get(j).getDateOfTaken().until(LocalDate.now(), ChronoUnit.DAYS);
                            if (days <= (28 - daysOfPostponement)) {
                                LocalDate newReturnDate = this.history.get(i).getBorrowedBooks().get(j).getDateOfReturn().plusDays(daysOfPostponement);
                                this.history.get(i).getBorrowedBooks().get(j).setDateOfReturn(newReturnDate);
                            } else if ((this.history.get(i).getBorrowedBooks().get(j).getDateOfReturn().until(LocalDate.now()).getDays() >= 0) && (this.history.get(i).getBorrowedBooks().get(j).getDateOfReturn().until(LocalDate.now()).getMonths() >= 0) && (this.history.get(i).getBorrowedBooks().get(j).getDateOfReturn().until(LocalDate.now()).getDays() >= 0)) {
                                String message = "The return date is expired with "
                                        + this.history.get(i).getBorrowedBooks().get(j).getDateOfReturn().until(LocalDate.now()).getDays()
                                        + " days.And "
                                        + this.history.get(i).getBorrowedBooks().get(j).getDateOfReturn().until(LocalDate.now()).getMonths()
                                        + " months.Please return your book.";
                                throw new IllegalArgumentException(message);
                            } else if (days > (28 - daysOfPostponement)) {
                                String message = "You can not make so long postponement.";
                                throw new IllegalArgumentException(message);
                            }
                        }
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("This book can not be postponement, because it is E-Book.");
        }
    }

    @Override
    public LocalDate getExpirationDate(Book book) {
        if (book instanceof PaperBook){
            for (int i = 0; i < this.history.size(); i++) {
                for (int j=0; j< this.history.get(i).getBorrowedBooks().size(); j++){
                    if (this.history.get(i).getBorrowedBooks().get(j).getBook().equals(book)) {
                        return this.history.get(i).getBorrowedBooks().get(j).getDateOfReturn();
                    }
                }
            }
        }
        return null;
    }

}

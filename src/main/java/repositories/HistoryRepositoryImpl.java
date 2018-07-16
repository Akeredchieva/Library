package repositories;

import entities.*;
import services.BorrowingService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class HistoryRepositoryImpl implements HistoryRepository {

    List<History> history;

    public HistoryRepositoryImpl() {
        this.history = new ArrayList<>();
    }

    @Override
    public void addBookToBorrowed(User user, Book bookInput) {

        if (bookInput instanceof PaperBook) {
            if (this.history.size() == 0){
                List<HistoryEntry> historyEntries = new ArrayList<>();
                List<BorrowedBook> borrowedBooks = new ArrayList<>();
                BorrowedBook borrowedBook = new BorrowedBook((PaperBook) bookInput);
                borrowedBooks.add(borrowedBook);
                History history = new History(user, historyEntries,borrowedBooks);
                this.history.add(history);
            } else {
                PaperBook book = (PaperBook) bookInput;
                for (int i = 0; i < this.history.size(); i++) {
                    if (this.history.get(i).getUser().getCredentials().getUsername().equals(user.getCredentials().getUsername())) {
                        BorrowedBook borrowedBook = new BorrowedBook(book);
                        this.history.get(i).addBorrowedBook(borrowedBook);
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("You can not borrow e-book.");
        }

    }

    @Override
    public void addBookToHistory(User user,Book book, Status status) {

        if (this.history.size() == 0){
            HistoryEntry historyEntry = new HistoryEntry(status,book,LocalDate.now());
            List<HistoryEntry> historyEntries = new ArrayList<>();
            historyEntries.add(historyEntry);
            List<Book> books = new ArrayList<>();
            books.add(book);
            List<BorrowedBook> borrowedBooks = new ArrayList<>();
          //  borrowedBooks.add(new BorrowedBook(null));
            History history = new History(user, historyEntries,null);
            this.history.add(history);
        } else {
            for (int i = 0; i < this.history.size(); i++) {
                if (!this.history.get(i).getHistoryOfBooks().contains(book)) {
                    HistoryEntry historyEntry = new HistoryEntry(status, book, LocalDate.now());
                    this.history.get(i).addBookInHistory(historyEntry);
                }

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
        if (result == null){
            throw new IllegalArgumentException("This user does not have history.");
        }
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

package services;

import entities.*;
import repositories.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Service which create the functionality of the user. Here is the needed options which the user can make in it's profile.
 */
public class UserService {
    private BookRepository bookRepository = new BookRepositoryImpl();
    private HistoryRepository historyRepository = new HistoryRepositoryImpl();
    private BorrowingService borrowingService = new BorrowingService();


    public LocalDate checkExpirationDate(Book book){
        if (book instanceof PaperBook) {
            if (historyRepository.getExpirationDate(book) != null){
                return historyRepository.getExpirationDate(book);
            }
            throw new IllegalArgumentException("This book is not present in the history.");

        }
        throw new IllegalArgumentException("You can not see expiration date on electronic book.");
    }

    public void makePostponement(User user, Book book, int daysOfPostponement){

        BorrowedBook borrowedBook= historyRepository.findExactBook(user, book);
        borrowingService.changeBorrowedBook(borrowedBook,daysOfPostponement);

    }

    public String viewHistory(String username){

       List<HistoryEntry> history = historyRepository.getHistoryEntry(username);
       StringBuilder sb = new StringBuilder();
        for (HistoryEntry historyEntry : history) {
            sb.append(historyEntry.toString()).append("\n");
        }
        return sb.toString();
    }


    public String viewBorrowedBooks(String username){
        List<BorrowedBook> borrowedBooks = historyRepository.getBorrowedBooks(username);
        StringBuilder sb = new StringBuilder();
        for (BorrowedBook borrowedBook : borrowedBooks) {
            sb.append(borrowedBook.toString()).append("\n");
        }
        return sb.toString();

    }

    public void requestForBorrowingBook(Book book, String username){
        StringBuilder sb = new StringBuilder();
        int numberInTheQueue = 0;
        long daysTheBookAvailable = 0;
        if (book instanceof PaperBook){
            if(bookRepository.isBookAvailable((PaperBook) book)){
                bookRepository.decAvailableCopies((PaperBook)book);
                borrowingService.borrowBook((PaperBook) book,username);
                //TODO: trqbva da namalish kopiqta na knigata s edno.Ako nqma kopiq trqbva da idesh w opashkata i da dobavish
                // TODO: user-a w opshakata za tazi kniga i da mu varnesh nomer
                sb.append("The book is borrowed.");
            } else {
                numberInTheQueue = bookRepository.createQueryForWaiting((PaperBook) book, username);
                daysTheBookAvailable = bookRepository.bookAvailability((PaperBook) book);
            }
        } else {
            throw new IllegalArgumentException("This book is not paper book so it could no be borrowed.");
        }
        sb.append("You number in the query is ")
                .append(numberInTheQueue)
                .append(".\nAnd the book will be available after: ")
                .append(daysTheBookAvailable)
                .append(("days."));

    }
}

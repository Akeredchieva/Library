package services;

import entities.*;
import repositories.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Service which create the functionality of the user. Here is the needed options which the user can make in it's profile.
 */
public class UserService {
    private BookRepository bookRepository = new BookRepositoryImpl(DBClassExample.booksInLibrary,DBClassExample.queue,DBClassExample.users,DBClassExample.history);
    private HistoryRepository historyRepository = new HistoryRepositoryImpl(DBClassExample.history);
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

    // TODO: Vij zashto tarsish borrowed book i izprashtash borrowed book ? Ne moje li prosto da pratish dadenata kato input book?
    public void makePostponement(User user, Book book, int daysOfPostponement){
        borrowingService.changeBorrowedBook(book,daysOfPostponement, user);

    }

    public String viewHistory(String username){

       return historyRepository.getAllHistory(username);

    }


    public String viewBorrowedBooks(String username){
        List<BorrowedBook> borrowedBooks = historyRepository.getBorrowedBooks(username);
        StringBuilder sb = new StringBuilder();
        if (borrowedBooks == null){
            throw new IllegalArgumentException("The is no borrowed books.");
        }
        for (BorrowedBook borrowedBook : borrowedBooks) {
            sb.append(borrowedBook.toString()).append("\n");
        }
        return sb.toString();

    }

    public String requestForBorrowingBook(Book book, String username){
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
                return sb.append("You number in the query is ")
                        .append(numberInTheQueue)
                        .append(".\nAnd the book will be available after: ")
                        .append(daysTheBookAvailable)
                        .append(("days.")).toString();
            }
        } else {
            throw new IllegalArgumentException("This book is not paper book so it could no be borrowed.");
        }
        return sb.toString();
    }
}

package services;

import entities.Book;
import entities.BorrowedBook;
import entities.HistoryEntry;
import entities.PaperBook;
import repositories.*;

import java.util.List;

public class UserService {
    private BookRepository bookRepository = new BookRepositoryImpl();
    private UserRepository userRepository = new UserRepositoryImpl();
    private HistoryRepository historyRepository = new HistoryRepositoryImpl();
    BorrowingService borrowingService = new BorrowingService();


    // TODO: LocalDate checkExpirationDate();

    // TODO: void makePostponement();

    public String viewHistory(String username){

       List<HistoryEntry> history = historyRepository.getHistoryEntry(username);
       StringBuilder sb = new StringBuilder();
        for (HistoryEntry historyEntry : history) {
            sb.append(historyEntry.toString() + "\n");
        }
        return sb.toString();
    }


    public String viewBorrowedBooks(String username){
        List<BorrowedBook> borrowedBooks = historyRepository.getBorrowedBooks(username);
        StringBuilder sb = new StringBuilder();
        for (BorrowedBook borrowedBook : borrowedBooks) {
            sb.append(borrowedBook.toString() + "\n");
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
        }
        sb.append("You number in the query is " + numberInTheQueue +
                ".\nAnd the book will be available after: " +
                daysTheBookAvailable + "days." );

    }
}

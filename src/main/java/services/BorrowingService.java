package services;

import entities.Book;
import entities.PaperBook;
import repositories.BookRepository;
import repositories.BookRepositoryImpl;


//TODO: Promeni si imeto da e BookService - zashtoto vatre ti e i logikata za online kniga, koqto ne se borrow-va
public class BorrowingService {

    private BookRepository bookRepository = new BookRepositoryImpl();

   // TODO: String msgForAttemptAnotherRequest();
   // TODO: int checkDelayDays();

    public void requestForBorrowingBook(Book book, String username){
        if (book instanceof PaperBook){
           if(bookRepository.isBookAvailable((PaperBook) book)){
               bookRepository.decAvailableCopies((PaperBook)book);
               //TODO: trqbva da namalish kopiqta na knigata s edno.Ako nqma kopiq trqbva da idesh w opashkata i da dobavish
               // TODO: user-a w opshakata za tazi kniga i da mu varnesh nomer
           } else {
               int numberInTheQueue = bookRepository.createQueryForWaiting((PaperBook) book, username);
               int daysTheBookAvailable = bookRepository.bookAvailability((PaperBook) book);
           }
        }

    }

   // TODO: Date calcTimeTillTheBookWillBeFree();

    public String openOnlineBook(){

        return "";
    }

    // TODO: downloadOnlineBook();



}

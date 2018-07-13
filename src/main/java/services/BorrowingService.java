package services;

import entities.Book;
import entities.EBook;
import entities.PaperBook;
import repositories.BookRepository;
import repositories.BookRepositoryImpl;


//TODO: Promeni si imeto da e BookService - zashtoto vatre ti e i logikata za online kniga, koqto ne se borrow-va
public class BorrowingService {

    private BookRepository bookRepository = new BookRepositoryImpl();

   // TODO: String msgForAttemptAnotherRequest();
   // TODO: int checkDelayDays();

    void borrowBook(PaperBook book, String username){
        bookRepository.setBookforBorrow(book, username);
    }

   // TODO: Date calcTimeTillTheBookWillBeFree();

    public String openOnlineBook(Book book){
        StringBuilder sb = new StringBuilder();
        if (book instanceof EBook) {
            sb.append("The link for online reading: "+ bookRepository.openOnlineBook((EBook) book));
        } else {
            throw new IllegalArgumentException("This book is not electronic.");
        }
        return sb.toString();
    }

    // TODO: downloadOnlineBook();

    public String downloadOnlineBook(Book book){

        StringBuilder sb = new StringBuilder();
        if (book instanceof EBook) {
            sb.append("The link for download: " + bookRepository.downloadEBook((EBook) book));
        } else {
            throw new IllegalArgumentException("This book is not electronic.");
        }

        return sb.toString();
    }

}

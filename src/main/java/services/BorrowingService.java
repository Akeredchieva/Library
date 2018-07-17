package services;

import entities.*;
import repositories.BookRepository;
import repositories.BookRepositoryImpl;
import repositories.HistoryRepository;
import repositories.HistoryRepositoryImpl;


//TODO: Promeni si imeto da e BookService - zashtoto vatre ti e i logikata za online kniga, koqto ne se borrow-va
public class BorrowingService {


    DBClassExample DBClassExample = new DBClassExample();
    private BookRepository bookRepository = new BookRepositoryImpl(DBClassExample.booksInLibrary,DBClassExample.queue,DBClassExample.users,DBClassExample.history);
    private HistoryRepository historyRepository = new HistoryRepositoryImpl(DBClassExample.history);

    // TODO: String msgForAttemptAnotherRequest();
   // TODO: int checkDelayDays();

    public void changeBorrowedBook(Book book, int daysOfPostponement, User user){
        historyRepository.changeReturnDate(book, daysOfPostponement, user);
    }

    public void borrowBook(PaperBook book, String username){
        bookRepository.setBookforBorrow(book, username);
    }

   // TODO: Date calcTimeTillTheBookWillBeFree();

    public String openOnlineBook(Book book){
        StringBuilder sb = new StringBuilder();
        if (book instanceof EBook) {
            sb.append("The link for online reading: ");
            sb.append(bookRepository.openOnlineBook((EBook) book));
        } else {
            throw new IllegalArgumentException("This book is not electronic.");
        }
        return sb.toString();
    }

    // TODO: downloadOnlineBook();

    public String downloadOnlineBook(Book book){

        StringBuilder sb = new StringBuilder();
        if (book instanceof EBook) {
            sb.append("The link for download: ");
            sb.append(bookRepository.downloadEBook((EBook) book));
        } else {
            throw new IllegalArgumentException("This book is not electronic.");
        }

        return sb.toString();
    }

}

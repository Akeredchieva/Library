package services;

import entities.*;
import repositories.BookRepository;
import repositories.BookRepositoryImpl;
import repositories.HistoryRepository;
import repositories.HistoryRepositoryImpl;


//TODO: Promeni si imeto da e BookService - zashtoto vatre ti e i logikata za online kniga, koqto ne se borrow-va
public class BorrowingService {

    DBClassExample db = new DBClassExample();
    private BookRepository bookRepository = new BookRepositoryImpl();
    private HistoryRepository historyRepository = new HistoryRepositoryImpl();

    public BorrowingService(BookRepository bookRepository, HistoryRepository historyRepository) {
        this.bookRepository = bookRepository;
        this.historyRepository = historyRepository;
    }

    // TODO: String msgForAttemptAnotherRequest();
   // TODO: int checkDelayDays();

    void changeBorrowedBook(Book book, int daysOfPostponement, User user){
        historyRepository.changeReturnDate(book, daysOfPostponement, user);
    }

    void borrowBook(PaperBook book, User user){
        bookRepository.setBookforBorrow(book, user.getCredentials().getUsername());
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

package services;

import entities.*;
import repositories.BookRepository;
import repositories.BookRepositoryImpl;
import repositories.HistoryRepository;
import repositories.HistoryRepositoryImpl;


public class BorrowingService {


    DBClassExample DBClassExample = new DBClassExample();
    private BookRepository bookRepository = new BookRepositoryImpl(DBClassExample.booksInLibrary,DBClassExample.queue,DBClassExample.users,DBClassExample.history);
    private HistoryRepository historyRepository = new HistoryRepositoryImpl(DBClassExample.history);

    // TODO: String msgForAttemptAnotherRequest();
   // TODO: int checkDelayDays();

    public void makePostponement(Book book, int daysOfPostponement, User user){
        historyRepository.changeReturnDate(book, daysOfPostponement, user);
    }

    public void borrowBook(Book book, String username){
        if (book instanceof PaperBook) {
            PaperBook paperBook = (PaperBook) book;
            bookRepository.setBookforBorrow(paperBook, username);
        } else {
            throw new IllegalArgumentException("This book is not paper.");
        }
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

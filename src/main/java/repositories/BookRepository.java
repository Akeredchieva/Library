package repositories;

import entities.Author;
import entities.Book;
import entities.EBook;
import entities.PaperBook;


import java.util.List;


public interface BookRepository {

   void creatBook(Book book);

   boolean isBookAvailable(PaperBook book);

   String downloadEBook(EBook eBook);


   List<Book> findBookByTitle(String title);

   List<Book> findBookByGenres(String... genres);

   List<Book> findBookByTags(String... tags);

   public List<Book> findBookByAuthorName(String fullName) ;

   boolean isTheBookFree();

   void decAvailableCopies(PaperBook book);

   int createQueryForWaiting(PaperBook book, String username);

   long bookAvailability(PaperBook book);

   String openOnlineBook(EBook eBook);

    public void setBookforBorrow(PaperBook book, String username);


}

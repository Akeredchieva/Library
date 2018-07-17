package services;

import entities.Book;
import repositories.BookRepository;
import repositories.BookRepositoryImpl;

import java.util.List;

public class SearchService {
    DBClassExample DBClassExample = new DBClassExample();

    private BookRepository bookRepository = new BookRepositoryImpl(DBClassExample.booksInLibrary, DBClassExample.queue, DBClassExample.users, DBClassExample.history);

    List<Book> searchByTitle(String title){
        if(bookRepository.findBookByTitle(title).isEmpty()){
            throw new IllegalArgumentException("There is no book with this title.");
        }
        return bookRepository.findBookByTitle(title);
    }

    List<Book> searchByGenres(String... genres){
        if (genres == null){
            throw new IllegalArgumentException("This is impossible search.");
        }
        if(bookRepository.findBookByGenres(genres).isEmpty()){
            throw new IllegalArgumentException("There is no book with this genres.");
        }
        return bookRepository.findBookByGenres(genres);
    }

    List<Book> searchByTags(String... tags){
        if (tags == null){
            throw new IllegalArgumentException("This is impossible search.");
        }
        if(bookRepository.findBookByTags(tags).isEmpty()) {
            throw new IllegalArgumentException("There is no book with this tags.");
        }
        return bookRepository.findBookByTags(tags);
    }

    List<Book> searchByAuthorFullName(String name){
        if (name == null){
            throw new IllegalArgumentException("This is impossible search.");
        }
        if(bookRepository.findBookByAuthorName(name).isEmpty()) {
         throw new IllegalArgumentException("There is no book with this author");
        }
        return bookRepository.findBookByAuthorName(name);
    }

}

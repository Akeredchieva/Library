package services;

import entities.Book;
import repositories.BookRepository;
import repositories.BookRepositoryImpl;

import java.util.List;

public class SearchService {

    private BookRepository bookRepository = new BookRepositoryImpl(DBClassExample.booksInLibrary, DBClassExample.queue, DBClassExample.users, DBClassExample.history);

    //TODO: opravi si metodite za imenata da sa generic - da priemat edin string da go parsva
    // TODO: chrez whitespace kato vzima parviq i posledniq substring

    List<Book> searchByTitle(String title){
        return bookRepository.findBookByTitle(title);
    }

    List<Book> searchByGenres(String... genres){
        return bookRepository.findBookByGenres(genres);
    }

    List<Book> searchByTags(String... tags){
        return bookRepository.findBookByTags(tags);
    }

    List<Book> searchByAuthorFullName(String name){
        return bookRepository.findBookByAuthorName(name);
    }

}

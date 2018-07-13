package repositories;


import entities.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.List;

import static java.util.concurrent.TimeUnit.DAYS;

public class BookRepositoryImpl implements BookRepository {

    //DBClassExample db = new DBClassExample();

    @Override
    public void creatBook(Book book) {

    }

    @Override
    public boolean isBookAvailable(PaperBook book) {
        for (int i=0; i< DBClassExample.booksInLibrary.size(); i++){
            if (book.equals(DBClassExample.booksInLibrary.get(i))){
                PaperBook paperBook = (PaperBook) DBClassExample.booksInLibrary.get(i);
                return paperBook.getNumberOfCopiesAvailable() > 0;
            }
        }
        throw new IllegalArgumentException("There is no such book.");
    }

    @Override
    public String downloadEBook(EBook eBook) {

        for (int i=0; i< DBClassExample.booksInLibrary.size(); i++){
            if (DBClassExample.booksInLibrary.get(i) instanceof EBook){
                if (DBClassExample.booksInLibrary.get(i).getTitle().equals(eBook.getTitle())){
                    if (DBClassExample.booksInLibrary.get(i).getAuthors().containsAll(eBook.getAuthors())){
                        return ((EBook) DBClassExample.booksInLibrary.get(i)).getDownloadLink();
                    }
                }
            }
        }
        return null;
    }

    /*
    @Override
    public List<Book> findBookByAuthorFirstName(String firstName) {
        List<Book> foundBooks = new ArrayList<>();
        for (Book book: DBClassExample.booksInLibrary) {
            for (Author author: book.getAuthors()) {
                if(author.getFirstName().equalsIgnoreCase(firstName)){
                    foundBooks.add(book);
                }
            }
        }
        return foundBooks;

    }

    @Override
    public List<Book> findBookByAuthorLastName(String lastName) {
        List<Book> foundBooks = new ArrayList<>();
        for (Book book: DBClassExample.booksInLibrary) {
            for (Author author: book.getAuthors()) {
                if(author.getLastName().equalsIgnoreCase(lastName)){
                    foundBooks.add(book);
                }
            }
        }
        return foundBooks;
    }

*/


    @Override
    public List<Book> findBookByTitle(String title) {
        List<Book> foundBooks = new ArrayList<>();
        for (Book book: DBClassExample.booksInLibrary) {
            if(book.getTitle().equalsIgnoreCase(title)){
                    foundBooks.add(book);
            }
        }
        return foundBooks;
    }

    @Override
    public List<Book> findBookByGenres(String... genres) {
        List<Book> foundBooks = new ArrayList<>();
        for (Book book: DBClassExample.booksInLibrary) {
            for (String genre: genres) {
                if (book.getGenre().equalsIgnoreCase(genre))
                    foundBooks.add(book);
            }
        }
        return foundBooks;
    }

    @Override
    public List<Book> findBookByTags(String... tags) {
        List<Book> foundBooks = new ArrayList<>();
        for (Book book: DBClassExample.booksInLibrary) {
            for (String tagBook : book.getTags()) {
                for (String tagSearch : tags) {
                    if (tagBook.equalsIgnoreCase(tagSearch))
                        foundBooks.add(book);
                }

            }
        }
        return foundBooks;
    }

    @Override
    public List<Book> findBookByAuthorName(String fullName) {
        List<Book> foundBooks = new ArrayList<>();
        for (Book book: DBClassExample.booksInLibrary) {
            for (Author author: book.getAuthors()) {
                String fullNameAuthor = author.getFirstName().toLowerCase() + " " + author.getLastName().toLowerCase();
                String fullNameAuthorReverse = author.getLastName().toLowerCase() + " " + author.getFirstName().toLowerCase();
                if(fullNameAuthor.contains(fullName) || fullNameAuthorReverse.contains(fullName)){
                        foundBooks.add(book);
                }
            }
        }
        return foundBooks;
    }

    @Override
    public boolean isTheBookFree() {
        return false;
    }

    @Override
    public void decAvailableCopies(PaperBook book) {
            for (int i=0; i < DBClassExample.booksInLibrary.size(); i++){
                if (book.equals(DBClassExample.booksInLibrary.get(i))) {
                    PaperBook paperBookDB = (PaperBook) DBClassExample.booksInLibrary.get(i);
                    int numberOfCopies = paperBookDB.getAllCopies() - 1;
                    DBClassExample.booksInLibrary.remove(paperBookDB);
                    paperBookDB.setAllCopies(numberOfCopies);
                    DBClassExample.booksInLibrary.add(paperBookDB);
                    break;
                }
            }
    }

    // TODO: vij kade go polzvash i napravi proverka za -1.
    // TODO: izchisti si metoda - imash povtoreniq na koda.
    @Override
    public int createQueryForWaiting(PaperBook book, String username){
        /*
          TODO: proverqva dali ima quety za tazi kniga. Ako ima proverqva(po username) dali tozi user e podal veche zaqvka.
          TODO: Ako e podal vrashta index-a mu ot list-a, a ako ne e do odbavq i otnovo vrashta index-a
        */
        if (DBClassExample.queue.isEmpty()){
            // ako spisaka e prazen syzdavam go
            for (int k=0; k< DBClassExample.users.size(); k++) {
                User user = DBClassExample.users.get(k);
                if (username.equals(user.getCredentials().getUsername())) {
                    List<User> users = new ArrayList<>();
                    users.add(user);
                    QueueForBorrow queueForBorrow = new QueueForBorrow(users, book);
                    DBClassExample.queue.add(queueForBorrow);
                    return DBClassExample.queue.size();
                }
            }
        } else {
            for (int i = 0; i < DBClassExample.queue.size(); i++) {
                // proverqvame dali knigata q ima v spisaka
                if (book.equals(DBClassExample.queue.get(i).getBook())) {
                    for (int j = 0; j < DBClassExample.queue.get(i).getWaitingUsers().size(); j++) {
                        //proverqvame dali user-a e veche v lista na chakashti
                        if (username.equals(DBClassExample.queue.get(i).getWaitingUsers().get(j).getCredentials().getUsername())) {
                            return j;
                        } else {
                            for (int k = 0; i < DBClassExample.users.size(); k++) {
                                User user = DBClassExample.users.get(k);
                                // dobavqme user-a v lista na chakashti
                                if (username.equals(user.getCredentials().getUsername())) {
                                    DBClassExample.queue.get(i).getWaitingUsers().add(user);
                                    return DBClassExample.queue.get(i).getWaitingUsers().size();
                                }

                            }
                        }
                    }
                } else {
                    //ako knigata q nqma q zapisvame s tozi user koito e napravil molbata
                    for (int k = 0; k < DBClassExample.users.size(); k++) {
                        User user = DBClassExample.users.get(k);
                        if (username.equals(user.getCredentials().getUsername())) {
                            List<User> users = new ArrayList<>();
                            users.add(user);
                            QueueForBorrow queueForBorrow = new QueueForBorrow(users, book);
                            DBClassExample.queue.add(queueForBorrow);
                            return DBClassExample.queue.size();
                        }
                    }
                }
            }
        }
        return -1;
    }


// TODO: Proveri dali raboti korektno
    @Override
    public long bookAvailability(PaperBook book) {
        LocalDate theDateForTaking = LocalDate.now();
        for (int i=0; i< DBClassExample.queue.size(); i++){
            if (book.equals(DBClassExample.queue.get(i).getBook())){
                User userBorrowedBook = DBClassExample.queue.get(i).getWaitingUsers().get(0);
                HistoryRepository historyRepository = new HistoryRepositoryImpl();
                List<BorrowedBook> borrowedBooks = historyRepository.getBorrowedBooks(userBorrowedBook.getCredentials().getUsername());
                for (int j=0; j< borrowedBooks.size(); j++){
                   if(borrowedBooks.get(i).getBook().getTitle().equals(book.getTitle())){
                       theDateForTaking =  borrowedBooks.get(i).getDateOfTaken();
                       break;
                   }
                }
                theDateForTaking = theDateForTaking.plusDays(DBClassExample.queue.get(i).getWaitingUsers().size()*28);
                return LocalDate.now().until(theDateForTaking,ChronoUnit.DAYS);
            }
        }
        return -1;
    }

    @Override
    public String openOnlineBook(EBook eBook) {
        for (int i=0; i< DBClassExample.booksInLibrary.size() ; i++){
            if (DBClassExample.booksInLibrary.get(i) instanceof EBook){
                if (eBook.getTitle().equals(DBClassExample.booksInLibrary.get(i).getTitle())){
                    if (eBook.getAuthors().containsAll(DBClassExample.booksInLibrary.get(i).getAuthors())){
                        return ((EBook) DBClassExample.booksInLibrary.get(i)).getOnlineReadingLink();
                    }
                }
            }
        }
        return null;
    }

    @Override
    public void setBookforBorrow(PaperBook book, String username) {
        for (int i=0; i< DBClassExample.history.size(); i++){
            if (DBClassExample.history.get(i).getUser().getCredentials().getUsername().equals(username)){
                    if (DBClassExample.history.get(i).getBorrowedBooks().contains(book)) {
                        throw new IllegalArgumentException("You can not borrow book that you already borrowed.");
                    } else {
                        BorrowedBook borrowedBook = new BorrowedBook(book,LocalDate.now().plusDays(14),LocalDate.now());
                        DBClassExample.history.get(i).getBorrowedBooks().add(borrowedBook);
                    }

            }

        }
    }

}

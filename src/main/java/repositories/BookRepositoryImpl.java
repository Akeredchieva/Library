package repositories;


import entities.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class BookRepositoryImpl implements BookRepository {

    List<Book> booksInLibrary;
    List<QueueForBorrow> queue;
    List<User> users;
    List<History> history;

    public BookRepositoryImpl() {
        booksInLibrary = new ArrayList<>();
        queue = new ArrayList<>();
        users = new ArrayList<>();
        history = new ArrayList<>();
    }

    @Override
    public void creatBook(Book book) {
        this.booksInLibrary.add(book);

    }

    @Override
    public boolean isBookAvailable(PaperBook book) {
        for (int i=0; i< booksInLibrary.size(); i++){
            if (book.equals(booksInLibrary.get(i))){
                PaperBook paperBook = (PaperBook) booksInLibrary.get(i);
                return paperBook.getNumberOfCopiesAvailable() > 0;
            }
        }
        throw new IllegalArgumentException("There is no such book.");
    }

    @Override
    public String downloadEBook(EBook eBook) {

        for (int i=0; i< booksInLibrary.size(); i++){
            if (booksInLibrary.get(i) instanceof EBook){
                if (booksInLibrary.get(i).getTitle().equals(eBook.getTitle())){
                    if (booksInLibrary.get(i).getAuthors().containsAll(eBook.getAuthors())){
                        return ((EBook) booksInLibrary.get(i)).getDownloadLink();
                    }
                }
            }
        }
        return null;
    }

    @Override
    public List<Book> findBookByTitle(String title) {
        List<Book> foundBooks = new ArrayList<>();
        for (Book book: booksInLibrary) {
            if(book.getTitle().equalsIgnoreCase(title)){
                    foundBooks.add(book);
            }
        }
        return foundBooks;
    }

    @Override
    public List<Book> findBookByGenres(String... genres) {
        List<Book> foundBooks = new ArrayList<>();
        for (Book book: booksInLibrary) {
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
        for (Book book: booksInLibrary) {
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
        for (Book book: booksInLibrary) {
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
            for (int i=0; i < booksInLibrary.size(); i++){
                if (book.equals(booksInLibrary.get(i))) {
                    PaperBook paperBookDB = (PaperBook) booksInLibrary.get(i);
                    int numberOfCopies = paperBookDB.getAllCopies() - 1;
                    booksInLibrary.remove(paperBookDB);
                    paperBookDB.setAllCopies(numberOfCopies);
                    booksInLibrary.add(paperBookDB);
                    break;
                }
            }
    }

    // TODO: vij kade go polzvash i napravi proverka za -1.
    // TODO: izchisti si metoda - imash povtoreniq na koda.
    @Override
    public int createQueryForWaiting(PaperBook book, User user){
        /*
          TODO: proverqva dali ima quety za tazi kniga. Ako ima proverqva(po username) dali tozi user e podal veche zaqvka.
          TODO: Ako e podal vrashta index-a mu ot list-a, a ako ne e do odbavq i otnovo vrashta index-a
        */
        if (this.queue.isEmpty()){
            // ako spisaka e prazen syzdavam go
            List<User> users = new ArrayList<>();
            users.add(user);
            List<QueueForBorrow> queueForBorrows = new ArrayList<>();
            QueueForBorrow queueForBorrow = new QueueForBorrow(users,book);
            queueForBorrows.add(queueForBorrow);
            this.queue.add(queueForBorrow);
        } else {
            for (int i = 0; i < this.queue.size(); i++) {
                // proverqvame dali knigata q ima v spisaka
                if (book.equals(this.queue.get(i).getBook())) {
                    for (int j = 0; j < this.queue.get(i).getWaitingUsers().size(); j++) {
                        //proverqvame dali user-a e veche v lista na chakashti
                        if (user.getCredentials().getUsername().equals(this.queue.get(i).getWaitingUsers().get(j).getCredentials().getUsername())) {
                            throw new IllegalArgumentException("You are presenting in the list of waiting.");
                        } else {
                            for (int k = 0; k < this.users.size(); k++) {
                                User userFromInside = this.users.get(k);
                                // dobavqme user-a v lista na chakashti
                                if (!user.getCredentials().getUsername().equals(userFromInside.getCredentials().getUsername())) {
                                    this.queue.get(i).getWaitingUsers().add(userFromInside);
                                    return this.queue.get(i).getWaitingUsers().size();
                                }

                            }
                        }
                    }
                } else {
                    //ako knigata q nqma q zapisvame s tozi user koito e napravil molbata
                    for (int k = 0; k < this.users.size(); k++) {
                        User userFromInside = this.users.get(k);
                        if (user.getCredentials().getUsername().equals(userFromInside.getCredentials().getUsername())) {
                            List<User> users = new ArrayList<>();
                            users.add(userFromInside);
                            QueueForBorrow queueForBorrow = new QueueForBorrow(users, book);
                            this.queue.add(queueForBorrow);
                            return this.queue.size();
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
        for (int i = 0; i< this.queue.size(); i++){
            if (book.equals(this.queue.get(i).getBook())){
                User userBorrowedBook = this.queue.get(i).getWaitingUsers().get(0);
                HistoryRepository historyRepository = new HistoryRepositoryImpl();
                List<BorrowedBook> borrowedBooks = historyRepository.getBorrowedBooks(userBorrowedBook.getCredentials().getUsername());
                for (int j=0; j< borrowedBooks.size(); j++){
                   if(borrowedBooks.get(i).getBook().getTitle().equals(book.getTitle())){
                       theDateForTaking =  borrowedBooks.get(i).getDateOfTaken();
                       break;
                   }
                }
                theDateForTaking = theDateForTaking.plusDays(this.queue.get(i).getWaitingUsers().size()*28);
                return LocalDate.now().until(theDateForTaking,ChronoUnit.DAYS);
            }
        }
        return -1;
    }

    @Override
    public String openOnlineBook(EBook eBook) {
        for (int i=0; i< booksInLibrary.size() ; i++){
            if (booksInLibrary.get(i) instanceof EBook){
                if (eBook.getTitle().equals(booksInLibrary.get(i).getTitle())){
                    if (eBook.getAuthors().containsAll(booksInLibrary.get(i).getAuthors())){
                        return ((EBook) booksInLibrary.get(i)).getOnlineReadingLink();
                    }
                }
            }
        }
        return null;
    }

    @Override
    public void setBookforBorrow(PaperBook book, String username) {
        for (int i = 0; i< this.history.size(); i++){
            if (this.history.get(i).getUser().getCredentials().getUsername().equals(username)){
                    if (this.history.get(i).getBorrowedBooks().contains(book)) {
                        throw new IllegalArgumentException("You can not borrow book that you already borrowed.");
                    } else {
                        BorrowedBook borrowedBook = new BorrowedBook(book);
                        this.history.get(i).getBorrowedBooks().add(borrowedBook);
                    }

            }

        }
    }

    public List<Book> getBooksInLibrary() {
        return this.booksInLibrary;
    }

    public void setBooksInLibrary(List<Book> booksInLibrary) {
       this.booksInLibrary = booksInLibrary;
    }
}

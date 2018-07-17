package repositories;


import entities.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class BookRepositoryImpl implements BookRepository {
    List<Book> booksInLibrary = new ArrayList<>();

    public List<QueueForBorrow> queue = new ArrayList<>();
    List<User> users = new ArrayList<>();
    public List<History> history = new ArrayList<>();

    public BookRepositoryImpl(List<Book> books, List<QueueForBorrow> queue, List<User> users, List<History> history ) {
        this.booksInLibrary = books;
        this.queue = queue;
        this.users = users;
        this.history = history;
    }

    @Override
    public void creatBook(Book book) {

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
            for (String genre: genres) {
                for (Book book: booksInLibrary) {
                    if (book.getGenre().equalsIgnoreCase(genre)) {
                        if (!foundBooks.contains(book)) {
                            foundBooks.add(book);
                        }
                    }
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
                        if (!foundBooks.contains(book)) {
                            foundBooks.add(book);
                        }
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
                if(fullNameAuthor.contains(fullName.toLowerCase()) || fullNameAuthorReverse.contains(fullName.toLowerCase())){
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

    @Override
    public int createQueryForWaiting(PaperBook book, String username){
        if (this.queue.isEmpty()){
            // ako spisaka e prazen syzdavam go
            for (int k = 0; k< this.users.size(); k++) {
                User user = this.users.get(k);
                if (username.equals(user.getCredentials().getUsername())) {
                    List<User> users = new ArrayList<>();
                    users.add(user);
                    QueueForBorrow queueForBorrow = new QueueForBorrow(users, book);
                    this.queue.add(queueForBorrow);
                    return this.queue.get(0).getWaitingUsers().size();
                }
            }
        } else {
            for (int i = 0; i < this.queue.size(); i++) {
                // proverqvame dali knigata q ima v spisaka
                if (book.equals(this.queue.get(i).getBook())) {
                    for (int j = 0; j < this.queue.get(i).getWaitingUsers().size(); j++) {
                        //proverqvame dali user-a e veche v lista na chakashti
                        if (username.equals(this.queue.get(i).getWaitingUsers().get(j).getCredentials().getUsername())) {
                            throw new IllegalArgumentException("You are presenting in the list of waiting.");
                        } else {
                            for (int k = 0; k < this.users.size(); k++) {
                                User user = this.users.get(k);
                                // dobavqme user-a v lista na chakashti
                                if (!username.equals(user.getCredentials().getUsername())) {
                                    this.queue.get(i).getWaitingUsers().add(user);
                                    return this.queue.get(i).getWaitingUsers().size();
                                }

                            }
                        }
                    }
                } else {
                    //ako knigata q nqma q zapisvame s tozi user koito e napravil molbata
                    for (int k = 0; k < this.users.size(); k++) {
                        User user = this.users.get(k);
                        if (username.equals(user.getCredentials().getUsername())) {
                            List<User> users = new ArrayList<>();
                            users.add(user);
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


    @Override
    public long bookAvailability(PaperBook book) {
        LocalDate theDateForTaking = LocalDate.now();
        for (int i = 0; i< this.queue.size(); i++){
            if (book.equals(this.queue.get(i).getBook())){
                User userBorrowedBook = this.queue.get(i).getWaitingUsers().get(0);
                HistoryRepository historyRepository = new HistoryRepositoryImpl(history);
                List<BorrowedBook> borrowedBooks = historyRepository.getBorrowedBooks(userBorrowedBook.getCredentials().getUsername());
                for (int j=0; j< borrowedBooks.size()-1; j++){
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
                        return ((EBook) booksInLibrary.get(i)).getOnlineReadingLink();
                }
            }
        }
        return null;
    }

    @Override
    public void setBookforBorrow(PaperBook book, String username) {
        for (int i = 0; i< this.history.size(); i++){
            if (this.history.get(i).getUser().getCredentials().getUsername().equals(username)){
                for (int j=0; j<this.history.get(i).getBorrowedBooks().size();j++) {
                    if (this.history.get(i).getBorrowedBooks().get(j).getBook().equals(book)) {
                        throw new IllegalArgumentException("You can not borrow book that you already borrowed.");
                    } else {
                        BorrowedBook borrowedBook = new BorrowedBook(book, LocalDate.now());
                        List<BorrowedBook> borrowedBooks = history.get(i).getBorrowedBooks();
                        borrowedBooks.add(borrowedBook);
                        history.get(i).setBorrowedBooks(borrowedBooks);
                    }
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

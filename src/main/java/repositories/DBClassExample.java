package repositories;

import entities.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public  class DBClassExample {
    static List<Book> booksInLibrary = new ArrayList<>();
    static List<User> users = new ArrayList<>();
    static List<QueueForBorrow> queue = new ArrayList<>();
    static List<History> history = new ArrayList<>();

    public DBClassExample() {
        Set<Author> authorsSet = new HashSet<>();
        Author author = new Author("Alexander", " Duma", "Englannd", 1994, 2003);
        authorsSet.add(author);
        Author author2 = new Author("Gosho ot pochivka", "", "BG", 1984);
        authorsSet.add(author2);
        Author author3 = new Author("Annie", "GFDS", "BG", 1994);
        authorsSet.add(author3);

        List<String> tags = new ArrayList<>();
        tags.add("aaaa");
        tags.add("bbb");
        tags.add("ccc");

        Book book = new EBook("AAAA", "comedy", "adsfsghjkhgfds", tags, "JGFBACSXDFGB", authorsSet, "www.sfgwr.com");
        Set<Author> authorsSet2 = new HashSet<>();
        Book book2 = new PaperBook("BBB", "horror", "poiuytrew", tags, "mnbvcx", authorsSet2, 23, 14);

        booksInLibrary.add(book);
        booksInLibrary.add(book2);

        Credentials credentials = new Credentials("apk94", "123456789");
        Address address = new Address("6-ti Septemvri", "Bulgaria", "Plovdiv");
        PersonInfo personInfo = new PersonInfo("Ana", "Keredchieva", 24, Sex.FEMALE, address);

        User user = new User(credentials, "annie.kere@gmail.com", true, personInfo);

        credentials = new Credentials("wwww","wertyu");
        address = new Address("kjhgfd","Bulgaria","Smolqn");
        personInfo = new PersonInfo("QQQQQ", "PPPPP",18, Sex.MALE, address);

        User user2 = new User(credentials, "grbfevd@abv.bg", true, personInfo);

        credentials = new Credentials("pppp","uuuu");
        address = new Address("lkjhg","Romania","Timishioara");
        personInfo = new PersonInfo("888888", "tttttt",20, Sex.MALE, address);

        User user3 = new User(credentials, "dadada@abv.bg", true, personInfo);

        users.add(user);
        users.add(user2);
        users.add(user3);


        QueueForBorrow waitingUsers = new QueueForBorrow(users, (PaperBook) book2);
        queue.add(waitingUsers);


        List<HistoryEntry> historyOfBooks = new ArrayList<>();
        historyOfBooks.add(new HistoryEntry(Status.READ, book,LocalDate.of(2018, 5, 2)));

        List<BorrowedBook> borrowedBooks = new ArrayList<>();
        borrowedBooks.add(new BorrowedBook(book,LocalDate.of(2018,5, 2),LocalDate.of(2018,5, 2)));

        history.add(new History(user, historyOfBooks, borrowedBooks));

    }





}

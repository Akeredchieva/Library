package services;

import entities.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// TODO: Ako imash vreme opravi go i go napravi na DAO Pattern ili razhvarli dannite po repository-tata.
public  class DBClassExample {
    public static List<Book> booksInLibrary = new ArrayList<>();
    static List<User> users = new ArrayList<>();
    public static List<QueueForBorrow> queue = new ArrayList<>();
    public static List<History> history = new ArrayList<>();

    public DBClassExample() {
        Set<Author> authorsSet = new HashSet<>();
        Set<Author> authorsSet2 = new HashSet<>();

        List<String> tags = new ArrayList<>();

        Author author = new Author("Alexander", " Duma", "Englannd", 1994, 2003);
        Author author2 = new Author("Gosho", "ot pochivka", "BG", 1984);
        Author author3 = new Author("Annie", "GFDS", "BG", 1994);

        authorsSet.add(author2);
        authorsSet.add(author);
        authorsSet.add(author3);



        tags.add("aaaa");
        tags.add("bbb");
        tags.add("ccc");

        Book book = new EBook("AAAA", "comedy", "adsfsghjkhgfds", tags, "JGFBACSXDFGB", authorsSet, "www.sfgwr.com");
        Book book2 = new PaperBook("BBB", "horror", "poiuytrew", tags, "mnbvcx", authorsSet2, 23, 0);


        booksInLibrary.add(book);
        booksInLibrary.add(book2);

        Credentials credentials = new Credentials("apk94", "123456789");
        Credentials credential3 = new Credentials("wwww","wertyu");
        Credentials credentials2 = new Credentials("pppp","uuuu");

        Address address = new Address("6-ti Septemvri", "Bulgaria", "Plovdiv");
        Address address3 = new Address("kjhgfd","Bulgaria","Smolqn");
        Address address2 = new Address("lkjhg","Romania","Timishioara");

        PersonInfo personInfo = new PersonInfo("Ana", "Keredchieva", 24, Sex.FEMALE, address);
        PersonInfo personInfo2 = new PersonInfo("888888", "tttttt",20, Sex.MALE, address2);
        PersonInfo personInfo3 = new PersonInfo("QQQQQ", "PPPPP",18, Sex.MALE, address3);

        User user = new User(credentials, "annie.kere@gmail.com", true, personInfo);
        User user2 = new User(credentials2, "grbfevd@abv.bg", true, personInfo2);
        User user3 = new User(credential3, "dadada@abv.bg", true, personInfo3);

        users.add(user);
        users.add(user2);
        users.add(user3);


        QueueForBorrow waitingUsers = new QueueForBorrow(users, (PaperBook) book2);
        queue.add(waitingUsers);


        List<HistoryEntry> historyOfBooks = new ArrayList<>();
        historyOfBooks.add(new HistoryEntry(Status.READ, book,LocalDate.of(2018, 5, 2)));

        List<BorrowedBook> borrowedBooks = new ArrayList<>();
        //borrowedBooks.add(new BorrowedBook(book,LocalDate.of(2018,7, 12)));
        borrowedBooks.add(new BorrowedBook((PaperBook)book2,LocalDate.of(2018,7, 12)));


        history.add(new History(user, historyOfBooks, borrowedBooks));

    }





}

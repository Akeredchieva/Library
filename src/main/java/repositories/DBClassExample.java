package repositories;

import entities.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public  class DBClassExample {
    public static List<Book> booksInLibrary = new ArrayList<>();
    static List<User> users = new ArrayList<>();
    private Set<Author> authorsSet = new HashSet<>();
    List<String> tags2 = new ArrayList<>();
    List<String> tags = new ArrayList<>();
    public static List<QueueForBorrow> queue = new ArrayList<>();
    private Set<Author> authorsSet2;
    public static List<History> history = new ArrayList<>();
    public static List<HistoryEntry> historyOfBooks = new ArrayList<>();
    public static List<BorrowedBook> borrowedBooks = new ArrayList<>();

    Book book;
    Book book2;

    User user;
    User user2;
    User user3;

    Credentials credentials;
    PersonInfo personInfo;
    Address address;

    QueueForBorrow waitingUsers;

    private Author author = new Author("Alexander"," Duma", "Englannd",1994,2003);
    Author author2 = new Author("Gosho ot pochivka","", "BG", 1984);
    Author author3 = new Author("Annie","GFDS", "BG", 1994);
    Author author4 = new Author("DDDD","GFGFEWRBT", "Englannd",2000);
    Author author5 = new Author("revfbgf ","WWWWWW", "BG", 1984);
    Author author6 = new Author("YYYYY RRR","TTTTT", "BG", 1899, 2001);

    public DBClassExample() {
        authorsSet.add(author);
        authorsSet.add(author);
        authorsSet.add(author);

        tags.add("aaaa");
        tags.add("bbb");
        tags.add("ccc");

        book = new EBook("AAAA","comedy","adsfsghjkhgfds",tags,"JGFBACSXDFGB",authorsSet,"www.sfgwr.com");
        authorsSet2 = new HashSet<>();
        book2 = new PaperBook("BBB","horror","poiuytrew",tags,"mnbvcx",authorsSet2,23,14);

        booksInLibrary.add(book);
        booksInLibrary.add(book2);

        credentials = new Credentials("apk94","123456789");
        address = new Address("6-ti Septemvri","Bulgaria","Plovdiv");
        personInfo = new PersonInfo("Ana", "Keredchieva",24, Sex.FEMALE, address);

        user = new User(credentials,"annie.kere@gmail.com",true,personInfo);

        credentials = new Credentials("wwww","wertyu");
        address = new Address("kjhgfd","Bulgaria","Smolqn");
        personInfo = new PersonInfo("QQQQQ", "PPPPP",18, Sex.MALE, address);

        user2 = new User(credentials,"grbfevd@abv.bg",true,personInfo);

        credentials = new Credentials("pppp","uuuu");
        address = new Address("lkjhg","Romania","Timishioara");
        personInfo = new PersonInfo("888888", "tttttt",20, Sex.MALE, address);

        user3 = new User(credentials,"dadada@abv.bg",true,personInfo);

        users.add(user);
        users.add(user2);
        users.add(user3);


        waitingUsers = new QueueForBorrow(users,(PaperBook) book2);
        queue.add(waitingUsers);


        historyOfBooks.add(new HistoryEntry(Status.READ,book,LocalDate.of(2018,05, 02)));

        borrowedBooks.add(new BorrowedBook(book,LocalDate.of(2018,05, 02),LocalDate.of(2018,05, 02)));

        history.add(new History(user, historyOfBooks, borrowedBooks));

    }





}

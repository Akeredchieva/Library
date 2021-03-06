package services;


import entities.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

// TODO: Opravi si dannite v listovete.
public class UserServiceTest {

    Book eBook;
    List<String> tags;
    Set<Author> authorsSetEBook;
    Book paperBook;
    Set<Author> authorsSetPaperBook = new HashSet<>();
    DBClassExample db;
    List<User> users = new ArrayList<>();
    User user;
    User user2;
    User user3;
    DBClassExample DBClassExample;
    PaperBook paperBook2;
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Before
    public void setUp(){

        DBClassExample DBClassExample = new DBClassExample();

        tags = new ArrayList<>();
        tags.add("aaaa");
        tags.add("bbb");
        tags.add("ccc");

        authorsSetEBook = new HashSet<>();
        Author author = new Author("Alexander", " Duma", "Englannd", 1994, 2003);
        authorsSetEBook.add(author);
        Author author2 = new Author("Gosho ot pochivka", "", "BG", 1984);
        authorsSetEBook.add(author2);
        Author author3 = new Author("Annie", "GFDS", "BG", 1994);
        authorsSetEBook.add(author3);

        authorsSetPaperBook = new HashSet<>();
        author = new Author("Alexander", " Duma", "Englannd", 1994, 2003);
        authorsSetPaperBook.add(author);
        author2 = new Author("Gosho ot pochivka", "", "BG", 1984);
        authorsSetPaperBook.add(author2);
        author3 = new Author("Annie", "GFDS", "BG", 1994);
        authorsSetPaperBook.add(author3);

        eBook = new EBook("AAAA", "comedy", "adsfsghjkhgfds", tags, "JGFBACSXDFGB", authorsSetEBook, "www.sfgwr.com");

        paperBook = new PaperBook("BBB", "horror", "poiuytrew", tags, "mnbvcx", authorsSetPaperBook, 23, 14);

        authorsSetPaperBook = new HashSet<>();
        author = new Author("Alexander", " Duma", "Englannd", 1994, 2003);
        authorsSetPaperBook.add(author);
        author2 = new Author("Gosho ot pochivka", "", "BG", 1984);
        authorsSetPaperBook.add(author2);
        author3 = new Author("Annie", "GFDS", "BG", 1994);
        authorsSetPaperBook.add(author3);
        paperBook2 = new PaperBook("CCC", "horror", "poiuytrew", tags, "mnbvcx", authorsSetPaperBook, 23, 14);

        Credentials credentials = new Credentials("apk94", "123456789");
        Address address = new Address("6-ti Septemvri", "Bulgaria", "Plovdiv");
        PersonInfo personInfo = new PersonInfo("Ana", "Keredchieva", 24, Sex.FEMALE, address);

        user = new User(credentials, "annie.kere@gmail.com", true, personInfo);

        credentials = new Credentials("wwww","wertyu");
        address = new Address("kjhgfd","Bulgaria","Smolqn");
        personInfo = new PersonInfo("QQQQQ", "PPPPP",18, Sex.MALE, address);

        user2 = new User(credentials, "grbfevd@abv.bg", true, personInfo);

        credentials = new Credentials("pppp","uuuu");
        address = new Address("lkjhg","Romania","Timishioara");
        personInfo = new PersonInfo("888888", "tttttt",20, Sex.MALE, address);

        user3 = new User(credentials, "dadada@abv.bg", true, personInfo);

        users.add(user);
        users.add(user2);
        users.add(user3);

        QueueForBorrow waitingUsers = new QueueForBorrow(users, (PaperBook) paperBook);
        DBClassExample.queue.add(waitingUsers);


        List<HistoryEntry> historyOfBooks = new ArrayList<>();
        historyOfBooks.add(new HistoryEntry(Status.READ, paperBook,LocalDate.of(2018, 5, 2)));

        List<BorrowedBook> borrowedBooks = new ArrayList<>();
        borrowedBooks.add(new BorrowedBook((PaperBook) paperBook,LocalDate.of(2018,7, 12)));
        //borrowedBooks.add(new BorrowedBook(eBook,LocalDate.of(2018,7, 12)));


        DBClassExample.history.add(new History(user, historyOfBooks, borrowedBooks));
        services.DBClassExample.booksInLibrary.add(paperBook2);

    }

    @Test
    public void checkExpirationDateForElectronicBook() {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("You can not see expiration date on electronic book.");
        UserService userService = new UserService();
        userService.checkExpirationDate(eBook);
        assertEquals(expectedEx,userService.checkExpirationDate(eBook));
    }

    @Test
    public void checkExpirationDateForPaperBookWhichNotPresent(){
        paperBook = new PaperBook("BBASB", "horror", "poiuytrew", tags, "mnbvcx", authorsSetPaperBook, 23, 14);
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("This book is not present in the history.");
        UserService userService = new UserService();
        userService.checkExpirationDate(paperBook);
        assertEquals(expectedEx,userService.checkExpirationDate(paperBook));
    }

    @Test
    public void checkExpirationDateForPaperBookWhichPresent(){
        UserService userService = new UserService();
        userService.checkExpirationDate(paperBook);
        assertEquals(LocalDate.of(2018,7, 26),userService.checkExpirationDate(paperBook));
    }

    @Test
    public void viewHistoryForUserWhoDoNotHave() {
        UserService userService = new UserService();
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("This user does not have history.");
        assertEquals(expectedEx, userService.viewHistory(user3.getCredentials().getUsername()));
    }

    @Test
    public void viewHistory() {
        UserService userService = new UserService();
        String expected = "book: Book: title: AAAA\n" +
                "genre: comedy\n" +
                "summary: adsfsghjkhgfds\n" +
                "tags: [aaaa, bbb, ccc]\n" +
                "isbn: JGFBACSXDFGB\n" +
                "authors: [Author{firstName='Annie', lastName='GFDS', country='BG', dateOfBirth=1994, dateOfDeath=0}, Author{firstName='Gosho', lastName='ot pochivka', country='BG', dateOfBirth=1984, dateOfDeath=0}, Author{firstName='Alexander', lastName=' Duma', country='Englannd', dateOfBirth=1994, dateOfDeath=2003}]\n" +
                "EBook\n" +
                "onlineReadingLink: www.sfgwr.com\n" +
                "downloadLink: null\n" +
                "\n" +
                "status: READ\n" +
                "dateOfReturn: 2018-05-02";
        String actualHistory = userService.viewHistory(user.getCredentials().getUsername());
        assertEquals(expected,actualHistory);
    }

    @Test
    public void viewBorrowedBooks() {
        UserService userService = new UserService();
        String actualHistory = userService.viewBorrowedBooks(user.getCredentials().getUsername());
        String expected = "book: Book: title: BBB\n" +
                "genre: horror\n" +
                "summary: poiuytrew\n" +
                "tags: [aaaa, bbb, ccc]\n" +
                "isbn: mnbvcx\n" +
                "authors: []\n" +
                "PaperBook: \n" +
                "numberOfCopiesAvailable: 0\n" +
                "allCopies: 23\n" +
                "\n" +
                "dateOfReturn: 2018-07-26\n" +
                "dateOfTaken: 2018-07-12\n";
        assertEquals(expected,actualHistory);
    }

    @Test
    public void viewBorrowedBooksOfUserWhoDoesNotBorrow() {
        UserService userService = new UserService();
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("The is no borrowed books.");
        assertEquals(expectedEx,userService.viewBorrowedBooks(user3.getCredentials().getUsername()));
    }

    @Test
    public void requestForBorrowingBookWhichHasCopies() {
        UserService userService = new UserService();
        assertEquals("The book is borrowed.", userService.requestForBorrowingBook(paperBook2,user.getCredentials().getUsername()));

    }

    @Test
    public void requestForBorrowingBookWhichHasNoCopies() {

        DBClassExample DBClassExample = new DBClassExample();
        UserService userService = new UserService();
        Book book2 = new PaperBook("BBB", "horror", "poiuytrew", tags, "mnbvcx", authorsSetPaperBook, 23, 0);
        //userService.requestForBorrowingBook(paperBook,user.getCredentials().getUsername());
        Credentials credentials = new Credentials("pkjh","fggbb");
        Address address = new Address("[[[[[","Bulgaria","Smolqn");
        PersonInfo personInfo = new PersonInfo("rrrr", "yyyyy",18, Sex.MALE, address);

        user2 = new User(credentials, "grbfevd@abv.bg", true, personInfo);
        String expected = "You number in the query is 13.\n" +
                "And the book will be available after: 364days.";
        assertEquals(expected, userService.requestForBorrowingBook(paperBook,user2.getCredentials().getUsername()));

    }

    @Test
    public void requestForBorrowingBookFromUserWhichPresentingInTheList() {
        UserService userService = new UserService();
        Book book2 = new PaperBook("BBB", "horror", "poiuytrew", tags, "mnbvcx", authorsSetPaperBook, 23, 4);
        //userService.requestForBorrowingBook(paperBook,user.getCredentials().getUsername());
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("You are presenting in the list of waiting.");
        assertEquals(expectedEx, userService.requestForBorrowingBook(paperBook,user.getCredentials().getUsername()));

    }
}
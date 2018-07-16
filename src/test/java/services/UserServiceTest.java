package services;


import entities.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import repositories.BookRepository;
import repositories.BookRepositoryImpl;
import repositories.HistoryRepository;
import repositories.HistoryRepositoryImpl;

import java.awt.print.Paper;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

// TODO: Opravi si dannite v listovete.
public class UserServiceTest {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();
/*
    @Before
    public void setUp(){

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
        db.queue.add(waitingUsers);


        List<HistoryEntry> historyOfBooks = new ArrayList<>();
        historyOfBooks.add(new HistoryEntry(Status.READ, paperBook,LocalDate.of(2018, 5, 2)));

        List<BorrowedBook> borrowedBooks = new ArrayList<>();
        borrowedBooks.add(new BorrowedBook((PaperBook) paperBook,LocalDate.of(2018,7, 12)));
        //borrowedBooks.add(new BorrowedBook(eBook,LocalDate.of(2018,7, 12)));


        db.history.add(new History(user, historyOfBooks, borrowedBooks));

    }
*/
    @Test
    public void checkExpirationDateForElectronicBook() {

        BookRepository bookRepository = new BookRepositoryImpl();
        HistoryRepository historyRepository = new HistoryRepositoryImpl();
        BorrowingService borrowingService = new BorrowingService(bookRepository,historyRepository);
        List<String> tags = new ArrayList<>();
        tags.add("aaaa");
        tags.add("bbb");
        tags.add("ccc");
        Set<Author> authorsSetEBook = new HashSet<>();
        Author author = new Author("Alexander", " Duma", "Englannd", 1994, 2003);
        authorsSetEBook.add(author);
        Author author2 = new Author("Gosho ot pochivka", "", "BG", 1984);
        authorsSetEBook.add(author2);
        Author author3 = new Author("Annie", "GFDS", "BG", 1994);
        authorsSetEBook.add(author3);
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("You can not see expiration date on electronic book.");
        UserService userService = new UserService(bookRepository,historyRepository,borrowingService);
        EBook eBook = new EBook("AAAA", "comedy", "adsfsghjkhgfds", tags, "JGFBACSXDFGB", authorsSetEBook, "www.sfgwr.com");
        userService.checkExpirationDate(eBook);
        assertEquals(expectedEx,userService.checkExpirationDate(eBook));
    }

    @Test
    public void checkExpirationDateForPaperBookWhichNotPresent(){
        BookRepository bookRepository = new BookRepositoryImpl();
        HistoryRepository historyRepository = new HistoryRepositoryImpl();
        BorrowingService borrowingService = new BorrowingService(bookRepository,historyRepository);
        List<String> tags = new ArrayList<>();
        tags.add("aaaa");
        tags.add("bbb");
        tags.add("ccc");
        Set<Author> authorsSetPaperBook = new HashSet<>();
        Author author = new Author("Alexander", " Duma", "Englannd", 1994, 2003);
        authorsSetPaperBook.add(author);
        Author author2 = new Author("Gosho ot pochivka", "", "BG", 1984);
        authorsSetPaperBook.add(author2);
        Author author3 = new Author("Annie", "GFDS", "BG", 1994);
        authorsSetPaperBook.add(author3);

        PaperBook paperBook = new PaperBook("BBASB", "horror", "poiuytrew", tags, "mnbvcx", authorsSetPaperBook, 23, 14);
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("This book is not present in the history.");
        UserService userService = new UserService(bookRepository,historyRepository,borrowingService);
        userService.checkExpirationDate(paperBook);
        assertEquals(expectedEx,userService.checkExpirationDate(paperBook));
    }

    @Test
    public void checkExpirationDateForPaperBookWhichPresent(){
        BookRepository bookRepository = new BookRepositoryImpl();
        HistoryRepository historyRepository = new HistoryRepositoryImpl();
        BorrowingService borrowingService = new BorrowingService(bookRepository,historyRepository);
        List<String> tags = new ArrayList<>();
        tags.add("aaaa");
        tags.add("bbb");
        tags.add("ccc");
        Set<Author> authorsSetPaperBook = new HashSet<>();
        Author author = new Author("Alexander", " Duma", "Englannd", 1994, 2003);
        authorsSetPaperBook.add(author);
        Author author2 = new Author("Gosho ot pochivka", "", "BG", 1984);
        authorsSetPaperBook.add(author2);
        Author author3 = new Author("Annie", "GFDS", "BG", 1994);
        authorsSetPaperBook.add(author3);
        PaperBook paperBook = new PaperBook("BBB", "horror", "poiuytrew", tags, "mnbvcx", authorsSetPaperBook, 23, 14);
        Credentials credentials = new Credentials("apk94", "123456789");
        Address address = new Address("6-ti Septemvri", "Bulgaria", "Plovdiv");
        PersonInfo personInfo = new PersonInfo("Ana", "Keredchieva", 24, Sex.FEMALE, address);

        User user = new User(credentials, "annie.kere@gmail.com", true, personInfo);
        historyRepository.addBookToBorrowed(user,paperBook);
        UserService userService = new UserService(bookRepository,historyRepository,borrowingService);
        assertEquals(LocalDate.now().plusDays(14),userService.checkExpirationDate(paperBook));
    }

    // Change the value of LocalDate on rows 94 and 95 with older date or add new Book with older date.
    @Test
    public void makePostponementForBookWithExpiredReturnDate() {
        BookRepository bookRepository = new BookRepositoryImpl();
        HistoryRepository historyRepository = new HistoryRepositoryImpl();
        BorrowingService borrowingService = new BorrowingService(bookRepository,historyRepository);
        List<String> tags = new ArrayList<>();
        tags.add("aaaa");
        tags.add("bbb");
        tags.add("ccc");
        Set<Author> authorsSetPaperBook = new HashSet<>();
        Author author = new Author("Alexander", " Duma", "Englannd", 1994, 2003);
        authorsSetPaperBook.add(author);
        Author author2 = new Author("Gosho ot pochivka", "", "BG", 1984);
        authorsSetPaperBook.add(author2);
        Author author3 = new Author("Annie", "GFDS", "BG", 1994);
        authorsSetPaperBook.add(author3);
        PaperBook paperBook = new PaperBook("BBB", "horror", "poiuytrew", tags, "mnbvcx", authorsSetPaperBook, 23, 14);
        Credentials credentials = new Credentials("apk94", "123456789");
        Address address = new Address("6-ti Septemvri", "Bulgaria", "Plovdiv");
        PersonInfo personInfo = new PersonInfo("Ana", "Keredchieva", 24, Sex.FEMALE, address);

        User user = new User(credentials, "annie.kere@gmail.com", true, personInfo);
        historyRepository.addBookToBorrowed(user,paperBook);
        for (int i=0; i<historyRepository.getBorrowedBooks(user.getCredentials().getUsername()).size(); i++) {
            if (paperBook.equals(historyRepository.getBorrowedBooks(user.getCredentials().getUsername()).get(i).getBook())){
                historyRepository.getBorrowedBooks(user.getCredentials().getUsername()).get(i).setDateOfReturn(LocalDate.of(2017,2,2));
            }
        }
        UserService userService = new UserService(bookRepository,historyRepository,borrowingService);

        LocalDate ld = LocalDate.now();
        try {
            userService.makePostponement(user, paperBook, 16);
        } catch(IllegalArgumentException iae) {
            assertEquals("The return date is expired with "
                    + (ld.until(LocalDate.now())).getDays()
                    +" days.And " + (ld.until(LocalDate.now())).getMonths()
                    + " months.Please return your book.",
                    iae.getMessage());
        }

    }

    // Change the value of LocalDate on rows 94 and 95 with 2018-07-12 or add new Book with this date.
    @Test
    public void makePostponementWithCorrectDates() {
        BookRepository bookRepository = new BookRepositoryImpl();
        HistoryRepository historyRepository = new HistoryRepositoryImpl();
        BorrowingService borrowingService = new BorrowingService(bookRepository,historyRepository);
        List<String> tags = new ArrayList<>();
        tags.add("aaaa");
        tags.add("bbb");
        tags.add("ccc");
        Set<Author> authorsSetPaperBook = new HashSet<>();
        Author author = new Author("Alexander", " Duma", "Englannd", 1994, 2003);
        authorsSetPaperBook.add(author);
        Author author2 = new Author("Gosho ot pochivka", "", "BG", 1984);
        authorsSetPaperBook.add(author2);
        Author author3 = new Author("Annie", "GFDS", "BG", 1994);
        authorsSetPaperBook.add(author3);
        PaperBook paperBook = new PaperBook("BBB", "horror", "poiuytrew", tags, "mnbvcx", authorsSetPaperBook, 23, 14);
        Credentials credentials = new Credentials("apk94", "123456789");
        Address address = new Address("6-ti Septemvri", "Bulgaria", "Plovdiv");
        PersonInfo personInfo = new PersonInfo("Ana", "Keredchieva", 24, Sex.FEMALE, address);

        User user = new User(credentials, "annie.kere@gmail.com", true, personInfo);
        historyRepository.addBookToBorrowed(user,paperBook);

        UserService userService = new UserService(bookRepository,historyRepository,borrowingService);
        userService.makePostponement(user, paperBook, 16);
        historyRepository.addBookToBorrowed(user,paperBook);
        LocalDate ld = LocalDate.now();
        for (int i=0; i<historyRepository.getBorrowedBooks(user.getCredentials().getUsername()).size(); i++) {
            if (paperBook.equals(historyRepository.getBorrowedBooks(user.getCredentials().getUsername()).get(i).getBook())){
               ld = historyRepository.getBorrowedBooks(user.getCredentials().getUsername()).get(i).getDateOfReturn();
            }
        }
        assertEquals(LocalDate.now().plusDays(14),ld);
    }

    @Test
    public void makePostponementWithTooManyDays() {
        BookRepository bookRepository = new BookRepositoryImpl();
        HistoryRepository historyRepository = new HistoryRepositoryImpl();
        BorrowingService borrowingService = new BorrowingService(bookRepository,historyRepository);
        List<String> tags = new ArrayList<>();
        tags.add("aaaa");
        tags.add("bbb");
        tags.add("ccc");
        Set<Author> authorsSetPaperBook = new HashSet<>();
        Author author = new Author("Alexander", " Duma", "Englannd", 1994, 2003);
        authorsSetPaperBook.add(author);
        Author author2 = new Author("Gosho ot pochivka", "", "BG", 1984);
        authorsSetPaperBook.add(author2);
        Author author3 = new Author("Annie", "GFDS", "BG", 1994);
        authorsSetPaperBook.add(author3);
        PaperBook paperBook = new PaperBook("BBB", "horror", "poiuytrew", tags, "mnbvcx", authorsSetPaperBook, 23, 14);
        Credentials credentials = new Credentials("apk94", "123456789");
        Address address = new Address("6-ti Septemvri", "Bulgaria", "Plovdiv");
        PersonInfo personInfo = new PersonInfo("Ana", "Keredchieva", 24, Sex.FEMALE, address);

        User user = new User(credentials, "annie.kere@gmail.com", true, personInfo);
        historyRepository.addBookToBorrowed(user,paperBook);

        UserService userService = new UserService(bookRepository,historyRepository,borrowingService);
        try {
            userService.makePostponement(user, paperBook, 160);
        } catch (IllegalArgumentException iae) {
            assertEquals("You can not make so long postponement.",iae.getMessage());
        }
    }

    @Test
    public void makePostponementForEBook() {
        BookRepository bookRepository = new BookRepositoryImpl();
        HistoryRepository historyRepository = new HistoryRepositoryImpl();
        BorrowingService borrowingService = new BorrowingService(bookRepository,historyRepository);
        List<String> tags = new ArrayList<>();
        tags.add("aaaa");
        tags.add("bbb");
        tags.add("ccc");
        Set<Author> authorsSetPaperBook = new HashSet<>();
        Author author = new Author("Alexander", " Duma", "Englannd", 1994, 2003);
        authorsSetPaperBook.add(author);
        Author author2 = new Author("Gosho ot pochivka", "", "BG", 1984);
        authorsSetPaperBook.add(author2);
        Author author3 = new Author("Annie", "GFDS", "BG", 1994);
        authorsSetPaperBook.add(author3);
        PaperBook paperBook = new PaperBook("BBB", "horror", "poiuytrew", tags, "mnbvcx", authorsSetPaperBook, 23, 14);
        Credentials credentials = new Credentials("apk94", "123456789");
        Address address = new Address("6-ti Septemvri", "Bulgaria", "Plovdiv");
        PersonInfo personInfo = new PersonInfo("Ana", "Keredchieva", 24, Sex.FEMALE, address);

        User user = new User(credentials, "annie.kere@gmail.com", true, personInfo);


        UserService userService = new UserService(bookRepository,historyRepository,borrowingService);

        EBook eBook = new EBook("AAAA", "comedy", "adsfsghjkhgfds", tags, "JGFBACSXDFGB", authorsSetPaperBook, "www.sfgwr.com");

        try {
            userService.makePostponement(user, eBook, 160);
        } catch (IllegalArgumentException iae) {
            assertEquals("This book can not be postponement, because it is E-Book.",iae.getMessage());
        }
    }

    // TODO: Opravi si testa - toq string e ujasen!!!
    @Test
    public void viewHistoryForUserWhoDoNotHave() {
        BookRepository bookRepository = new BookRepositoryImpl();
        HistoryRepository historyRepository = new HistoryRepositoryImpl();
        BorrowingService borrowingService = new BorrowingService(bookRepository,historyRepository);
        List<String> tags = new ArrayList<>();
        tags.add("aaaa");
        tags.add("bbb");
        tags.add("ccc");
        Set<Author> authorsSetPaperBook = new HashSet<>();
        Author author = new Author("Alexander", " Duma", "Englannd", 1994, 2003);
        authorsSetPaperBook.add(author);
        Author author2 = new Author("Gosho ot pochivka", "", "BG", 1984);
        authorsSetPaperBook.add(author2);
        Author author3 = new Author("Annie", "GFDS", "BG", 1994);
        authorsSetPaperBook.add(author3);
        PaperBook paperBook = new PaperBook("BBB", "horror", "poiuytrew", tags, "mnbvcx", authorsSetPaperBook, 23, 14);
        Credentials credentials = new Credentials("apk94", "123456789");
        Address address = new Address("6-ti Septemvri", "Bulgaria", "Plovdiv");
        PersonInfo personInfo = new PersonInfo("Ana", "Keredchieva", 24, Sex.FEMALE, address);

        User user = new User(credentials, "annie.kere@gmail.com", true, personInfo);

        credentials = new Credentials("pppp","uuuu");
        address = new Address("lkjhg","Romania","Timishioara");
        personInfo = new PersonInfo("888888", "tttttt",20, Sex.MALE, address);

        User user3 = new User(credentials, "dadada@abv.bg", true, personInfo);

        UserService userService = new UserService(bookRepository,historyRepository,borrowingService);

        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("This user does not have history.");


        assertEquals(expectedEx, userService.viewHistory(user3.getCredentials().getUsername()));
    }

    @Test
    public void viewHistory() {

        BookRepository bookRepository = new BookRepositoryImpl();
        HistoryRepository historyRepository = new HistoryRepositoryImpl();
        BorrowingService borrowingService = new BorrowingService(bookRepository,historyRepository);
        List<String> tags = new ArrayList<>();
        tags.add("aaaa");
        tags.add("bbb");
        tags.add("ccc");
        Set<Author> authorsSetPaperBook = new HashSet<>();
        Author author = new Author("Alexander", " Duma", "Englannd", 1994, 2003);
        authorsSetPaperBook.add(author);
        Author author2 = new Author("Gosho ot pochivka", "", "BG", 1984);
        authorsSetPaperBook.add(author2);
        Author author3 = new Author("Annie", "GFDS", "BG", 1994);
        authorsSetPaperBook.add(author3);
        PaperBook paperBook = new PaperBook("BBB", "horror", "poiuytrew", tags, "mnbvcx", authorsSetPaperBook, 23, 14);
        Credentials credentials = new Credentials("apk94", "123456789");
        Address address = new Address("6-ti Septemvri", "Bulgaria", "Plovdiv");
        PersonInfo personInfo = new PersonInfo("Ana", "Keredchieva", 24, Sex.FEMALE, address);

        User user = new User(credentials, "annie.kere@gmail.com", true, personInfo);

        historyRepository.addBookToHistory(user,paperBook,Status.READ);
        String expected = "book: Book: title: BBB\n" +
                "genre: horror\n" +
                "summary: poiuytrew\n" +
                "tags: [aaaa, bbb, ccc]\n" +
                "isbn: mnbvcx\n" +
                "authors: [Annie, GFDS, BG, 1994, 0\n" +
                ", Gosho ot pochivka, , BG, 1984, 0\n" +
                ", Alexander,  Duma, Englannd, 1994, 2003\n" +
                "]\n" +
                "PaperBook: \n" +
                "numberOfCopiesAvailable: 14\n" +
                "allCopies: 23\n" +
                "\n" +
                "status: READ\n" +
                "dateOfReturn: 2018-07-16";
        UserService userService = new UserService(bookRepository,historyRepository,borrowingService);

        String actualHistory = userService.viewHistory(user.getCredentials().getUsername());
        assertEquals(expected,actualHistory);
    }

    @Test
    public void viewBorrowedBooks() {

        BookRepository bookRepository = new BookRepositoryImpl();
        HistoryRepository historyRepository = new HistoryRepositoryImpl();
        BorrowingService borrowingService = new BorrowingService(bookRepository,historyRepository);
        List<String> tags = new ArrayList<>();
        tags.add("aaaa");
        tags.add("bbb");
        tags.add("ccc");
        Set<Author> authorsSetPaperBook = new HashSet<>();
        Author author = new Author("Alexander", " Duma", "Englannd", 1994, 2003);
        authorsSetPaperBook.add(author);
        Author author2 = new Author("Gosho ot pochivka", "", "BG", 1984);
        authorsSetPaperBook.add(author2);
        Author author3 = new Author("Annie", "GFDS", "BG", 1994);
        authorsSetPaperBook.add(author3);
        PaperBook paperBook = new PaperBook("BBB", "horror", "poiuytrew", tags, "mnbvcx", authorsSetPaperBook, 23, 14);
        Credentials credentials = new Credentials("apk94", "123456789");
        Address address = new Address("6-ti Septemvri", "Bulgaria", "Plovdiv");
        PersonInfo personInfo = new PersonInfo("Ana", "Keredchieva", 24, Sex.FEMALE, address);

        User user = new User(credentials, "annie.kere@gmail.com", true, personInfo);

        historyRepository.addBookToBorrowed(user,paperBook);
        UserService userService = new UserService(bookRepository,historyRepository,borrowingService);

        String actualHistory = userService.viewBorrowedBooks(user.getCredentials().getUsername());
        String expected = "book: Book: title: BBB\n" +
                "genre: horror\n" +
                "summary: poiuytrew\n" +
                "tags: [aaaa, bbb, ccc]\n" +
                "isbn: mnbvcx\n" +
                "authors: [Alexander,  Duma, Englannd, 1994, 2003\n" +
                ", Gosho ot pochivka, , BG, 1984, 0\n" +
                ", Annie, GFDS, BG, 1994, 0\n" +
                "]\n" +
                "PaperBook: \n" +
                "numberOfCopiesAvailable: 14\n" +
                "allCopies: 23\n" +
                "\n" +
                "dateOfReturn: 2018-07-30\n" +
                "dateOfTaken: 2018-07-16\n";
        assertEquals(expected,actualHistory);
    }

    @Test
    public void viewBorrowedBooksOfUserWhoDoesNotBorrow() {
        BookRepository bookRepository = new BookRepositoryImpl();
        HistoryRepository historyRepository = new HistoryRepositoryImpl();
        BorrowingService borrowingService = new BorrowingService(bookRepository,historyRepository);
        List<String> tags = new ArrayList<>();
        tags.add("aaaa");
        tags.add("bbb");
        tags.add("ccc");
        Set<Author> authorsSetPaperBook = new HashSet<>();
        Author author = new Author("Alexander", " Duma", "Englannd", 1994, 2003);
        authorsSetPaperBook.add(author);
        Author author2 = new Author("Gosho ot pochivka", "", "BG", 1984);
        authorsSetPaperBook.add(author2);
        Author author3 = new Author("Annie", "GFDS", "BG", 1994);
        authorsSetPaperBook.add(author3);
        PaperBook paperBook = new PaperBook("BBB", "horror", "poiuytrew", tags, "mnbvcx", authorsSetPaperBook, 23, 14);
        Credentials credentials = new Credentials("apk94", "123456789");
        Address address = new Address("6-ti Septemvri", "Bulgaria", "Plovdiv");
        PersonInfo personInfo = new PersonInfo("Ana", "Keredchieva", 24, Sex.FEMALE, address);

        User user = new User(credentials, "annie.kere@gmail.com", true, personInfo);
        credentials = new Credentials("pppp","uuuu");
        address = new Address("lkjhg","Romania","Timishioara");
        personInfo = new PersonInfo("888888", "tttttt",20, Sex.MALE, address);

        User user3 = new User(credentials, "dadada@abv.bg", true, personInfo);
        UserService userService = new UserService(bookRepository,historyRepository,borrowingService);

        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("The is no borrowed books.");
        assertEquals(expectedEx,userService.viewBorrowedBooks(user3.getCredentials().getUsername()));
    }

    @Test
    public void requestForBorrowingBookWhichHasCopies() {
        BookRepository bookRepository = new BookRepositoryImpl();
        HistoryRepository historyRepository = new HistoryRepositoryImpl();
        BorrowingService borrowingService = new BorrowingService(bookRepository,historyRepository);
        List<String> tags = new ArrayList<>();
        tags.add("aaaa");
        tags.add("bbb");
        tags.add("ccc");
        Set<Author> authorsSetPaperBook = new HashSet<>();
        Author author = new Author("Alexander", " Duma", "Englannd", 1994, 2003);
        authorsSetPaperBook.add(author);
        Author author2 = new Author("Gosho ot pochivka", "", "BG", 1984);
        authorsSetPaperBook.add(author2);
        Author author3 = new Author("Annie", "GFDS", "BG", 1994);
        authorsSetPaperBook.add(author3);
        PaperBook paperBook = new PaperBook("BBB", "horror", "poiuytrew", tags, "mnbvcx", authorsSetPaperBook, 23, 14);
        Credentials credentials = new Credentials("apk94", "123456789");
        Address address = new Address("6-ti Septemvri", "Bulgaria", "Plovdiv");
        PersonInfo personInfo = new PersonInfo("Ana", "Keredchieva", 24, Sex.FEMALE, address);

        User user = new User(credentials, "annie.kere@gmail.com", true, personInfo);
        credentials = new Credentials("pppp","uuuu");
        address = new Address("lkjhg","Romania","Timishioara");
        personInfo = new PersonInfo("888888", "tttttt",20, Sex.MALE, address);

        User user3 = new User(credentials, "dadada@abv.bg", true, personInfo);
        UserService userService = new UserService(bookRepository,historyRepository,borrowingService);

        bookRepository.creatBook(paperBook);

        assertEquals("The book is borrowed.", userService.requestForBorrowingBook(paperBook,user));

    }

    @Test
    public void requestForBorrowingBookWhichHasNoCopies() {
        BookRepository bookRepository = new BookRepositoryImpl();
        HistoryRepository historyRepository = new HistoryRepositoryImpl();
        BorrowingService borrowingService = new BorrowingService(bookRepository,historyRepository);
        List<String> tags = new ArrayList<>();
        tags.add("aaaa");
        tags.add("bbb");
        tags.add("ccc");
        Set<Author> authorsSetPaperBook = new HashSet<>();
        Author author = new Author("Alexander", " Duma", "Englannd", 1994, 2003);
        authorsSetPaperBook.add(author);
        Author author2 = new Author("Gosho ot pochivka", "", "BG", 1984);
        authorsSetPaperBook.add(author2);
        Author author3 = new Author("Annie", "GFDS", "BG", 1994);
        authorsSetPaperBook.add(author3);
        PaperBook paperBook = new PaperBook("BBB", "horror", "poiuytrew", tags, "mnbvcx", authorsSetPaperBook, 23, 14);

        UserService userService = new UserService(bookRepository,historyRepository,borrowingService);

        Book book2 = new PaperBook("BBB", "horror", "poiuytrew", tags, "mnbvcx", authorsSetPaperBook, 23, 0);
        //userService.requestForBorrowingBook(paperBook,user.getCredentials().getUsername());
        Credentials credentials = new Credentials("pkjh","fggbb");
        Address address = new Address("[[[[[","Bulgaria","Smolqn");
        PersonInfo personInfo = new PersonInfo("rrrr", "yyyyy",18, Sex.MALE, address);
        bookRepository.creatBook(book2);
       User user2 = new User(credentials, "grbfevd@abv.bg", true, personInfo);
        String expected = "You number in the query is 4.\n" +
                "And the book will be available after: 112days.";
        Credentials credentials1 = new Credentials("apk94", "123456789");
        Address address1 = new Address("6-ti Septemvri", "Bulgaria", "Plovdiv");
        PersonInfo personInfo1 = new PersonInfo("Ana", "Keredchieva", 24, Sex.FEMALE, address);

        User user = new User(credentials, "annie.kere@gmail.com", true, personInfo);
        userService.requestForBorrowingBook(book2,user2);

        assertEquals(expected,userService.requestForBorrowingBook(book2,user));

    }
/*
    @Test
    public void requestForBorrowingBookFromUserWhichPresentingInTheList() {
        UserService userService = new UserService();
        db = new DBClassExample();
        Book book2 = new PaperBook("BBB", "horror", "poiuytrew", tags, "mnbvcx", authorsSetPaperBook, 23, 4);
        //userService.requestForBorrowingBook(paperBook,user.getCredentials().getUsername());
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("You are presenting in the list of waiting.");
        assertEquals(expectedEx, userService.requestForBorrowingBook(paperBook,user.getCredentials().getUsername()));

    }
    */
}
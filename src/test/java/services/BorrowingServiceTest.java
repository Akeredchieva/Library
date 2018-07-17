package services;

import entities.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class BorrowingServiceTest {


    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void makePostponementForBookWithExpiredReturnDate() {
        Credentials credentials = new Credentials("apk94", "123456789");
        Address address = new Address("6-ti Septemvri", "Bulgaria", "Plovdiv");
        PersonInfo personInfo = new PersonInfo("Ana", "Keredchieva", 24, Sex.FEMALE, address);
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

        User user = new User(credentials, "annie.kere@gmail.com", true, personInfo);
        Book paperBook = new PaperBook("BBB", "horror", "poiuytrew", tags, "mnbvcx", authorsSetPaperBook, 23, 14);

        BorrowingService borrowingService = new BorrowingService();

        LocalDate ld = LocalDate.now();
        for (int i = 0; i < DBClassExample.history.size(); i++) {
            if (DBClassExample.history.get(i).getUser().equals(user)){
                for(int j=0; j< DBClassExample.history.get(i).getBorrowedBooks().size(); j++){
                    if (DBClassExample.history.get(i).getBorrowedBooks().get(j).getBook().equals(paperBook)){
                        ld = DBClassExample.history.get(i).getBorrowedBooks().get(j).getDateOfReturn();
                    }
                }
            }
        }
        try {
            borrowingService.makePostponement(paperBook, 16,user);
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
        Credentials credentials = new Credentials("apk94", "123456789");
        Address address = new Address("6-ti Septemvri", "Bulgaria", "Plovdiv");
        PersonInfo personInfo = new PersonInfo("Ana", "Keredchieva", 24, Sex.FEMALE, address);
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

        User user = new User(credentials, "annie.kere@gmail.com", true, personInfo);
        Book paperBook = new PaperBook("BBB", "horror", "poiuytrew", tags, "mnbvcx", authorsSetPaperBook, 23, 14);

        BorrowingService borrowingService = new BorrowingService();
        borrowingService.makePostponement(paperBook,16,user);
        LocalDate ld = LocalDate.now();
        for (int i = 0; i < DBClassExample.history.size(); i++) {
            if (DBClassExample.history.get(i).getUser().equals(user)){
                for(int j=0; j< DBClassExample.history.get(i).getBorrowedBooks().size(); j++){
                    if (DBClassExample.history.get(i).getBorrowedBooks().get(j).getBook().equals(paperBook)){
                        ld = DBClassExample.history.get(i).getBorrowedBooks().get(j).getDateOfReturn();
                    }
                }
            }
        }
        assertEquals(LocalDate.of(2018,8,11),ld);
    }

    @Test
    public void makePostponementWithTooManyDays() {
        Credentials credentials = new Credentials("apk94", "123456789");
        Address address = new Address("6-ti Septemvri", "Bulgaria", "Plovdiv");
        PersonInfo personInfo = new PersonInfo("Ana", "Keredchieva", 24, Sex.FEMALE, address);
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

        User user = new User(credentials, "annie.kere@gmail.com", true, personInfo);
        Book paperBook = new PaperBook("BBB", "horror", "poiuytrew", tags, "mnbvcx", authorsSetPaperBook, 23, 14);

        BorrowingService borrowingService = new BorrowingService();

        try {
            borrowingService.makePostponement(paperBook, 160,user);
        } catch (IllegalArgumentException iae) {
            assertEquals("You can not make so long postponement.",iae.getMessage());
        }
    }

    @Test
    public void makePostponementForEBook() {
        Credentials credentials = new Credentials("apk94", "123456789");
        Address address = new Address("6-ti Septemvri", "Bulgaria", "Plovdiv");
        PersonInfo personInfo = new PersonInfo("Ana", "Keredchieva", 24, Sex.FEMALE, address);
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

        User user = new User(credentials, "annie.kere@gmail.com", true, personInfo);
        Book eBook = new EBook("AAAA", "comedy", "adsfsghjkhgfds", tags, "JGFBACSXDFGB", authorsSetEBook, "www.sfgwr.com");

        BorrowingService borrowingService = new BorrowingService();
        try {
            borrowingService.makePostponement(eBook, 160,user);
        } catch (IllegalArgumentException iae) {
            assertEquals("This book can not be postponement, because it is E-Book.",iae.getMessage());
        }
    }

    @Test
    public void borrowBookWhichAlreadyBorrowed() {

        Credentials credentials = new Credentials("apk94", "123456789");
        Address address = new Address("6-ti Septemvri", "Bulgaria", "Plovdiv");
        PersonInfo personInfo = new PersonInfo("Ana", "Keredchieva", 24, Sex.FEMALE, address);
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

        User user = new User(credentials, "annie.kere@gmail.com", true, personInfo);
        Book paperBook = new PaperBook("BBB", "horror", "poiuytrew", tags, "mnbvcx", authorsSetPaperBook, 23, 14);

        BorrowingService borrowingService = new BorrowingService();

        try {
            borrowingService.borrowBook(paperBook, user.getCredentials().getUsername());
        } catch (IllegalArgumentException iae){
            String expected = "You can not borrow book that you already borrowed.";
            assertEquals(expected,iae.getMessage());
        }

    }

    @Test
    public void openOnlineBook() {
        Credentials credentials = new Credentials("apk94", "123456789");
        Address address = new Address("6-ti Septemvri", "Bulgaria", "Plovdiv");
        PersonInfo personInfo = new PersonInfo("Ana", "Keredchieva", 24, Sex.FEMALE, address);
        List<String> tags = new ArrayList<>();
        tags.add("aaaa");
        tags.add("bbb");
        tags.add("ccc");

        Set<Author> authorsSetEBook = new HashSet<>();

        Author author3 = new Author("Annie", "GFDS", "BG", 1994);
        authorsSetEBook.add(author3);
        Author author2 = new Author("Gosho ot pochivka", "", "BG", 1984);
        authorsSetEBook.add(author2);
        Author author = new Author("Alexander", " Duma", "Englannd", 1994, 2003);
        authorsSetEBook.add(author);


        User user = new User(credentials, "annie.kere@gmail.com", true, personInfo);
        Book eBook = new EBook("AAAA", "comedy", "adsfsghjkhgfds", tags, "JGFBACSXDFGB", authorsSetEBook, "www.sfgwr.com");

        BorrowingService borrowingService = new BorrowingService();


        assertEquals("The link for online reading: www.sfgwr.com",borrowingService.openOnlineBook(eBook));

    }

    @Test
    public void downloadOnlineBook() {
    }
}
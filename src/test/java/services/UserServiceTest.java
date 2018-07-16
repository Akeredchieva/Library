package services;


import entities.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import repositories.DBClassExample;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

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
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Before
    public void setUp(){
        db = new DBClassExample();
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
        assertEquals(LocalDate.of(2018,5, 2),userService.checkExpirationDate(paperBook));
    }

    @Test
    public void makePostponementForBookWithExpiredReturnDate() {
        UserService userService = new UserService();

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
            userService.makePostponement(user, paperBook, 16);
        } catch(IllegalArgumentException iae) {
            assertEquals("The return date is expired with "
                    + (ld.until(LocalDate.now())).getDays()
                    +" days.And " + (ld.until(LocalDate.now())).getMonths()
                    + " months.Please return your book.",
                    iae.getMessage());
        }

    }

    @Test
    public void viewHistory() {
    }

    @Test
    public void viewBorrowedBooks() {
    }

    @Test
    public void requestForBorrowingBook() {
    }
}
package services;

import entities.Author;
import entities.PaperBook;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class SearchServiceTest {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void searchByTitleWhichNotPresent() {
        SearchService searchService = new SearchService();
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("There is no book with this title.");
        assertEquals(expectedEx,searchService.searchByTitle("fdvds"));
    }

    @Test
    public void searchByEmptyTitle() {
        SearchService searchService = new SearchService();
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("There is no book with this title.");
        assertEquals(expectedEx,searchService.searchByTitle(""));
    }
    @Test
    public void searchByTitleNull() {
        SearchService searchService = new SearchService();
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("There is no book with this title.");
        assertEquals(expectedEx,searchService.searchByTitle(null));
    }


    @Test
    public void searchByGenresWhichNotPresent() {
        SearchService searchService = new SearchService();
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("There is no book with this genres.");
        assertEquals(expectedEx,searchService.searchByGenres("sdf","tyuio"));
    }

    @Test
    public void searchByGenresOnePresentAndOneNot() {
        SearchService searchService = new SearchService();
        List<String> tags = new ArrayList<>();
        tags.add("aaaa");
        tags.add("bbb");
        tags.add("ccc");
        Set<Author> authorsSetPaperBook = new HashSet<>();
        PaperBook paperBook = new PaperBook("BBB", "horror", "poiuytrew", tags, "mnbvcx", authorsSetPaperBook, 23, 0);

        List<PaperBook> paperBooks = new ArrayList<>();
        paperBooks.add(paperBook);
        assertEquals(paperBooks.toString(),searchService.searchByGenres("sdf","horror").toString());
    }

    @Test
    public void searchByEmptyGenre() {
        SearchService searchService = new SearchService();
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("There is no book with this genres.");
        assertEquals(expectedEx,searchService.searchByGenres(""));
    }
    @Test
    public void searchByGenreNull() {
        SearchService searchService = new SearchService();
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("This is impossible search.");
        assertEquals(expectedEx,searchService.searchByGenres(null));
    }

    @Test
    public void searchByTagsWhichNotPresent() {
        SearchService searchService = new SearchService();

        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("There is no book with this tags.");

        assertEquals(expectedEx,searchService.searchByTags("sdf","horror"));
    }

    @Test
    public void searchByTagsOnePresentAndOneNot() {
        SearchService searchService = new SearchService();
        List<String> tags = new ArrayList<>();
        tags.add("aaaa");
        tags.add("bbb");
        tags.add("ccc");
        Set<Author> authorsSetPaperBook = new HashSet<>();
        PaperBook paperBook = new PaperBook("BBB", "horror", "poiuytrew", tags, "mnbvcx", authorsSetPaperBook, 23, 0);

        List<PaperBook> paperBooks = new ArrayList<>();
        paperBooks.add(paperBook);
        assertEquals(paperBooks.toString(),searchService.searchByGenres("ccc","horror").toString());
    }

    @Test
    public void searchByTags() {
        SearchService searchService = new SearchService();

        String expected = "[Book: title: AAAA\n" +
                "genre: comedy\n" +
                "summary: adsfsghjkhgfds\n" +
                "tags: [aaaa, bbb, ccc]\n" +
                "isbn: JGFBACSXDFGB\n" +
                "authors: [Author{firstName='Annie', lastName='GFDS', country='BG', dateOfBirth=1994, dateOfDeath=0}, Author{firstName='Gosho', lastName='ot pochivka', country='BG', dateOfBirth=1984, dateOfDeath=0}, Author{firstName='Alexander', lastName=' Duma', country='Englannd', dateOfBirth=1994, dateOfDeath=2003}]\n" +
                "EBook\n" +
                "onlineReadingLink: www.sfgwr.com\n" +
                "downloadLink: null\n" +
                ", Book: title: BBB\n" +
                "genre: horror\n" +
                "summary: poiuytrew\n" +
                "tags: [aaaa, bbb, ccc]\n" +
                "isbn: mnbvcx\n" +
                "authors: []\n" +
                "PaperBook: \n" +
                "numberOfCopiesAvailable: 0\n" +
                "allCopies: 23\n" +
                "]";
        assertEquals(expected,searchService.searchByTags("ccc","bbb").toString());
    }

    @Test
    public void searchByAuthorFullName() {
        SearchService searchService = new SearchService();

        String expected = "[Book: title: AAAA\n" +
                "genre: comedy\n" +
                "summary: adsfsghjkhgfds\n" +
                "tags: [aaaa, bbb, ccc]\n" +
                "isbn: JGFBACSXDFGB\n" +
                "authors: [Author{firstName='Annie', lastName='GFDS', country='BG', dateOfBirth=1994, dateOfDeath=0}, Author{firstName='Gosho', lastName='ot pochivka', country='BG', dateOfBirth=1984, dateOfDeath=0}, Author{firstName='Alexander', lastName=' Duma', country='Englannd', dateOfBirth=1994, dateOfDeath=2003}]\n" +
                "EBook\n" +
                "onlineReadingLink: www.sfgwr.com\n" +
                "downloadLink: null\n" +
                "]";
        assertEquals(expected,searchService.searchByAuthorFullName("Gosho ot pochivka").toString());

    }

    @Test
    public void searchByAuthorNameNull() {
        SearchService searchService = new SearchService();
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("This is impossible search.");
        assertEquals(expectedEx,searchService.searchByAuthorFullName(null));
    }

    @Test
    public void searchByAuthorNameWhichNotPresent() {
        SearchService searchService = new SearchService();
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("There is no book with this author");
        assertEquals(expectedEx,searchService.searchByAuthorFullName("dxfcgbhjkmljhgfdx brhtyuku"));
    }

    @Test
    public void searchByAuthorNameWhichFirstName() {
        SearchService searchService = new SearchService();
        String expected = "[Book: title: AAAA\n" +
                "genre: comedy\n" +
                "summary: adsfsghjkhgfds\n" +
                "tags: [aaaa, bbb, ccc]\n" +
                "isbn: JGFBACSXDFGB\n" +
                "authors: [Author{firstName='Gosho', lastName='ot pochivka', country='BG', dateOfBirth=1984, dateOfDeath=0}, Author{firstName='Alexander', lastName=' Duma', country='Englannd', dateOfBirth=1994, dateOfDeath=2003}, Author{firstName='Annie', lastName='GFDS', country='BG', dateOfBirth=1994, dateOfDeath=0}]\n" +
                "EBook\n" +
                "onlineReadingLink: www.sfgwr.com\n" +
                "downloadLink: null\n" +
                "]";
        assertEquals(expected,searchService.searchByAuthorFullName("Gosho").toString());
    }

    @Test
    public void searchByAuthorNameWhichLastName() {
        SearchService searchService = new SearchService();
        String expected = "[Book: title: AAAA\n" +
                "genre: comedy\n" +
                "summary: adsfsghjkhgfds\n" +
                "tags: [aaaa, bbb, ccc]\n" +
                "isbn: JGFBACSXDFGB\n" +
                "authors: [Author{firstName='Gosho', lastName='ot pochivka', country='BG', dateOfBirth=1984, dateOfDeath=0}, Author{firstName='Alexander', lastName=' Duma', country='Englannd', dateOfBirth=1994, dateOfDeath=2003}, Author{firstName='Annie', lastName='GFDS', country='BG', dateOfBirth=1994, dateOfDeath=0}]\n" +
                "EBook\n" +
                "onlineReadingLink: www.sfgwr.com\n" +
                "downloadLink: null\n" +
                ", Book: title: AAAA\n" +
                "genre: comedy\n" +
                "summary: adsfsghjkhgfds\n" +
                "tags: [aaaa, bbb, ccc]\n" +
                "isbn: JGFBACSXDFGB\n" +
                "authors: [Author{firstName='Annie', lastName='GFDS', country='BG', dateOfBirth=1994, dateOfDeath=0}, Author{firstName='Alexander', lastName=' Duma', country='Englannd', dateOfBirth=1994, dateOfDeath=2003}, Author{firstName='Gosho', lastName='ot pochivka', country='BG', dateOfBirth=1984, dateOfDeath=0}]\n" +
                "EBook\n" +
                "onlineReadingLink: www.sfgwr.com\n" +
                "downloadLink: null\n" +
                "]";
        assertEquals(expected,searchService.searchByAuthorFullName("ot pochivka").toString());
    }
}
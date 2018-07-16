package entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Book {

    private String title;
    private  String genre;
    private String summary;
    private List<String> tags = new ArrayList<String>();
    private String isbn;
    private Set<Author> authors = new HashSet<Author>();

    protected Book(String title, String genre, String summary, List<String> tags, String isbn, Set<Author> authors) {
        this.title = title;
        this.genre = genre;
        this.summary = summary;
        this.tags = tags;
        this.isbn = isbn;
        this.authors = authors;
    }

    @Override
    public String toString() {
        return "Book: " +
                "title: " + title + '\n' +
                "genre: " + genre + '\n' +
                "summary: " + summary + '\n' +
                "tags: " + tags + '\n' +
                "isbn: " + isbn + '\n' +
                "authors: " + authors + '\n' ;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public String getSummary() {
        return summary;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getIsbn() {
        return isbn;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public String getAllAuthorsByName(){
        StringBuilder sb = new StringBuilder();
        for (Author author: this.authors) {
            sb.append(author.toString());
        }
        return sb.toString();
    }


}

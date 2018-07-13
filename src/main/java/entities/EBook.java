package entities;

import java.util.List;
import java.util.Set;

public class EBook extends  Book {

    private String onlineReadingLink;
    private String downloadLink;

    public EBook(String title, String genre, String summary, List<String> tags, String isbn, Set<Author> authors, String onlineReadingLink, String downloadLink) {
        super(title, genre, summary, tags, isbn, authors);
        this.setDownloadLink(downloadLink);
        this.setOnlineReadingLink(onlineReadingLink);
    }

    public EBook(String title, String genre, String summary, List<String> tags, String isbn, Set<Author> authors, String onlineReadingLink) {
        super(title, genre, summary, tags, isbn, authors);
        this.setOnlineReadingLink(onlineReadingLink);
    }

    @Override
    public String toString() {
        return super.toString() + "EBook\n" +
                "onlineReadingLink: " + onlineReadingLink + '\n' +
                "downloadLink: " + downloadLink + '\n';
    }

    public String getOnlineReadingLink() {
        return onlineReadingLink;
    }

    public void setOnlineReadingLink(String onlineReadingLink) {
        this.onlineReadingLink = onlineReadingLink;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }
}

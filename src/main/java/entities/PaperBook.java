package entities;

import java.util.List;
import java.util.Set;

public class PaperBook extends Book {

    private int numberOfCopiesAvailable;
    private int allCopies;


    public PaperBook(String title, String genre, String summary, List<String> tags, String isbn, Set<Author> authors,int allCopies, int numberOfCopiesAvailable) {
        super(title, genre, summary, tags, isbn, authors);
        this.setAllCopies(allCopies);
        this.setNumberOfCopiesAvailable(numberOfCopiesAvailable);
    }

    @Override
    public String toString() {
        return  super.toString() + "PaperBook: " + '\n' +
                "numberOfCopiesAvailable: " + numberOfCopiesAvailable + '\n' +
                "allCopies: " + allCopies +
                '\n';
    }

    public int getNumberOfCopiesAvailable() {
        return numberOfCopiesAvailable;
    }

    public void setNumberOfCopiesAvailable(int numberOfCopiesAvailable) {
        this.numberOfCopiesAvailable = numberOfCopiesAvailable;
    }

    public int getAllCopies() {
        return allCopies;
    }

    public void setAllCopies(int allCopies) {
        this.allCopies = allCopies;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PaperBook){
            Book book = (Book) obj;
            if (book.getTitle().equals(this.getTitle())){
                return true;
            }
        }
        return false;
    }
}

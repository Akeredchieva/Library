
import entities.PaperBook;
import services.AuthenticationService;
import services.BorrowingService;
import repositories.DBClassExample;
import services.SearchService;
import services.UserService;


public class LibraryEngine {
    public static void main(String[] args) {

        BorrowingService borrowingService = new BorrowingService();
        AuthenticationService as = new AuthenticationService();
        SearchService ss = new SearchService();
        UserService us = new UserService();

        //DBClassExample db = new DBClassExample();






                // testing borrowing book not full
    /*
        PaperBook paperBook = (PaperBook)  DBClassExample.booksInLibrary.get(1);
        System.out.println(paperBook.toString());
        System.out.println(paperBook.getNumberOfCopiesAvailable());

        borrowingService.requestForBorrowingBook(DBClassExample.booksInLibrary.get(1),"asdfds");
        for (int i=0;i<DBClassExample.booksInLibrary.size();i++){
            if (DBClassExample.booksInLibrary.get(i) instanceof PaperBook){
                PaperBook pb = (PaperBook) DBClassExample.booksInLibrary.get(i);
                System.out.println(pb.toString());
            }
        }
        */
    }
}

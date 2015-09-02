import org.junit.*;
import static org.junit.Assert.*;
import org.junit.Rule;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class BookTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void add_emptyAtFirst() {
    assertEquals(Book.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfStudentsAreTheSame() {
    Book newBook = new Book("Sewing", 5);
    Book secondBook = new Book("Sewing", 5);
    assertTrue(newBook.equals(secondBook));
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Book newBook = new Book("Sewing",  5);
    newBook.save();
    assertTrue(Book.all().get(0).equals(newBook));
  }

  @Test
  public void find_findsBookInDatabase_true() {
    Book myBook = new Book("Sewing", 5);
    myBook.save();
    Book savedBook = Book.find(myBook.getId());
    assertTrue(myBook.equals(savedBook));
  }

  @Test
  public void update_updatesBookInDatabase_true() {
    Book myBook = new Book("Sewing", 5);
    myBook.save();
    String title = "Juggling";
    int copies = 3;
    myBook.update(title, copies);
    assertTrue(Book.all().get(0).getTitle().equals(title));
    assertTrue(Book.all().get(0).getCopies() == copies);
  }

  @Test
  public void delete_deletesAllBooksAndListAssociations() {
    Author myAuthor = new Author("Maggie Mags");
    myAuthor.save();

    Book newBook = new Book("Childcare", 1);
    newBook.save();

    newBook.addAuthor(myAuthor);
    newBook.delete();
    assertEquals(myAuthor.getBooks().size(), 0);
  }

  @Test
  public void getAuthors_returnsAllAuthorsAssociatedWithThisBook_List() {
    Author newAuthor = new Author("Author author");
    newAuthor.save();

    Book myBook = new Book("Jumping Rope", 1);
    myBook.save();

    myBook.addAuthor(newAuthor);
    List savedAuthors = myBook.getAuthors();
    assertEquals(savedAuthors.size(), 1);
  }
}

import org.junit.*;
import static org.junit.Assert.*;
import org.junit.Rule;
import java.util.List;

public class AuthorTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Author.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfCoursesAreTheSame() {
    Author firstAuthor = new Author("Robert Bigsbee");
    Author secondAuthor = new Author("Robert Bigsbee");
    assertTrue(firstAuthor.equals(secondAuthor));
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Author newAuthor = new Author("Helena Bagsby");
    newAuthor.save();
    assertTrue(Author.all().get(0).equals(newAuthor));
  }

  @Test
  public void find_findsAuthorInDatabase_true() {
    Author newAuthor = new Author("Bishop Briggers");
    newAuthor.save();
    Author savedAuthor = Author.find(newAuthor.getId());
    assertTrue(newAuthor.equals(savedAuthor));
  }

  @Test
  public void update_updatesAuthorNameInDatabase_true() {
    Author newAuthor = new Author("Bubbles Braggart");
    newAuthor.save();
    String name = "Cesar Chavez";
    newAuthor.update(name);
    assertTrue(Author.all().get(0).getAuthorName().equals(name));
  }

  // @Test
  // public void delete_deletesAuthorAndListAssociations() {
  //   Author newAuthor = new Author("Meeble Morpies");
  //   newAuthor.save();
  //
  //   Book myBook = new Book("Dancing");
  //   myBook.save();
  //
  //   newAuthor.addBook(myBook);
  //   newAuthor.delete();
  //   assertEquals(myBook.getAuthors().size(), 0);
  // }

  @Test
  public void getBooks_returnsAllBooksAssociatedWithThisAuthor_List() {
    Author newAuthor = new Author("Arthur Bigsby");
    newAuthor.save();

    Book myBook = new Book("Bungee Jumping", 1);
    myBook.save();

    newAuthor.addBook(myBook);
    List savedBooks = newAuthor.getBooks();
    assertEquals(savedBooks.size(), 1);

  }

}

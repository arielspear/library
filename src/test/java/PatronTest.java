import org.junit.*;
import static org.junit.Assert.*;
import org.junit.Rule;
import java.util.List;

public class PatronTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Patron.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfPatronNamesAreTheSame_true() {
    Patron firstPatron = new Patron("Morgan");
    Patron secondPatron = new Patron("Morgan");
    assertTrue(firstPatron.equals(secondPatron));
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Patron newPatron = new Patron("Morgan");
    newPatron.save();
    assertTrue(Patron.all().get(0).equals(newPatron));
  }

  @Test
  public void find_findsPatronInDatabase_true() {
    Patron newPatron = new Patron("Morgan");
    newPatron.save();
    Patron savedPatron = Patron.find(newPatron.getId());
    assertTrue(newPatron.equals(savedPatron));
  }
}

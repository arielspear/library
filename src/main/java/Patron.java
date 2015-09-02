import java.util.List;
import org.sql2o.*;

  public class Patron {
    private int id;
    private String patron_name;

    public int getId() {
      return id;
    }

    public String getPatronName() {
      return patron_name;
    }

    public Patron(String patron_name) {
      this.patron_name = patron_name;
    }

  //   @Override
  //   public boolean equals(Object otherPatron) {
  //     if(!(otherPatron instanceof Patron)) {
  //       return false;
  //     } else {
  //       Patron newPatron = (Patron)
  //     }
  //   }
  }

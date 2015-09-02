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

    @Override
    public boolean equals(Object otherPatron) {
      if(!(otherPatron instanceof Patron)) {
        return false;
      } else {
        Patron newPatron = (Patron) otherPatron;
        return this.getPatronName().equals(newPatron.getPatronName());
      }
    }

    public static List<Patron> all() {
      String sql = "SELECT * FROM patrons ORDER BY patron_name ASC";
      try(Connection con = DB.sql2o.open()) {
        return con.createQuery(sql).executeAndFetch(Patron.class);
      }
    }

    public void save() {
      try(Connection con = DB.sql2o.open()) {
        String sql = "INSERT INTO patrons (patron_name) VALUES (:patron_name)";
        this.id = (int) con.createQuery(sql, true)
          .addParameter("patron_name", this.patron_name)
          .executeUpdate()
          .getKey();
      }
    }

    public static Patron find(int id) {
      try(Connection con = DB.sql2o.open()) {
        String sql = "SELECT * FROM patrons WHERE id=:id";
        Patron patron = con.createQuery(sql)
          .addParameter("id", id)
          .executeAndFetchFirst(Patron.class);
        return patron;
      }
    }

    public  void checkoutBook(Book book, String due_date) {
      try(Connection con = DB.sql2o.open()){
        String sql = "INSERT INTO checkouts (patron_id, book_id, due_date) VALUES (:patron_id, :author_id, :due_date);";
           con.createQuery(sql)
          .addParameter("patron_id", this.getId())
          .addParameter("book_id", book.getId())
          .addParameter("due_date", due_date)
          .executeUpdate();

      }
    }


  }

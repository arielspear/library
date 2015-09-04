import java.util.List;
import org.sql2o.*;

  public class Patron {
    private int id;
    private String patron_name;
    // private String due_date;

    public int getId() {
      return id;
    }

    public String getPatronName() {
      return patron_name;
    }

    public Patron(String patron_name) {
      this.patron_name = patron_name;
    }

    //public String getDueDate() {
    //  return due_date;
    //}

    @Override
    public boolean equals(Object otherPatron) {
      if(!(otherPatron instanceof Patron)) {
        return false;
      } else {
        Patron newPatron = (Patron) otherPatron;
        return this.getPatronName().equals(newPatron.getPatronName()) &&
               this.getId() == newPatron.getId();
      }
    }

    public static List<Patron> all() {
      String sql = "SELECT * FROM patrons ORDER BY patron_name ASC";
      try(Connection con = DB.sql2o.open()) {
        return con.createQuery(sql).executeAndFetch(Patron.class);
      }
    }

    public void save() {
      String salt = Patron.hashPassword(this.patron_name + currenttime);
      String hashedPassword = Patron.hashPassword(salt + password);


      try(Connection con = DB.sql2o.open()) {
        String sql = "INSERT INTO patrons (patron_name, password, salt) VALUES (:patron_name, :password, :salt)";
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
      try(Connection con = DB.sql2o.open()) {
        String sql = "INSERT INTO checkouts (patron_id, book_id, due_date) VALUES (:patron_id, :book_id, :due_date);";
           con.createQuery(sql)
          .addParameter("patron_id", this.getId())
          .addParameter("book_id", book.getId())
          .addParameter("due_date", due_date)
          .executeUpdate();

      }
    }

    public void update(String patron_name) {
      this.patron_name = patron_name;
      try(Connection con = DB.sql2o.open()) {
        String sql = "UPDATE patrons SET patron_name=:patron_name WHERE id=:id";
          con.createQuery(sql)
            .addParameter("patron_name", patron_name)
            .addParameter("id", id)
            .executeUpdate();
      }
    }

    public void delete() {
      try(Connection con = DB.sql2o.open()) {
        String deleteQuery = "DELETE FROM patrons WHERE id=:id";
          con.createQuery(deleteQuery)
            .addParameter("id", id)
            .executeUpdate();

        String joinDeleteQuery = "DELETE FROM checkouts WHERE patron_id=:patron_id";
          con.createQuery(joinDeleteQuery)
          .addParameter("patron_id", this.getId())
          .executeUpdate();
      }
    }

    public List<Book> checkedOut() {
      try(Connection con = DB.sql2o.open()){
      String sql = "SELECT books.* FROM patrons JOIN checkouts ON (patrons.id = checkouts.patron_id) JOIN books ON (checkouts.book_id = books.id) WHERE patrons.id= :patron_id;";
           List<Book> books = con.createQuery(sql)
          .addParameter("patron_id", this.getId())
          .executeAndFetch(Book.class);
        return books;
      }
    }

    public static findByName(String name) {
      try(Connection con = DB.sql2o.open()) {
        String sql = "SELECT * FROM patrons WHERE patron_name ilike :name";
        Patron patron = con.createQuery(sql)
          .addParameter("name", name)
          .executeAndFetchFirst(Patron.class);
        return patron;
      }
    }

    private static String hashPassword(String password) {
      String sha1 = "";
      // try {
          MessageDigest crypt = MessageDigest.getInstance("SHA-1");
          crypt.reset();
          crypt.update(password.getBytes("UTF-8"));
          sha1 = byteToHex(crypt.digest());
      // } catch(NoSuchAlgorithmException e) {
      //     e.printStackTrace();
      // } catch(UnsupportedEncodingException e) {
      //     e.printStackTrace();
      //}
      return sha1;
    }
  }//end of class

import java.util.List;
import org.sql2o.*;


  public class Author {
    private int id;
    private String name;


    public int getId() {
      return id;
    }

    public String getAuthorName() {
      return name;
    }

    public Author(String name) {
      this.name = name;
    }


    @Override
    public boolean equals(Object otherAuthor) {
      if(!(otherAuthor instanceof Author)) {
        return false;
      } else {
        Author newAuthor = (Author) otherAuthor;
        return this.getAuthorName().equals(newAuthor.getAuthorName()) &&
               this.getId() == newAuthor.getId();
      }
    }

    public static List<Author> all() {
      String sql = "SELECT * FROM authors ORDER BY name ASC";
      try(Connection con = DB.sql2o.open()) {
        return con.createQuery(sql).executeAndFetch(Author.class);
      }
    }

    public void save() {
      try(Connection con = DB.sql2o.open()) {
        String sql ="INSERT INTO authors (name) VALUES (:name)";
        this.id = (int) con.createQuery(sql, true)
          .addParameter("name", this.name)
          .executeUpdate()
          .getKey();
      }
    }

    public static Author find(int id) {
      try(Connection con = DB.sql2o.open()) {
        String sql = "SELECT * FROM authors WHERE id=:id";
        Author author = con.createQuery(sql)
          .addParameter("id", id)
          .executeAndFetchFirst(Author.class);
        return author;
      }
    }

    public void update(String name) {
      this.name = name;
      try(Connection con = DB.sql2o.open()) {
        String sql = "UPDATE authors SET name=:name WHERE id=:id";
          con.createQuery(sql)
            .addParameter("name", name)
            .addParameter("id", id)
            .executeUpdate();
      }
    }

    public void delete() {
      try(Connection con = DB.sql2o.open()) {
        String deleteQuery = "DELETE FROM authors WHERE id=:id";
          con.createQuery(deleteQuery)
            .addParameter("id", id)
            .executeUpdate();

        String joinDeleteQuery = "DELETE FROM books_authors WHERE author_id=:author_id";
          con.createQuery(joinDeleteQuery)
            .addParameter("author_id", this.getId())
            .executeUpdate();
      }
    }

    public void addBook(Book books) {
      try(Connection con = DB.sql2o.open()) {
        String sql = "INSERT INTO books_authors (book_id, author_id) VALUES (:book_id, :author_id)";
          con.createQuery(sql)
          .addParameter("book_id", books.getId())
          .addParameter("author_id", this.getId())
          .executeUpdate();
      }
    }

    public List<Book> getBooks() {
      try(Connection con = DB.sql2o.open()){
        String sql = "SELECT books.* FROM authors JOIN books_authors ON (authors.id = books_authors.author_id) JOIN books ON (books_authors.book_id = books.id) where authors.id= :author_id;";
        List<Book> books = con.createQuery(sql)
        .addParameter("author_id", this.getId())
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
    try {
        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
        crypt.reset();
        crypt.update(password.getBytes("UTF-8"));
        sha1 = byteToHex(crypt.digest());
    } catch(NoSuchAlgorithmException e) {
        e.printStackTrace();
    } catch(UnsupportedEncodingException e) {
        e.printStackTrace();
    } return sha1;
  }


  }//ends class

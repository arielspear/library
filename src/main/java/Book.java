import java.util.List;
import org.sql2o.*;

public class Book {
  private int id;
  private String title;
  private int copies;

  public int getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public int getCopies() {
    return copies;
  }

  public Book(String title, int copies) {
    this.title = title;
    this.copies = copies;
  }

  @Override
  public boolean equals(Object otherBook) {
    if(!(otherBook instanceof Book)) {
      return false;
    } else {
      Book newBook = (Book) otherBook;
      return this.getTitle().equals(newBook.getTitle()) &&
      this.getCopies() == newBook.getCopies() &&
      this.getId() == newBook.getId();
    }
  }

  public static List<Book> all() {
    String sql = "SELECT * FROM books ORDER BY title ASC";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Book.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO books (title, copies) VALUES (:title, :copies)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("title", this.title)
        .addParameter("copies", this.copies)
        .executeUpdate()
        .getKey();
    }
  }

  public static Book find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM books WHERE id=:id";
      Book book = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Book.class);
      return book;
    }
  }

  public void update(String title, int copies) {
    this.title = title;
    this.copies = copies;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE books SET title=:title, copies=:copies WHERE id=:id";
      con.createQuery(sql)
        .addParameter("title", title)
        .addParameter("copies", copies)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String deleteQuery = "DELETE FROM books WHERE id=:id";
        con.createQuery(deleteQuery)
          .addParameter("id", id)
          .executeUpdate();

      String joinDeleteQuery = "DELETE FROM books_authors WHERE book_id=:book_id";
        con.createQuery(joinDeleteQuery)
          .addParameter("book_id", this.getId())
          .executeUpdate();
    }
  }

  public void addAuthor(Author author) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO books_authors (book_id, author_id) VALUES (:book_id, :author_id)";
      con.createQuery(sql)
        .addParameter("author_id", author.getId())
        .addParameter("book_id", this.getId())
        .executeUpdate();
    }
  }

  public List<Author> getAuthors() {
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT authors.* FROM books JOIN books_authors ON (books.id = books_authors.book_id) JOIN authors ON (books_authors.author_id = authors.id) where books.id= :book_id;";
         List<Author> authors = con.createQuery(sql)
        .addParameter("book_id", this.getId())
        .executeAndFetch(Author.class);
      return authors;
    }
}



}

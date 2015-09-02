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
        return this.getAuthorName().equals(newAuthor.getAuthorName());
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

            //joined delete statement for books goes here
      }
    }


  }//ends class

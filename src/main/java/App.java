
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //get for admin page that displays two tables of books and authors
    get("/admin", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      model.put("books", Book.all());
      model.put("authors", Author.all());
      model.put("template", "templates/admin.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //get to display all books for patrons. should show checkout button
    get("/books", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      model.put("books", Book.all());
      model.put("authors", Author.all());
      model.put("template", "templates/books.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //get and post for adding a new patron
    get("/new-patron", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      model.put("template", "templates/new-account-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/add-patron", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("patron_name");
      Patron newPatron = new Patron(name);
      newPatron.save();

      response.redirect("/admin-book");
      return null;
    });

    //get and post for adding a new book
    get("/new-book", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      model.put("template", "templates/admin-book.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/new-book", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String title = request.queryParams("title");
      int copies = Integer.parseInt(request.queryParams("copies"));
      int authorId = Integer.parseInt(request.queryParams("author_id"));
      Author newAuthor = Author.find(authorId);

      Book newBook = new Book(title, copies);
      newBook.save();
      newBook.addAuthor(newAuthor);

      response.redirect("/admin-book");
      return null;
    });

    //get and post for adding a new author
    get("/new-author", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      model.put("template", "templates/admin-author.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/new-author", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");

      int bookId = Integer.parseInt(request.queryParams("book_id"));
      Book newBook = Book.find(bookId);

      Author newAuthor = new Author(name);
      newAuthor.save();
      newAuthor.addBook(newBook);

      response.redirect("/admin-author");
      return null;
    });

    //get and post for editing a book
    get("/books/:id/edit", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Book book = Book.find(Integer.parseInt(request.params(":id")));
      model.put("book", book);
      model.put("template", "templates/book-edit.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/books/:id/edit", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Book book = Book.find(Integer.parseInt(request.params(":id")));
      String title = request.queryParams("title");
      int copies = Integer.parseInt(request.queryParams("copies"));
      // int authorId = Integer.parseInt(request.queryParams("author_id"));
      // Author newAuthor = Author.find(authorId);

      book.update(title, copies);
      // book.addAuthor(newAuthor);

      response.redirect("/admin-book");
      return null;
    });

    post("/books/:id/delete", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Book book = Book.find(Integer.parseInt(request.params(":id")));

      book.delete();

      response.redirect("/admin-book");
      return null;
    });

    //get and post for editing an author
    get("/authors/:id/edit", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Author author = Author.find(Integer.parseInt(request.params(":id")));
      model.put("author", author);
      model.put("template", "templates/author-edit.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/authors/:id/edit", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Author author = Author.find(Integer.parseInt(request.params(":id")));
      String name = request.queryParams("name");

      author.update(name);

      response.redirect("/admin-author");
      return null;
    });

    post("/authors/:id/delete", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Author author = Author.find(Integer.parseInt(request.params(":id")));

      author.delete();

      response.redirect("/admin-author");
      return null;
    });

  }
}

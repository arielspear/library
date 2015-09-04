
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

      model.put("patrons", Patron.all());
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


      model.put("template", "templates/new-patron.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/new-patron", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("patron_name");
      //check for duplicate user
      List<Patron> patrons = Patron.findByName(name);
        if(!patrons.isEmpty()) {
          model.put("error", "This username already exists");
          model.put("template", "templates/welcome.vtl");
          return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

          ????? )
        } else {
          Patron newPatron = new Patron(name);
        }

      //get user password
      String password = request.queryParams("password");
      //save user password //need trycatch logic //save must throw an execption if password.length<min_password_length
      newPatron.save(password);
      int patronId = newPatron.getId();
      response.redirect("/welcome/"+ patronId);
      return null;
    });

    //get page for welcoming a new patron
    get("/welcome/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Patron patron = Patron.find(Integer.parseInt(request.params(":id")));
      model.put("patron", patron);
      model.put("template", "templates/welcome.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //get page to show all books to patrons
    get("/welcome/:id/all-books", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Patron patron = Patron.find(Integer.parseInt(request.params(":id")));
      model.put("books", Book.all());
      model.put("patron", patron);
      model.put("template", "templates/all-books.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //get page to show librarian how many books patron has
    get("/patrons/:id/check-books", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Patron patron = Patron.find(Integer.parseInt(request.params(":id")));

      model.put("books", Book.all());
      model.put("patron", patron);
      //model.put("patron_books", patron.checkedOut());
      model.put("template", "templates/patrons-books.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

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
      // int authorId = Integer.parseInt(request.queryParams("author_id"));
      // Author newAuthor = Author.find(authorId);

      Book newBook = new Book(title, copies);
      newBook.save();
      // newBook.addAuthor(newAuthor);

      response.redirect("/new-book");
      return null;
    });

    //get and post for adding authors to books
    get("/books/:id/add-author", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Author author = Author.find(Integer.parseInt(request.params(":id")));
      model.put("author", author);
      model.put("template", "templates/author-edit.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/books/:id/add-author", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Author author = Author.find(Integer.parseInt(request.params(":id")));
      String name = request.queryParams("name");

      author.update(name);

      response.redirect("/admin-author");
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

      Author newAuthor = new Author(name);
      newAuthor.save();

      response.redirect("/admin");
      return null;
    });

    //get and post for editing a book
    get("/books/:id/edit", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Book book = Book.find(Integer.parseInt(request.params(":id")));

      model.put("authors", Author.all());
      model.put("book", book);
      model.put("template", "templates/book-edit.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/books/:id/edit", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Book book = Book.find(Integer.parseInt(request.params(":id")));
      String title = request.queryParams("title");
      int copies = Integer.parseInt(request.queryParams("copies"));
      int bookId = Integer.parseInt(request.params(":id"));

      book.update(title, copies);

      response.redirect("/admin");
      return null;
    });

    //post to add authors to book
    post("/books/:id/add-authors-to-book", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Book book = Book.find(Integer.parseInt(request.params(":id")));
      int bookId = Integer.parseInt(request.params(":id"));

      int authorId = Integer.parseInt(request.queryParams("author_id"));
      Author newAuthor = Author.find(authorId);

      book.addAuthor(newAuthor);

      response.redirect("/admin");
      return null;
    });

    //post to delete book
    post("/books/:id/delete", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Book book = Book.find(Integer.parseInt(request.params(":id")));

      book.delete();

      response.redirect("/admin");
      return null;
    });

    //get and post for editing an author
    get("/authors/:id/edit", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Author author = Author.find(Integer.parseInt(request.params(":id")));

      model.put("books", Book.all());
      model.put("author", author);
      model.put("template", "templates/author-edit.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/authors/:id/edit", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Author author = Author.find(Integer.parseInt(request.params(":id")));
      String name = request.queryParams("name");
      int authorId = Integer.parseInt(request.params(":id"));
      author.update(name);

      response.redirect("/authors/" + authorId + "/edit");
      return null;
    });

    //post to add books to authors
    post("/authors/:id/add-books-to-author", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Author author = Author.find(Integer.parseInt(request.params(":id")));
      int authorId = Integer.parseInt(request.params(":id"));

      int bookId = Integer.parseInt(request.queryParams("book_id"));
      Book newBook = Book.find(bookId);

      author.addBook(newBook);

      response.redirect("/admin");
      return null;
    });

    //post to delete author
    post("/authors/:id/delete", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Author author = Author.find(Integer.parseInt(request.params(":id")));

      author.delete();

      response.redirect("/admin");
      return null;
    });

    //get all books for specific author for patrons
    get("/authors/:id/all-books", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Author author = Author.find(Integer.parseInt(request.params(":id")));

      model.put("author", author);
      model.put("template", "templates/show-author-books.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //checkout a book get/post routes
    get("welcome/:patron_id/books/:id/checkout", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Book book = Book.find(Integer.parseInt(request.params(":id")));
      Patron patron = Patron.find(Integer.parseInt(request.params(":patron_id")));
      model.put("book", book);
      model.put("patron", patron);
      model.put("books", Book.all());
      model.put("template", "templates/checkout.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());


  }
}

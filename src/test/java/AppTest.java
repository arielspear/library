import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.junit.rules.ExternalResource;
import org.sql2o.*;

import static org.fluentlenium.core.filter.FilterConstructor.*;
import static org.assertj.core.api.Assertions.assertThat;

public class AppTest extends FluentTest{
  public WebDriver webDriver = new HtmlUnitDriver();
  public WebDriver getDefaultDriver(){
    return webDriver;
  }

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void rootTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("Special Library for Unicorns");
  }

  @Test
  public void adminPageInstantiates() {
    goTo("http://localhost:4567/");
    click("a", withText("Admin Page"));
    assertThat(pageSource().contains("Unicorn University Library Admin Page"));

  }

  @Test
  public void authorIsCreated_true() {
    goTo("http://localhost:4567/");
    click("a", withText("Admin Page"));
    click("a", withText("Add Author"));
    fill("#name").with("Jocelyn");
    submit(".new-author");
    assertThat(pageSource().contains("Jocelyn"));
  }

  //edit book name and copy number
  @Test
  public void editBookWithNewNameAndCopyNumber_Edit() {
    Book myBook = new Book("Wild", 1);
    myBook.save();
    goTo("http://localhost:4567/admin");
    click("a", withText("Wild"));
    fill("#title").with("Jocelyn");
    fill("#copies").with("1");
    submit(".test");
    assertThat(pageSource().contains("Jocelyn"));

  }




  }

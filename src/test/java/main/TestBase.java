package main;

import factory.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

@SpringBootTest(classes = Application.class)
public abstract class TestBase extends AbstractTestNGSpringContextTests {

  @Autowired
  private WebDriverFactory webDriverFactory;

  @BeforeMethod(alwaysRun = true)
  public void setUp() {
    System.out.println("Initializing WebDriver...");
    webDriverFactory.create();
  }

  @AfterMethod(alwaysRun = true)
  public void tearDown() {
    webDriverFactory.killDriver();
  }
}
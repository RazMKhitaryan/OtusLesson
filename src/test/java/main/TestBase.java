package main;

import factory.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class TestBase {
  protected final WebDriverFactory webDriverFactory = new WebDriverFactory();

  @BeforeMethod
  public void setUp() {
    webDriverFactory.create();
  }

  @AfterMethod(alwaysRun = true)
  public void tearDown() {
    WebDriver driver = webDriverFactory.getDriver();
    if (driver != null) {
      driver.quit();
    }
    webDriverFactory.killDriver();
  }

  protected WebDriver getDriver() {
    return webDriverFactory.getDriver();
  }
}
package utils;

import factory.WebDriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.Duration;

@Component
public class WaitUtils {
  private static final int DEFAULT_TIMEOUT_SECONDS = 20;
  private WebDriverWait wait;

  @Autowired
  private WebDriverFactory webDriverFactory;

  private void ensureInitialized() {
    this.wait = new WebDriverWait(webDriverFactory.getDriver(), Duration.ofSeconds(DEFAULT_TIMEOUT_SECONDS));
  }

  public void waitTillElementVisible(WebElement element) {
    ensureInitialized();
    wait.until(ExpectedConditions.visibilityOf(element));
  }

  public void waitTillElementVisible(By element) {
    ensureInitialized();
    wait.until(ExpectedConditions.presenceOfElementLocated(element));
  }

  public WebDriverWait getWait() {
    return wait;
  }


  /**
   * Waits for the page to be fully loaded by checking document.readyState
   */
  public void waitForPageLoad() {
    ensureInitialized();
    wait.until(driver -> ((JavascriptExecutor) driver)
        .executeScript("return document.readyState").equals("complete"));

    ExpectedCondition<Boolean> expectation = driver -> {
      assert driver != null;
      return ((JavascriptExecutor) driver).executeScript("return jQuery.active").toString()
          .equals("0");
    };
    try {
      Thread.sleep(1000);
      wait.until(expectation);
    } catch (Exception e) {
      //ignore
    }
  }
}
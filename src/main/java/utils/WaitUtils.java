package utils;

import factory.WebDriverFactory;
import jakarta.annotation.PostConstruct;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.Duration;

@Component
public class WaitUtils {
  private static final int DEFAULT_TIMEOUT_SECONDS = 20;
  private WebDriver driver;
  private WebDriverWait wait;

  @Autowired
  private WebDriverFactory webDriverFactory;

  private void ensureInitialized() {
    if (this.driver == null || this.wait == null) {
      try {
        this.driver = webDriverFactory.getDriver();
      } catch (IllegalStateException e) {
        // Create the driver if it doesn't exist yet
        this.driver = webDriverFactory.create();
      }
      this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT_SECONDS));
    }
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
}
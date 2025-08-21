package utils;

import factory.WebDriverFactory;
import jakarta.annotation.PostConstruct;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActionUtils {
  private Actions actions;

  @Autowired
  private WebDriverFactory webDriverFactory;

  // No @PostConstruct needed

  private void ensureInitialized() {
    if (this.actions == null) {
      try {
        this.actions = new Actions(webDriverFactory.getDriver());
      } catch (IllegalStateException e) {
        // Create the driver if it doesn't exist yet
      }
    }
  }

  public void hoverOnElement(WebElement element) {
    ensureInitialized();
    actions.moveToElement(element).build().perform();
  }

  public Actions getActions() {
    ensureInitialized();
    return actions;
  }
}
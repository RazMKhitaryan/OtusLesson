package utils;

import com.google.inject.Inject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class ActionUtils {
  private Actions actions;

  @Inject
  public ActionUtils(Actions actions) {
    this.actions = actions;
  }

  public void hoverOnElement(WebElement element) {
    actions.moveToElement(element).build().perform();
  }
}

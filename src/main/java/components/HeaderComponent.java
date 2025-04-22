package components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.ActionUtils;

public class HeaderComponent extends AbsBaseComponent {

  private final ActionUtils actionUtils;

  @FindBy(css = "span[title='Обучение']")
  private WebElement trainingField;

  public HeaderComponent(WebDriver driver, ActionUtils actionUtils) {
    super(driver);
    this.actionUtils = actionUtils;
  }

  public void hoverOnTrainingField() {
    actionUtils.hoverOnElement(trainingField);// use Actions
  }
}

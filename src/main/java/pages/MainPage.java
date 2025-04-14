package pages;

import com.google.inject.Inject;
import components.TrainingComponent;
import listeners.MouseListener;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.ActionUtils;

public class MainPage extends AbsBasePage {

  private ActionUtils actionUtils;
  private TrainingComponent trainingComponent;

  @FindBy(css = "span[title='Обучение']")
  private WebElement trainingField;

  @FindBy(css = ".sc-bvhtwp-2")
  private WebElement okButton;

  @Inject
  public MainPage(WebDriver driver, ActionUtils actionUtils, MouseListener mouseListener) {
    super(driver, mouseListener);
    this.actionUtils = actionUtils;
  }

  public void hoverOnTrainingField() {
    actionUtils.hoverOnElement(trainingField);// use Actions
  }

  @Override
  public MainPage open() {
    openPage(getUrl());
    return this;
  }

  @Override
  public String getUrl() {
    return "";
  }

}

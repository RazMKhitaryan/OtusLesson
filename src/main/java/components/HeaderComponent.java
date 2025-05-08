package components;

import annotations.Component;
import com.google.inject.Inject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import scope.ScenScoped;

@Component("xpath://*[@id=\"__next\"]/div[2]/div[2]")
public class HeaderComponent extends AbsBaseComponent {

  @FindBy(css = "span[title='Обучение']")
  private WebElement trainingField;

  @Inject
  public HeaderComponent(ScenScoped scenScoped) {
    super(scenScoped.getDriver());
  }

  public void hoverOnTrainingField() {
    verifyComponentLoaded(); // can not check in constructor ,causes the issue in injection,during creating object element can not find
    actionUtils.hoverOnElement(trainingField);// use Actions
  }
}

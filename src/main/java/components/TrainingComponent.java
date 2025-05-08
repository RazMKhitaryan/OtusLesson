package components;

import annotations.Component;
import com.google.inject.Inject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import scope.ScenScoped;
import java.util.List;

@Component("xpath://nav/div[3]/div")
public class TrainingComponent extends AbsBaseComponent {

  @FindBy(xpath = "//nav//div[3]//div//div//div[1]//div//div//a")
  public List<WebElement> courcesList;

  @Inject
  public TrainingComponent(ScenScoped scenScoped) {
    super(scenScoped.getDriver());
  }

  public String clickOnRandomCourseAndReturnName() {
    verifyComponentLoaded(); // can not check in constructor ,causes the issue in injection,during creating object element can not find
    int index = (int) (Math.random() * courcesList.size());
    String name = getText(courcesList.get(index)).split(" \\(")[0];
    clickOnElement(courcesList.get(index));
    return name;
  }
}

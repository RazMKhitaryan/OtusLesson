package components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.List;

public class TrainingComponent extends AbsBaseComponent {

  @FindBy(css = "[class='sc-1kjc6dh-2 sc-ig0m9y-0 lhsLfs cgYLnJ'] a")
  public List<WebElement> courcesList;

  public TrainingComponent(WebDriver driver) {
    super(driver);
  }

  public String clickOnRandomCourseAndReturnName() {
    int index = (int) (Math.random() * courcesList.size());
    String name = getText(courcesList.get(index)).split(" ")[0];
    clickOnElement(courcesList.get(index));
    return name;
  }
}

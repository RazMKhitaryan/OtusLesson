package components;

import annotations.Component;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Random;

@Service
@Component("xpath://nav/div[3]/div")
public class TrainingComponent extends AbsBaseComponent {

  private final Random random = new Random();
  @FindBy(xpath = "//nav//div[3]//div//div//div[1]//div//div//a")
  private List<WebElement> coursesList;

  public String clickOnRandomCourseAndReturnName() {
    verifyComponentLoaded();
    initComponents();
    waitUtils.waitTillElementVisible(coursesList.get(0));

    int index = random.nextInt(coursesList.size());
    WebElement selectedCourse = coursesList.get(index);

    String name = getText(selectedCourse).split(" \\(")[0];
    clickOnElement(selectedCourse);

    return name;
  }
}
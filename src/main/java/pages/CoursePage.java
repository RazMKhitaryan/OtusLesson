package pages;

import annotations.Path;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Path("/")
public class CoursePage extends AbsBasePage {

  @FindBy(css = "[class='sc-1x9oq14-0 sc-s2pydo-1 kswXpy diGrSa']")
  WebElement courseTitle;

  public CoursePage(WebDriver driver) {
    super(driver);
  }

  @Override
  public CoursePage open() {
    return null;
  }

  public boolean isSelectedCoursePageOpened(String courseName) {
    return getText(courseTitle).contains(courseName);
  }
}

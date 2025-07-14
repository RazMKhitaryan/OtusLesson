package pages;

import annotations.Path;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.stereotype.Service;

@Service
@Path("/")
public class CoursePage extends AbsBasePage {

  @FindBy(xpath = "//main//h1")
  private WebElement courseTitle;

  public boolean isSelectedCoursePageOpened(String courseName) {
    initPages();
    waitUtils.waitTillElementVisible(courseTitle);
    return getText(courseTitle).contains(courseName);
  }
}
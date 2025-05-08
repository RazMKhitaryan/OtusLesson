package pages;

import annotations.Path;
import com.google.inject.Inject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import scope.ScenScoped;

@Path("/")
public class CoursePage extends AbsBasePage {

  @FindBy(xpath = "//main//h1")
  WebElement courseTitle;

  @Inject
  public CoursePage(ScenScoped scenScoped) {
    super(scenScoped.getDriver());
  }

  public boolean isSelectedCoursePageOpened(String courseName) {
    return getText(courseTitle).contains(courseName);
  }
}

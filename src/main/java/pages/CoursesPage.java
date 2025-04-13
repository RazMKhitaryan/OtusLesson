package pages;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CoursesPage extends AbsBasePage {

  @FindBy(css = "[class='sc-hrqzy3-1 jEGzDf']")
  private List<WebElement> courses;

  public CoursesPage(WebDriver driver) {
    super(driver);
  }

  @Override
  public AbsBasePage open() {
    return openPage(getUrl());
  }

  @Override
  public String getUrl() {
    return "/catalog/courses";
  }

  public CoursePage clickOnCourseByName(String courseName) {
    WebElement webElement = courses.stream()
        .filter(course -> course.getText()
            .trim()
            .contains(courseName))
        .findFirst()
            .orElseThrow(() -> new RuntimeException("Course not found: " + courseName));
    clickOnElement(webElement);
    return new CoursePage(driver);
  }
}

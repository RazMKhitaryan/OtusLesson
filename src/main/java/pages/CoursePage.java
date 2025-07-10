package pages;

import annotations.Path;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Path("/")
public class CoursePage extends AbsBasePage {

  @FindBy(xpath = "//main//h1")
  WebElement courseTitle;

  @Autowired
  public CoursePage(WebDriver driver) {
    super(driver);
  }

  public boolean isSelectedCoursePageOpened(String courseName) {
    return getText(courseTitle).contains(courseName);
  }
}

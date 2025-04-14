package pages;

import com.google.inject.Inject;
import listeners.MouseListener;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CoursePage extends AbsBasePage {

  @FindBy(css = "[class='sc-1x9oq14-0 sc-s2pydo-1 kswXpy diGrSa']")
  WebElement courseTitle;

  @Inject
  public CoursePage(WebDriver driver, MouseListener mouseListener) {
    super(driver, mouseListener);
  }

  @Override
  public CoursePage open() {
    return null;
  }

  @Override
  public String getUrl() {
    return "";
  }

  public boolean isSelectedCoursePageOpened(String courseName) {
    return getText(courseTitle).contains(courseName);
  }
}

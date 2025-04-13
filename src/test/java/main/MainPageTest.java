package main;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.inject.Inject;
import extencions.UIExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import pages.CoursePage;
import pages.CoursesPage;
import pages.MainPage;

@ExtendWith(UIExtension.class)
public class MainPageTest {

  @Inject
  private WebDriver driver;

  @Inject
  private MainPage mainPage;

  @Inject
  private CoursesPage coursesPage;

  @Test
  public void clickDayNewsItem() {
    String courseName = "Scratch";
    coursesPage.open();
    CoursePage coursePage = coursesPage.clickOnCourseByName(courseName);
    assertThat(coursePage.isSelectedCoursePageOpened(courseName))
        .as("Check that course page for '%s' is opened", courseName)
        .isTrue();
  }
}

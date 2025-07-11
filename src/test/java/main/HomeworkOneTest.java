package main;

import static org.assertj.core.api.Assertions.assertThat;

import components.HeaderComponent;
import components.TrainingComponent;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.CoursePage;
import pages.CoursesPage;
import pages.MainPage;
import java.util.List;

public class HomeworkOneTest extends TestBase {
  SoftAssertions softly = new SoftAssertions();

  @Test
  public void coursePageVerification() {
    CoursesPage coursesPage = new CoursesPage();
    coursesPage.openPage();
    String randomCourseName = coursesPage.getRandomCourseName();
    coursesPage.clickOnCourseByName(randomCourseName);
    assertThat(new CoursePage().isSelectedCoursePageOpened(randomCourseName))
        .as("Check that course page for '%s' is opened" + randomCourseName)
        .isTrue();
  }

  @Test
  public void earliestAndLatestCoursesVerification() {
    CoursesPage coursesPage = new CoursesPage();
    coursesPage.openPage();
    List<WebElement> earliestCourses = coursesPage.getEarliestCourses();
    List<WebElement> latestCourses = coursesPage.getLatestCourses();

    earliestCourses.forEach(course ->
        softly.assertThat(coursesPage.isCourseModelInPage(course))
            .as("Earliest course should be present in the page: " + course.getText())
            .isTrue());
    latestCourses.forEach(course ->
        softly.assertThat(coursesPage.isCourseModelInPage(course))
            .as("Latest course should be present in the page: " + course.getText())
            .isTrue());
    softly.assertAll();
  }

  @Test
  public void selectedCourseVerification() {
    new MainPage().openPage();
    new HeaderComponent().hoverOnTrainingField();
    String courseName = new TrainingComponent().clickOnRandomCourseAndReturnName();
    CoursesPage coursesPage = new CoursesPage();
    assertThat(coursesPage.isCourseSelected(coursesPage.getOpenedCourseByName(courseName)))
        .as("Course should be present in the page" + courseName)
        .isTrue();
  }
}

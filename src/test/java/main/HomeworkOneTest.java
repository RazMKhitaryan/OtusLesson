package main;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import java.util.List;

public class HomeworkOneTest extends TestBase {

  SoftAssertions softly = new SoftAssertions();

  @Test
  public void coursePageVerification() {
    coursesPage.openPage();
    String randomCourseName = coursesPage.getRandomCourseName();
    coursesPage.clickOnCourseByName(randomCourseName);
    assertThat(coursePage.isSelectedCoursePageOpened(randomCourseName))
        .as("Check that course page for '%s' is opened" + randomCourseName)
        .isTrue();
  }

  @Test
  public void earliestAndLatestCoursesVerification() {
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
    mainPage.openPage();
    headerComponent.hoverOnTrainingField();
    String courseName = trainingComponent.clickOnRandomCourseAndReturnName();
    assertThat(coursesPage.isCourseSelected(coursesPage.getOpenedCourseByName(courseName)))
        .as("Course should be present in the page" + courseName)
        .isTrue();
  }
}

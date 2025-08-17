package main;

import static org.assertj.core.api.Assertions.assertThat;

import components.HeaderComponent;
import components.TrainingComponent;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;
import pages.CoursePage;
import pages.CoursesPage;
import pages.MainPage;
import java.util.List;

public class HomeworkOneTest extends TestBase {

  private final SoftAssertions softly = new SoftAssertions();
  @Autowired
  private MainPage mainPage;
  @Autowired
  private CoursesPage coursesPage;
  @Autowired
  private CoursePage coursePage;
  @Autowired
  private HeaderComponent headerComponent;
  @Autowired
  private TrainingComponent trainingComponent;

  @Test(description = "Verify random course page can be opened")
  public void coursePageVerification() {
    System.out.println("Verify random course page can be opened");
    coursesPage.openPage();
    String randomCourseName = coursesPage.getRandomCourseName();
    coursesPage.clickOnCourseByName(randomCourseName);
    assertThat(coursePage.isSelectedCoursePageOpened(randomCourseName))
        .as("Course page for '%s' should be opened", randomCourseName)
        .isTrue();
  }

  @Test(description = "Verify earliest and latest courses are displayed on the page")
  public void earliestAndLatestCoursesVerification() {
    System.out.println("Verify earliest and latest courses are displayed on the page");
    coursesPage.openPage();
    List<WebElement> earliestCourses = coursesPage.getEarliestCourses();
    List<WebElement> latestCourses = coursesPage.getLatestCourses();
    earliestCourses.forEach(course ->
        softly.assertThat(coursesPage.isCourseModelInPage(course))
            .as("Earliest course should be present: %s", course.getText())
            .isTrue());
    latestCourses.forEach(course ->
        softly.assertThat(coursesPage.isCourseModelInPage(course))
            .as("Latest course should be present: %s", course.getText())
            .isTrue());

    softly.assertAll();
  }

  @Test(description = "Verify course selection from training menu")
  public void selectedCourseVerification() {
    System.out.println("Verify course selection from training menu");
    mainPage.openPage();
    headerComponent.hoverOnTrainingField();
    String courseName = trainingComponent.clickOnRandomCourseAndReturnName();
    assertThat(coursesPage.isCourseSelected(coursesPage.getOpenedCourseByName(courseName)))
        .as("Course '%s' should be marked as selected", courseName)
        .isTrue();
  }

}
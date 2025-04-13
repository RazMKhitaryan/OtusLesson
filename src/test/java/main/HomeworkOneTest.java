package main;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.inject.Inject;
import extencions.UIExtension;
import java.util.List;
import models.CourseModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import pages.CoursePage;
import pages.CoursesPage;
import pages.MainPage;

@ExtendWith(UIExtension.class)
public class HomeworkOneTest {

  @Inject
  private WebDriver driver;

  @Inject
  private MainPage mainPage;

  @Inject
  private CoursesPage coursesPage;

  @Inject
  private CourseModel courseModel;

  @Test
  public void scenario_one() {
    String courseName = "Scratch";
    coursesPage.open();
    CoursePage coursePage = coursesPage.clickOnCourseByName(courseName);
    assertThat(coursePage.isSelectedCoursePageOpened(courseName))
        .as("Check that course page for '%s' is opened", courseName)
        .isTrue();
  }

  @Test
  public void scenario_two() {
    coursesPage.open();
    List<CourseModel> earliestCourses = coursesPage.getEarliestCourses(
        coursesPage.parseCourseLinksToModels(courseModel));
    earliestCourses.forEach(course ->
        assertThat(coursesPage.isCourseModelInPage(course))
            .as("Course should be present in the page", course.getName())
            .isTrue()
    );
  }
}

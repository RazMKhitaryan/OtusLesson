package main;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.inject.Inject;
import components.HeaderComponent;
import components.TrainingComponent;
import extencions.UIExtension;
import models.CourseModel;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.CoursePage;
import pages.CoursesPage;
import java.util.List;

@ExtendWith(UIExtension.class)
public class HomeworkOneTest {

  @Inject
  private CoursesPage coursesPage;

  SoftAssertions softly = new SoftAssertions();

  @Inject
  private CoursePage coursePage;

  @Inject
  private CourseModel courseModel;

  @Inject
  private TrainingComponent trainingComponent;

  @Inject
  private HeaderComponent headerComponent;

  @Test
  public void scenario_one() {
    String randomCourseName = coursesPage.open().getRandomCourseName();
    coursesPage.clickOnCourseByName(randomCourseName);
    assertThat(coursePage.isSelectedCoursePageOpened(randomCourseName))
        .as("Check that course page for '%s' is opened" + randomCourseName)
        .isTrue();
  }

  @Test
  public void scenario_two() {
    List<CourseModel> courses = coursesPage.open().parseCourseLinksToModels(courseModel);
    List<CourseModel> earliestCourses = coursesPage.getEarliestCourses(courses);
    List<CourseModel> latestCourses = coursesPage.getLatestCourses(courses);

    earliestCourses.forEach(course ->
        softly.assertThat(coursesPage.isCourseModelInPage(course))
            .as("Earliest course should be present in the page: " + course.getName())
            .isTrue());
    latestCourses.forEach(course ->
        softly.assertThat(coursesPage.isCourseModelInPage(course))
            .as("Latest course should be present in the page: " + course.getName())
            .isTrue());
    softly.assertAll();
  }

  @Test
  public void scenario_three() {
    headerComponent.hoverOnTrainingField();
    String courseName = trainingComponent
        .clickOnRandomCourseAndReturnName();
    assertThat(coursesPage.isCourseSelected(coursesPage.getOpenedCourseByName(courseName)))
        .as("Course should be present in the page" + courseName)
        .isTrue();
  }
}

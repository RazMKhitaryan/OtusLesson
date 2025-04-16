package main;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.inject.Inject;
import components.HeaderComponent;
import components.TrainingComponent;
import extencions.UIExtension;
import models.CourseModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.CoursePage;
import pages.CoursesPage;
import java.util.List;


@ExtendWith(UIExtension.class)
public class HomeworkOneTest {

  @Inject
  private CoursesPage coursesPage;

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
    String courseName = "Archimate";
    coursesPage.open()
        .clickOnCourseByName(courseName);
    assertThat(coursePage.isSelectedCoursePageOpened(courseName))
        .as("Check that course page for '%s' is opened" + courseName)
        .isTrue();
  }

  @Test
  public void scenario_two() {
    List<CourseModel> earliestCourses = coursesPage.open()
        .getEarliestCourses(coursesPage.parseCourseLinksToModels(courseModel));
    earliestCourses.forEach(course ->
        assertThat(coursesPage.isCourseModelInPage(course))
            .as("Course should be present in the page" + course.getName())
            .isTrue());
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

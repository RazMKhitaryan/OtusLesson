package otus.steps;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.inject.Inject;
import io.cucumber.java.en.Then;
import pages.CoursePage;
import scope.ScenScoped;

public class CoursePageSteps {

  @Inject
  private CoursePage coursePage;

  @Inject
  private ScenScoped scenScoped;

  @Then("course page should opened successfully")
  public void coursePageShouldOpenedSuccessfully() {
    String randomCourseName = scenScoped.get("randomCourseName");
    assertThat(coursePage.isSelectedCoursePageOpened(randomCourseName))
        .as("Check that course page for '%s' is opened" + randomCourseName)
        .isTrue();
  }

}

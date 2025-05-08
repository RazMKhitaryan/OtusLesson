package otus.steps;

import com.google.inject.Inject;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import pages.CoursesPage;
import scope.ScenScoped;

public class CoursesPageSteps {

  @Inject
  private CoursesPage coursesPage;

  @Inject
  private ScenScoped scenScoped;

  @Given("the courses catalog page opened")
  public void openCoursePage() {
    coursesPage.openPage();
  }

  @When("click on random course")
  public void clickOnRandomCourse() {
    String randomCourseName = coursesPage.getRandomCourseName();
    scenScoped.set(randomCourseName, "randomCourseName");
    coursesPage.clickOnCourseByName(randomCourseName);
  }
}

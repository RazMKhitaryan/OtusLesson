package otus.steps;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.inject.Inject;
import components.HeaderComponent;
import components.TrainingComponent;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.CoursePage;
import pages.CoursesPage;
import pages.MainPage;
import scope.ScenScoped;

public class CoursePageSteps {

  @Inject
  private CoursePage coursePage;

  @Inject
  private ScenScoped scenScoped;

  @Inject
  private MainPage mainPage;

  @Inject
  private HeaderComponent headerComponent;

  @Inject
  private TrainingComponent trainingComponent;

  @Inject
  private CoursesPage coursesPage;

  @Then("course page should opened successfully")
  public void coursePageShouldOpenedSuccessfully() {
    String randomCourseName = scenScoped.get("courseName");
    assertThat(coursePage.isSelectedCoursePageOpened(randomCourseName))
        .as("Check that course page for '%s' is opened" + randomCourseName)
        .isTrue();
  }

  @Given("the main page is opened")
  public void openMainPage() {
    mainPage.openPage();
  }

  @When("I hover over the training menu")
  public void hoverOnTraining() {
    headerComponent.hoverOnTrainingField();
  }

  @When("I click on a random course from training")
  public void clockOnRandomCourse() {
    String courseName = trainingComponent.clickOnRandomCourseAndReturnName();
    scenScoped.set(courseName,"randomCourseName");
  }

  @Then("the course page should display the selected course")
  public void checkCourseCheckmarkSelected() {
    String selectedCourseName = scenScoped.get("randomCourseName");
    boolean isCourseDisplayed = coursesPage.isCourseSelected(
        coursesPage.getOpenedCourseByName(selectedCourseName));
    assertThat(isCourseDisplayed)
        .as("Course should be present in the page: " + selectedCourseName)
        .isTrue();
  }
}

package otus.steps;

import com.google.inject.Inject;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.WebElement;
import pages.CoursesPage;
import scope.ScenScoped;
import java.util.*;

public class CoursesPageSteps {

  @Inject
  private CoursesPage coursesPage;

  @Inject
  private ScenScoped scenScoped;

  private final SoftAssertions softly = new SoftAssertions();
  private final List<WebElement> earliestCourses = new ArrayList<>();
  private final List<WebElement> latestCourses = new ArrayList<>();

  @Given("the courses catalog page opened")
  public void openCoursePage() {
    coursesPage.openPage();
  }

  @When("click on {string} course")
  public void clickOnRandomCourse(String courseName) {
    ArrayList<String> names = new ArrayList<>();
    try {
      names.addAll(Arrays.asList(courseName.split(",")));
    } catch (Exception e) {
      names.add(courseName);
    }
    int index = new Random().nextInt(names.size());
    coursesPage.clickOnCourseByName(names.get(index));
    scenScoped.set(courseName, "courseName");
  }

  @When("click on Подготовительные курсы")
  public void selectOnboardingCourses() {
    coursesPage.clickInOnboardingCourses();
  }

  @Given("the courses page is opened")
  public void openCoursesPage() {
    coursesPage.openPage();
  }

  @When("find courses starting on {string} or after")
  public void findCoursesStartingOnOrAfter(String dateStr) {
    Map<String, String> allCoursesBigOrEqualDate = coursesPage.getAllCoursesBigOrEqualDate(dateStr);

    allCoursesBigOrEqualDate.forEach((courseName, courseDate) -> {
      System.out.printf("Course: %s | Start Date: %s%n", courseName, courseDate);
    });
  }

  @Then("all earliest and latest courses should be visible on the page")
  public void checkCoursesVisibility() {
    earliestCourses.forEach(course ->
        softly.assertThat(coursesPage.isCourseModelInPage(course))
            .as("Earliest course should be present: " + course.getText())
            .isTrue());

    latestCourses.forEach(course ->
        softly.assertThat(coursesPage.isCourseModelInPage(course))
            .as("Latest course should be present: " + course.getText())
            .isTrue());

    softly.assertAll();
  }

  @Then("find cheapest and expensive courses")
  public void getCheapestAndExpensiveCourse() {
    Map<String, String> allCoursesPricesAndNames = coursesPage.getAllCoursesPricesAndNames();
    Map.Entry<String, String> cheapestCourse = allCoursesPricesAndNames.entrySet().stream()
        .min(Comparator.comparing(entry -> entry.getValue().split(" ")[0]))
        .orElseThrow(() -> new IllegalArgumentException("No courses found"));

    Map.Entry<String, String> expensiveCourse = allCoursesPricesAndNames.entrySet().stream()
        .max(Comparator.comparing(entry -> entry.getValue().split(" ")[0]))
        .orElseThrow(() -> new IllegalArgumentException("No courses found"));

    System.out.println("Cheapest Course: " + cheapestCourse.getKey() + " with price " + cheapestCourse.getValue());
    System.out.println("Expensive Course: " + expensiveCourse.getKey() + " with price " + expensiveCourse.getValue());
  }
}

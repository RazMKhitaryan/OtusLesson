package pages;

import com.google.inject.Inject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import models.CourseModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CoursesPage extends AbsBasePage {

  @FindBy(css = ".sc-18q05a6-1.bwGwUO a")
  List<WebElement> courseLinks;
  private CoursePage coursePage;
  private CourseModel courseModel;

  @Inject
  public CoursesPage(WebDriver driver, CoursePage coursePage) {
    super(driver);
    this.coursePage = coursePage;
  }

  @Inject
  public CoursesPage(WebDriver driver, CoursePage coursePage, CourseModel courseModel) {
    super(driver);
    this.coursePage = coursePage;
    this.courseModel = courseModel;
  }

  @Inject
  public CoursesPage(WebDriver driver) {
    super(driver);
  }

  @Override
  public AbsBasePage open() {
    return openPage(getUrl());
  }

  @Override
  public String getUrl() {
    return "/catalog/courses";
  }

  public CoursePage clickOnCourseByName(String courseName) {
    WebElement webElement = courseLinks.stream()
        .filter(course -> course.getText()
            .trim()
            .contains(courseName)) // реализациа фильтра
        .findFirst()
            .orElseThrow(() -> new RuntimeException("Course not found: " + courseName));
    clickOnElement(webElement);
    return coursePage;
  }

  public List<CourseModel> parseCourseLinksToModels(CourseModel courseModel) {
    return courseLinks.stream()
        .map(el -> {
          String[] split = el.getText().split("\n");
          courseModel.setGroup(split[0].trim());
          courseModel.setStatus(split[1].trim());
          courseModel.setName(split[2].trim());
          try {
            courseModel.setDate(split[3].split(" · ")[0].trim());
          } catch (Exception e) {
          }
          return courseModel;
        })
        .filter(course -> course.getDate() != null)
        .collect(Collectors.toList());
  }

  public List<CourseModel> getEarliestCourses(List<CourseModel> courses) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM, yyyy", new Locale("ru"));
    Optional<LocalDate> earliestDateOpt = courses.stream()
        .map(course -> LocalDate.parse(course.getDate(), formatter))
        .reduce(
            (d1, d2) -> d1.isBefore(d2) ? d1 : d2); // необходимо использовать stream api и reduce.
    if (earliestDateOpt.isEmpty()) {
      return List.of();
    }
    LocalDate earliestDate = earliestDateOpt.get();
    return courses.stream()
        .filter(course -> LocalDate.parse(course.getDate(), formatter).equals(earliestDate))
        .collect(Collectors.toList());
  }


  public boolean isCourseModelInPage(CourseModel courseModel) {
    Document doc = Jsoup.parse(driver.getPageSource()); // необходимо использовать jsoup
    String group = courseModel.getGroup();
    String status = courseModel.getStatus();
    String name = courseModel.getName();
    String date = courseModel.getDate();
    String query = String.format("div:contains(%s):contains(%s):contains(%s):contains(%s)", group,
        status, name, date);
    Element element = doc.select(query).first();
    return element != null;
  }
}

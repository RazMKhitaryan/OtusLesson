package pages;

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

  @FindBy(css = ".sc-1w8jhjp-4.isrHWT .sc-1fry39v-0.eAOVcR.sc-nncjh3-1.eQrMuA")
  private List<WebElement> coursesList;

  public CoursesPage(WebDriver driver) {
    super(driver);
  }

  @Override
  public CoursesPage open() {
    openPage(getUrl());
    return this;
  }

  @Override
  public String getUrl() {
    return "/catalog/courses";
  }

  public void clickOnCourseByName(String courseName) {
    WebElement webElement = courseLinks.stream()
        .filter(course -> course.getText().trim()
            .contains(courseName)) // реализациа фильтра
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Course not found: " + courseName));
    clickOnElement(webElement);
  }

  public List<CourseModel> parseCourseLinksToModels(CourseModel courseModel) {
    return courseLinks.stream()
        .map(el -> {
          String[] split = el.getText().split("\n");
          try {
            courseModel.setGroup(split[0].trim());
            courseModel.setStatus(split[1].trim());
            courseModel.setName(split[2].trim());
            courseModel.setDate(split[3].split(" · ")[0].trim());
          } catch (Exception ignored) {
            System.out.println("Error parsing date: " + ignored.getMessage());
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

  public WebElement getOpenedCourseByName(String courseName) {
    return coursesList.stream()
        .filter(course -> course.getText()
            .contains(courseName))
        .findFirst()
        .get();
  }

  public boolean isCourseSelected(WebElement webElement) {
    if (getElementAttribute(webElement, "value").equals("true")) {
      return true;
    } else {
      return false;
    }
  }
}

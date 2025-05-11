package pages;

import annotations.Path;
import com.google.inject.Inject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import scope.ScenScoped;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Path("/catalog/courses")
public class CoursesPage extends AbsBasePage {

  @FindBy(xpath = "//main//section[2]//a")
  List<WebElement> courseLinks;

  @FindBy(xpath = "//main//section[2]//div[2]//a//h6/div")
  List<WebElement> coursesNames;

  @FindBy(xpath = "//section[2]//a/div[2]/div/div")
  List<WebElement> coursesDates;

  @FindBy(xpath = "//main//section[1]//div[1]//div[2]//div//div//div")
  private List<WebElement> coursesList;

  @FindBy(xpath = "//*[text()='Подготовительные курсы']")
  private WebElement onboardingCourses;

  @Inject
  public CoursesPage(ScenScoped scenScoped) {
    super(scenScoped.getDriver());
  }

  public String getRandomCourseName() {
    List<String> names = coursesNames.stream().map(WebElement::getText).toList();
    int index = (int) (Math.random() * coursesNames.size());
    return names.get(index);
  }

  public void clickOnCourseByName(String courseName) {
    WebElement webElement = courseLinks.stream()
        .filter(course -> course.getText().trim().contains(courseName)) // реализациа фильтра
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Course not found: " + courseName));
    clickOnElement(webElement);
  }

  public void clickInOnboardingCourses() {
    waitUtils.waitTillElementVisible(onboardingCourses);
    actionUtils.moveToElementAndClick(onboardingCourses);
  }

  public List<WebElement> getEarliestCourses() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM, yyyy", new Locale("ru"));
    Optional<LocalDate> earliestDateOpt = coursesDates.stream()
        .map(course -> {
          String dateText = course.getText().trim().split(" . ")[0].trim();
          return LocalDate.parse(dateText, formatter);
        })
        .reduce((d1, d2) -> d1.isBefore(d2) ? d1 : d2);
    if (earliestDateOpt.isEmpty()) {
      return List.of();
    }
    LocalDate earliestDate = earliestDateOpt.get();
    return coursesDates.stream()
        .filter(course -> {
          String dateText = course.getText().trim().split(" . ")[0].trim();
          return LocalDate.parse(dateText, formatter).equals(earliestDate);
        })
        .collect(Collectors.toList());
  }


  public List<WebElement> getLatestCourses() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM, yyyy", new Locale("ru"));
    Optional<LocalDate> latestDates = coursesDates.stream()
        .map(course -> LocalDate.parse(course.getText().split(" . ")[0].trim(), formatter))
        .reduce(
            (d1, d2) -> d1.isAfter(d2) ? d1 : d2); // необходимо использовать stream api и reduce.
    if (latestDates.isEmpty()) {
      return List.of();
    }
    LocalDate latestDate = latestDates.get();
    return coursesDates.stream()
        .filter(course -> LocalDate.parse(course.getText().split(" . ")[0].trim(), formatter).equals(latestDate))
        .collect(Collectors.toList());
  }

  public Map<String, String> getAllCoursesBigOrEqualDate(String date) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM, yyyy", new Locale("ru"));
    LocalDate inputDate = LocalDate.parse(date, formatter);

    return coursesDates.stream()
        .filter(el -> {
          LocalDate parsedDate = LocalDate.parse(el.getText().split(" . ")[0].trim(), formatter);
          return !parsedDate.isBefore(inputDate);
        })
        .collect(Collectors.toMap(
            dateEl -> dateEl.findElement(By.xpath("./ancestor::a//h6/div")).getText(),
            WebElement::getText));
  }

  public boolean isCourseModelInPage(WebElement courseDate) {
    Document doc = Jsoup.parse(driver.getPageSource());
    String courseDateText = courseDate.getText().trim();
    String query = String.format("div:contains(%s)", courseDateText);
    Element element = doc.select(query).first();
    return element != null;
  }

  public WebElement getOpenedCourseByName(String courseName) {
    return coursesList.stream()
        .filter(course -> course.getText().equalsIgnoreCase(courseName))
        .findFirst()
        .get();
  }

  public boolean isCourseSelected(WebElement webElement) {
    waitUtils.waitTillElementVisible(webElement);
    if (getElementAttribute(webElement, "value").equals("true")) {
      return true;
    } else {
      return false;
    }
  }
  public Map<String, String> getAllCoursesPricesAndNames() {
    try {
      Thread.sleep(5000); // there is not other way to wait elements update
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    this.initPage();
    String courseTitle = null;
    String coursePrice = null;
    HashMap<String, String> courses = new HashMap<>();
    List<String> courseHrefs = courseLinks.stream()
        .map(link -> link.getAttribute("href"))
        .collect(Collectors.toList());

    for (String href : courseHrefs) {
      try {
        Document doc = Jsoup.connect(href).get();

        courseTitle = doc.select("main h3").first().text();
        coursePrice = doc.select("div:matchesOwn(\\d+\\s*₽)").first().text();
      } catch (IOException e) {
        e.printStackTrace();
      }
      courses.put(courseTitle, coursePrice);
    }
    return courses;
  }
}

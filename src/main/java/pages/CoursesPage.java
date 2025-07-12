package pages;

import annotations.Path;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Path("/catalog/courses")
public class CoursesPage extends AbsBasePage {

  private static final DateTimeFormatter DATE_FORMATTER =
      DateTimeFormatter.ofPattern("d MMMM, yyyy", new Locale("ru"));
  private final Random random = new Random();

  @FindBy(xpath = "//main//section[2]//a")
  private List<WebElement> courseLinks;

  @FindBy(xpath = "//main//section[2]//div[2]//a//h6/div")
  private List<WebElement> coursesNames;

  @FindBy(xpath = "//main//section[1]//div[1]//div[2]//div//div//div")
  private List<WebElement> coursesList;
  @FindBy(xpath = "//section[2]//a/div[2]/div/div")
  private List<WebElement> coursesDates;

  public String getRandomCourseName() {
    waitUtils.waitTillElementVisible(coursesNames.get(0));
    List<String> names = coursesNames.stream()
        .map(this::getText)
        .toList();
    return names.get(random.nextInt(names.size()));
  }

  public void clickOnCourseByName(String courseName) {
    WebElement courseElement = courseLinks.stream()
        .filter(course -> getText(course).trim().contains(courseName))
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Course not found: " + courseName));
    clickOnElement(courseElement);
  }

  private LocalDate parseCourseDate(WebElement courseDate) {
    String dateText = getText(courseDate).trim().split(" . ")[0].trim();
    return LocalDate.parse(dateText, DATE_FORMATTER);
  }

  public List<WebElement> getEarliestCourses() {
    if (coursesDates.isEmpty()) {
      return List.of();
    }

    LocalDate earliestDate = coursesDates.stream()
        .map(this::parseCourseDate)
        .min(LocalDate::compareTo)
        .orElseThrow();

    return coursesDates.stream()
        .filter(course -> parseCourseDate(course).equals(earliestDate))
        .collect(Collectors.toList());
  }

  public List<WebElement> getLatestCourses() {
    if (coursesDates.isEmpty()) {
      return List.of();
    }

    LocalDate latestDate = coursesDates.stream()
        .map(this::parseCourseDate)
        .max(LocalDate::compareTo)
        .orElseThrow();

    return coursesDates.stream()
        .filter(course -> parseCourseDate(course).equals(latestDate))
        .collect(Collectors.toList());
  }

  public boolean isCourseModelInPage(WebElement courseDate) {
    Document doc = Jsoup.parse(driver.getPageSource());
    String courseDateText = getText(courseDate).trim();
    Element element = doc.select(String.format("div:contains(%s)", courseDateText)).first();
    return element != null;
  }

  public WebElement getOpenedCourseByName(String courseName) {
    return coursesList.stream()
        .filter(course -> getText(course).equalsIgnoreCase(courseName))
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Course not found: " + courseName));
  }

  public boolean isCourseSelected(WebElement webElement) {
    return "true".equals(getElementAttribute(webElement, "value"));
  }
}
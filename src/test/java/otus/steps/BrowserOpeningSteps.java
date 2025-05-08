package otus.steps;

import com.google.inject.Inject;
import io.cucumber.java.en.Given;
import scope.ScenScoped;

public class BrowserOpeningSteps {
  private final static String BASE_URL = System.getProperty("baseUrl", "https://otus.ru");

  private String browser;

  @Inject
  private ScenScoped scenScoped;

  @Given("I open the browser {string}")
  public void openBrowser(String browserName) {
    this.browser = browserName.toLowerCase();
    scenScoped.getDriver().get(BASE_URL);
  }
}

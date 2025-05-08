package otus.steps.hooks;

import com.google.inject.Inject;
import io.cucumber.java.After;
import org.openqa.selenium.WebDriver;
import scope.ScenScoped;

public class Hooks {

  @Inject
  private ScenScoped scenScoped;

  @After
  public void after() {
    WebDriver driver = scenScoped.getDriver();
    if (driver != null) {
      driver.quit();
    }
  }
}

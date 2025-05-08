package scope;

import factory.WebDriverFactory;
import io.cucumber.guice.ScenarioScoped;
import org.openqa.selenium.WebDriver;
import java.util.HashMap;
import java.util.Map;

@ScenarioScoped
public class ScenScoped {

  private WebDriver driver = new WebDriverFactory().create();

  private Map<String, Object> storeObj = new HashMap<>();

  public WebDriver getDriver() {
    return this.driver;
  }

  public <T> void set(T obj, String name) {
    storeObj.put(name, obj);
  }

  public <T> T get(String name) {
    return (T) storeObj.get(name);
  }
}

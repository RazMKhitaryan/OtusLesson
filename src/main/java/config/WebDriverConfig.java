package config;

import factory.WebDriverFactory;
import org.openqa.selenium.WebDriver;

public class WebDriverConfig {

  public WebDriver webDriver() {
    return new WebDriverFactory().create();
  }
}

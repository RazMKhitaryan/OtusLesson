package factory;

import exeptions.BrowserNotSupportedException;
import factory.settings.ChromeSettings;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebDriverFactory {

  private final String browserName = System.getProperty("browser");

  public WebDriver create() {
    switch (browserName) {
      case "chrome": {
        return new ChromeDriver(new ChromeSettings().settings());
      }
    }
    throw new BrowserNotSupportedException(browserName);
  }

}

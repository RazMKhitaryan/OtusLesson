package factory;

import exeptions.BrowserNotSupportedException;
import factory.settings.ChromeSettings;
import listeners.MouseListener;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;


public class WebDriverFactory {

  private final String browserName = System.getProperty("browser");

  public WebDriver create() {
    switch (browserName) {
      case "chrome": {
        return new EventFiringDecorator<>(new MouseListener()).decorate(
            new ChromeDriver(new ChromeSettings().settings()));
      }
    }
    throw new BrowserNotSupportedException(browserName);
  }

}

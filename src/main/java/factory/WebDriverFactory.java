package factory;

import exeptions.BrowserNotSupportedException;
import factory.settings.ChromeSettings;
import listeners.MouseListener;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import java.net.MalformedURLException;
import java.net.URL;

public class WebDriverFactory {

  private final String browserName = System.getProperty("browser", "chrome").toLowerCase();
  private final String runMode = System.getProperty("mode", "local").toLowerCase();

  public WebDriver create() throws MalformedURLException {
    WebDriver driver;

    switch (browserName) {
      case "chrome":
        if ("remote".equals(runMode)) {
          driver = new RemoteWebDriver(new URL("http://192.168.18.52/wd/hub"), new ChromeSettings().settings());
        } else {
          driver = new ChromeDriver(new ChromeSettings().settings());
        }
        break;
      default:
        throw new BrowserNotSupportedException(browserName);
    }
    return new EventFiringDecorator<>(new MouseListener()).decorate(driver);
  }
}

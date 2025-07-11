package factory;

import exeptions.BrowserNotSupportedException;
import factory.settings.ChromeSettings;
import factory.settings.FirefoxSettings;
import listeners.MouseListener;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import java.net.MalformedURLException;
import java.net.URL;

public class WebDriverFactory {

  private final String browserName = System.getProperty("browser", "chrome").toLowerCase();
  private final String runMode = System.getProperty("mode", "local").toLowerCase();
  private final String vm = System.getProperty("url","http://192.168.18.52:4444/wd/hub");
  private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

  public WebDriver getDriver() {
    return DRIVER.get();
  }

  public WebDriver create() {
    WebDriver driver;

    switch (browserName) {
      case "chrome":
        if ("remote".equals(runMode)) {
          try {
            driver = new RemoteWebDriver(new URL(vm), new ChromeSettings().settingsAmd64());
          } catch (MalformedURLException e) {
            throw new RuntimeException(e);
          }
        } else {
          driver = new ChromeDriver(new ChromeSettings().settingsAmd64());
        }
        break;
      case "firefox":
        if ("remote".equals(runMode)) {
          try {
            driver = new RemoteWebDriver(new URL(vm), new FirefoxSettings().settings());
          } catch (MalformedURLException e) {
            throw new RuntimeException(e);
          }
        } else {
          driver = new FirefoxDriver(new FirefoxSettings().settings());
        }
        break;
      default:
        throw new BrowserNotSupportedException(browserName);
    }
    DRIVER.set(driver);
    return new EventFiringDecorator<>(new MouseListener()).decorate(driver);
  }

  public void killDriver() {
    if (DRIVER != null) {
      DRIVER.remove();
    }
  }
}

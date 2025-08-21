package factory;

import exceptions.BrowserNotSupportedException;
import factory.settings.ChromeSettings;
import factory.settings.FirefoxSettings;
import java.net.MalformedURLException;
import java.net.URL;
import listeners.MouseListener;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.springframework.stereotype.Component;

@Component
public class WebDriverFactory {

  private final String browserName = System.getProperty("browser", "chrome").toLowerCase();
  private final String runMode = System.getProperty("mode", "local").toLowerCase();
  private final String vm = System.getProperty("url", "http://192.168.18.52:4444/wd/hub");
  private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();
  private static final ThreadLocal<RemoteWebDriver> REMOTE_DRIVER = new ThreadLocal<>();

  public synchronized WebDriver create() {
    RemoteWebDriver rawDriver;
    try {
      switch (browserName.toLowerCase()) {
        case "chrome" -> {
          if ("remote".equalsIgnoreCase(runMode)) {
            rawDriver = new RemoteWebDriver(new URL(vm), new ChromeSettings().settingsAmd64());
          } else {
            rawDriver = new ChromeDriver(new ChromeSettings().settingsAmd64());
          }
        }
        case "firefox" -> {
          if ("remote".equalsIgnoreCase(runMode)) {
            rawDriver = new RemoteWebDriver(new URL(vm), new FirefoxSettings().settings());
          } else {
            rawDriver = new FirefoxDriver(new FirefoxSettings().settings());
          }
        }
        default -> throw new BrowserNotSupportedException(browserName);
      }
    } catch (MalformedURLException e) {
      throw new RuntimeException("Invalid remote URL: " + vm, e);
    }
    WebDriver decoratedDriver = new EventFiringDecorator<>(new MouseListener()).decorate(
        rawDriver);
    REMOTE_DRIVER.set(rawDriver);
    DRIVER.set(decoratedDriver);
    return decoratedDriver;
  }

  public WebDriver getDriver() {
    return DRIVER.get();
  }

  public void killDriver() {
    WebDriver driver = DRIVER.get();
    if (driver != null) {
      try {
        driver.close();
        driver.quit();
        DRIVER.remove();
        REMOTE_DRIVER.remove();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }

  public RemoteWebDriver getRawDriver() {
    return REMOTE_DRIVER.get();
  }
}
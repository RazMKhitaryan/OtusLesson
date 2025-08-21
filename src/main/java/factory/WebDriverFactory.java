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
import org.testng.ITestContext;

@Component
public class WebDriverFactory {

  private final String browserName = System.getProperty("browser", "chrome").toLowerCase();
  private final String runMode = System.getProperty("mode", "local").toLowerCase();
  private final String vm = System.getProperty("url", "http://192.168.18.52:4444/wd/hub");
  private final String video = System.setProperty("java.awt.headless", "false");

  private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

  public synchronized WebDriver getDriver() {
    return DRIVER.get();
  }

  public synchronized WebDriver create(ITestContext context) {
    WebDriver driver;
    switch (browserName) {
      case "chrome" -> {
        if ("remote".equals(runMode)) {
          try {
            driver = new RemoteWebDriver(new URL(vm),
                new ChromeSettings().settingsAmd64(context.getName()));
          } catch (MalformedURLException e) {
            throw new RuntimeException(e);
          }
        } else {
          driver = new ChromeDriver(new ChromeSettings().settingsAmd64(context.getName()));
        }
      }
      case "firefox" -> {
        if ("remote".equals(runMode)) {
          try {
            driver = new RemoteWebDriver(new URL(vm), new FirefoxSettings().settings());
          } catch (MalformedURLException e) {
            throw new RuntimeException(e);
          }
        } else {
          driver = new FirefoxDriver(new FirefoxSettings().settings());
        }
      }
      default -> throw new BrowserNotSupportedException(browserName);
    }

    WebDriver decoratedDriver = new EventFiringDecorator<>(new MouseListener()).decorate(driver);
    DRIVER.set(decoratedDriver);
    return decoratedDriver;
  }

  public void killDriver() {
    WebDriver driver = DRIVER.get();
    if (driver != null) {
      try {
        DRIVER.remove();
        driver.close();
        driver.quit();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }
}
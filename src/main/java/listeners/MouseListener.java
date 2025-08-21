package listeners;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.WebDriverListener;

public class MouseListener implements WebDriverListener {

  @Override
  public void beforeClick(WebElement element) {
    try {
      RemoteWebDriver driver = (RemoteWebDriver) ((WrapsDriver) element).getWrappedDriver();
      JavascriptExecutor js = driver;
      js.executeScript("arguments[0].style.border='3px solid red';",
          element);
    } catch (Exception e) {
      // Ignore exceptions in the listener to prevent test failures
      System.out.println("Warning: MouseListener could not highlight element: " + e.getMessage());
    }
  }
}

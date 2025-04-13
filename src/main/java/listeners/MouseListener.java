package listeners;


import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;

public class MouseListener implements WebDriverListener {

  WebDriver driver;

  public MouseListener(WebDriver driver) {
    this.driver = driver;
  }

  @Override
  public void beforeClick(WebElement element) {
    ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='3px solid red';",
        element);
  }

}

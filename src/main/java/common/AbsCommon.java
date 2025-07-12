package common;

import factory.WebDriverFactory;
import jakarta.annotation.PostConstruct;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import utils.ActionUtils;
import utils.WaitUtils;

public abstract class AbsCommon<T extends AbsCommon<T>> {
  @Autowired
  protected WebDriverFactory webDriverFactory;

  protected WebDriver driver;
  @Autowired
  protected WaitUtils waitUtils;

  @Autowired
  protected ActionUtils actionUtils;

  @PostConstruct
  public void initPages() {
    PageFactory.initElements(getDriver(), this);
  }

  protected WebDriver getDriver() {
    if (this.driver == null) {
      try {
        this.driver = webDriverFactory.getDriver();
      } catch (IllegalStateException e) {
        e.printStackTrace();
      }
      PageFactory.initElements(driver, this);
    }
    return this.driver;
  }

  protected T clickOnElement(WebElement element) {
    waitUtils.waitTillElementVisible(element);
    element.click();
    return (T) this;
  }

  protected String getText(WebElement element) {
    waitUtils.waitTillElementVisible(element);
    return element.getText();
  }

  protected String getElementAttribute(WebElement element, String text) {
    waitUtils.waitTillElementVisible(element);
    return element.getDomAttribute(text);
  }

  public void addCookie() {
    JavascriptExecutor js = (JavascriptExecutor) getDriver();
    js.executeScript("localStorage.setItem('cookieAccess', 'true');");
  }
}
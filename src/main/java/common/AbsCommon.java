package common;

import config.WebDriverConfig;
import factory.WebDriverFactory;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import utils.ActionUtils;
import utils.WaitUtils;

public abstract class AbsCommon<T extends AbsCommon<T>> {
  protected ActionUtils actionUtils;
  protected WebDriver driver;
  protected WaitUtils waitUtils;

  public AbsCommon() {
    this.driver = new WebDriverFactory().getDriver();
    this.waitUtils = new WaitUtils(driver).getWaitDriver();
    this.actionUtils = new ActionUtils(driver);
    PageFactory.initElements(driver, this);
  }

  protected T clickOnElement(WebElement element) {
    waitUtils.waitTillElementVisible(element); // before click or getText
    element.click();
    return (T) this;
  }

  protected String getText(WebElement element) {
    waitUtils.waitTillElementVisible(element); // before click or getText
    return element.getText();
  }

  protected String getElementAttribute(WebElement element, String text) {
    waitUtils.waitTillElementVisible(element); // before click or getText
    return element.getDomAttribute(text);
  }

  public void addCookie() {
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("localStorage.setItem('cookieAccess', 'true');");
  }
}

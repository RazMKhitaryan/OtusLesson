package common;

import listeners.MouseListener;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public abstract class AbsCommon<T extends AbsCommon> {

  protected WebDriver driver;
  protected MouseListener mouseListener;

  public AbsCommon(WebDriver driver, MouseListener mouseListener) {
    this.driver = driver;
    this.mouseListener = mouseListener;
    PageFactory.initElements(driver, this); //реализациа фабрики
  }

  protected T clickOnElement(WebElement element) {
    mouseListener.beforeClick(element); // подсветка элементов
    element.click();
    return (T) this;
  }

  protected String getText(WebElement element) {
    return element.getText();
  }

  protected String getElementAttribute(WebElement element, String text) {
    return element.getDomAttribute(text);
  }

  public void addCookie() {
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("localStorage.setItem('cookieAccess', 'true');");
  }
}

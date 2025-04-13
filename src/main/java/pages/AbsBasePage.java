package pages;

import common.AbsCommon;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public abstract class AbsBasePage<T extends AbsBasePage> extends AbsCommon {

  private final static String BASE_URL = "https://otus.ru";

  public AbsBasePage(WebDriver driver) {
    super(driver);
    PageFactory.initElements(driver, this);
  }

  public abstract T open();

  protected T openPage(String path) {
    driver.get(BASE_URL + path);
    return (T) this;
  }

  public abstract String getUrl();

  protected T clickOnElement(WebElement element) {
    element.click();
    return (T) this;
  }

  protected String getText(WebElement element) {
    return element.getText();
  }
}
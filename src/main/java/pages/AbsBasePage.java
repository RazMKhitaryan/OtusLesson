package pages;

import common.AbsCommon;
import org.openqa.selenium.WebDriver;

public abstract class AbsBasePage<T extends AbsBasePage> extends AbsCommon {

  private final static String BASE_URL = "https://otus.ru";

  public AbsBasePage(WebDriver driver) {
    super(driver);
  }

  public abstract T open();

  protected T openPage(String path) {
    driver.get(BASE_URL + path);
    return (T) this;
  }

  public abstract String getUrl();

}
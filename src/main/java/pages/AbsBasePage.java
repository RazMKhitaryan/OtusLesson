package pages;

import common.AbsCommon;
import listeners.MouseListener;
import org.openqa.selenium.WebDriver;

public abstract class AbsBasePage<T extends AbsBasePage> extends AbsCommon {

  private final static String BASE_URL = "https://otus.ru";

  public AbsBasePage(WebDriver driver, MouseListener mouseListener) {
    super(driver, mouseListener);
  }

  public abstract T open();

  protected T openPage(String path) {
    driver.get(BASE_URL + path);
    return (T) this;
  }

  public abstract String getUrl();

}
package pages;

import annotations.Path;
import common.AbsCommon;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.AnnotationUtils;
import java.util.List;

public abstract class AbsBasePage<T extends AbsBasePage> extends AbsCommon {
  private final static String BASE_URL = "https://otus.ru";

  public AbsBasePage(WebDriver driver) {
    super(driver);
    waitUtils.waitTillPageLoaded();
  }

  public abstract T open();

  protected T openPage() {
    driver.get(BASE_URL + getPath());
    return (T) this;
  }

  private String getPath() {
    return new AnnotationUtils().getAnnotationInstance(this.getClass(), Path.class).value();
  }

  protected void pageLoadedCondition(List<WebElement> element) {
    waitUtils.waitTillElementVisible(element.getFirst());
  }
}
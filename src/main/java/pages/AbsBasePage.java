package pages;

import annotations.Path;
import common.AbsCommon;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.AnnotationUtils;
import java.util.List;

public abstract class AbsBasePage extends AbsCommon<AbsBasePage> {
  private final static String BASE_URL = System.getProperty("baseUrl", "https://otus.ru");

  public AbsBasePage() {
    super();
    waitUtils.waitTillPageLoaded();
    waitUtils.waitTillPageReady();
  }

  public void openPage() {
    driver.get(BASE_URL + getPath());
    this.addCookie();
    driver.navigate().refresh();
    waitUtils.waitTillPageLoaded();
    waitUtils.waitTillPageReady();
  }

  private String getPath() {
    return new AnnotationUtils().getAnnotationInstance(this.getClass(), Path.class).value();
  }

  protected void pageLoadedCondition(List<WebElement> elements) {
    waitUtils.waitTillElementVisible(elements.getFirst());
  }
}
